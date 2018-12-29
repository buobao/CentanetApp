package com.cetnaline.findproperty.widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.utils.ApplicationUtil;

import java.util.ArrayList;
import java.util.List;

public class RefreshListView extends FrameLayout {
    private static final String LOADING_MSG = "数据载入中...";
    private static final String DEFAULT_MSG = "下拉刷新数据";
    private static final String ALREADY_REFRESH_MSG = "释放刷新数据";
    private static final String CANCEL_REFRESH_MSG = "释放以取消刷新";

    private int headRefreshViewHeight;
    private RecyclerView mRecyclerView;
    private LinearLayout smoothLayout;
    private LinearLayout msgBar;
    private ImageView loadingImage;
    private TextView statusText;
    private RecyclerView.Adapter mAdapter;
    private ListViewRefreshListener mListViewRefreshListener;

    private boolean isOnTop;
    private boolean isOnBottom;

    private boolean isRefreshStatus;         //是否是下拉刷新状态
    private boolean isHorizontalDragStatus;  //是否是横向滑动状态

    private boolean needDeleteOperation; //是否需要删除操作

    private  boolean allDataLoaded;  //所有数据加载完毕

    private boolean scrollToRefresh;  //标识是否需要下拉刷新
    private boolean lastScrollRefreshing; //标识上一次下拉刷新仍在执行
    private boolean pullAlreadyCanceled;

    private float lastPullY = -1;
    private float lastPullX = -1;
    private float lastRefreshY = -1;

    private ObjectAnimator rotation;

    public RefreshListView(Context context) {
        this(context,null);
    }

    public RefreshListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attr) {
        headRefreshViewHeight = ApplicationUtil.dip2px(context,90);
        isOnTop = true;
        isOnBottom = false;

        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setBackgroundColor(Color.parseColor("#eeeeee"));
        FrameLayout.LayoutParams headLoadingViewParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(relativeLayout,headLoadingViewParams);
        smoothLayout = new LinearLayout(context);
        smoothLayout.setOrientation(LinearLayout.VERTICAL);
        FrameLayout.LayoutParams smoothLayoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        smoothLayoutParams.setMargins(0,-headRefreshViewHeight/3,0,0);
        this.addView(smoothLayout,smoothLayoutParams);

        msgBar = new LinearLayout(context);
        msgBar.setGravity(Gravity.CENTER);
        msgBar.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams msgBarLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,headRefreshViewHeight/3);
        smoothLayout.addView(msgBar, msgBarLayoutParams);

        loadingImage = new ImageView(context);
        loadingImage.setImageResource(R.drawable.ic_list_loading);
        LinearLayout.LayoutParams loadingImgParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        msgBar.addView(loadingImage,loadingImgParams);

        rotation = ObjectAnimator.ofFloat(loadingImage,"rotation",0f,360f);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setInterpolator(new LinearInterpolator());
        rotation.setDuration(500);

        statusText = new TextView(context);
        statusText.setText(DEFAULT_MSG);
        statusText.setTextColor(getResources().getColor(R.color.textHint));
        LinearLayout.LayoutParams statusTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        statusTextParams.setMargins(ApplicationUtil.dip2px(context,8),0,0,0);
        msgBar.addView(statusText, statusTextParams);

        mRecyclerView = new RecyclerView(context);
        LinearLayout.LayoutParams listParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setBackgroundColor(Color.WHITE);
//        mRecyclerView.setNestedScrollingEnabled(true);
        smoothLayout.addView(mRecyclerView, listParams);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!mRecyclerView.canScrollVertically(1)){
                    isOnBottom = true;
                    if (!allDataLoaded && mListViewRefreshListener != null) {
                        allDataLoaded = mListViewRefreshListener.onLoadMore();
                    }
                } else {
                    isOnBottom = false;
                }
                if(!mRecyclerView.canScrollVertically(-1)){
                    isOnTop = true;
                    //这里初始化滑动状态信息，由于listview在上滑过程中消耗了ACTION_UP事件 所以在上滑和下滑交替时只能在这里初始化这部分内容
                    lastPullY = -1;
                    lastPullX = -1;
                    lastRefreshY = -1;
                    scrollToRefresh = false;
                    isRefreshStatus = false;
                    isHorizontalDragStatus = false;
                } else {
                    isOnTop = false;
                }
            }
        });
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isOnTop) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    actionUpClearStatus(ev);
                case MotionEvent.ACTION_MOVE:
                    if (!isHorizontalDragStatus) {
                        if (lastPullY < 0) {
                            lastPullY = ev.getY();
                        } else {
                            if (ev.getY() - lastPullY > ApplicationUtil.dip2px(getContext(), 5)) {
                                isRefreshStatus = true;
                            }
                        }
                    } else {
                        return super.onInterceptTouchEvent(ev);
                    }

                    if (isRefreshStatus) {  //是否已锁定竖向滑动
                        return isRefreshStatus;
                    } else {
                        if (lastPullX < 0) {
                            lastPullX = ev.getX();
                        } else {
                            if (ev.getX() - lastPullX < -ApplicationUtil.dip2px(getContext(), 5)) {
                                isHorizontalDragStatus = true;
                            }
                        }
                        return super.onInterceptTouchEvent(ev);
                    }
                case MotionEvent.ACTION_UP:
                    actionUpClearStatus(ev);
                    break;
            }
            return super.onInterceptTouchEvent(ev);
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isHorizontalDragStatus) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    actionUpClearStatus(event);
                case MotionEvent.ACTION_MOVE:
                    rotation.cancel();
                    statusText.setText(DEFAULT_MSG);
                    if (lastPullY < 0) {
                        lastPullY = event.getY();
                    } else {
                        float deltaY = event.getY() - lastPullY;
                        if (smoothLayout.getY() >= headRefreshViewHeight && deltaY >= 0) {
                            break;
                        } else if (smoothLayout.getY() >= headRefreshViewHeight / 3) {
                            statusText.setText(ALREADY_REFRESH_MSG);
                            scrollToRefresh = true;
                        }
                        if (lastScrollRefreshing) {
                            if (lastRefreshY < 0) {
                                lastRefreshY = event.getY();
                            } else if (event.getY() - lastRefreshY < headRefreshViewHeight / 2) {
                                statusText.setText(CANCEL_REFRESH_MSG);
                            }
                        }

                        loadingImage.setRotation(loadingImage.getRotation() + deltaY);
                        float listY = smoothLayout.getY();
                        double facter = Math.pow(headRefreshViewHeight - listY, 2) / Math.pow(headRefreshViewHeight, 2);
                        lastPullY = event.getY();
                        float locationY = (float) (listY + deltaY * facter);
                        if (locationY > -headRefreshViewHeight / 3) {
                            smoothLayout.setY(locationY > headRefreshViewHeight ? headRefreshViewHeight : locationY);
                            pullAlreadyCanceled = false;
                        } else {
                            pullAlreadyCanceled = true;
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    actionUpClearStatus(event);
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void actionUpClearStatus(MotionEvent event) {
        if (pullAlreadyCanceled) {
            return;
        }
        if (lastScrollRefreshing) {
            if (event.getY() - lastRefreshY < headRefreshViewHeight/2) {
                //轻拉取消
                stopRefresh();
                if (mListViewRefreshListener != null) {
                    mListViewRefreshListener.onRefreshCancel();
                }
            } else {
                smoothLayout.setY(0);
                rotation.start();
                allDataLoaded = false;
                statusText.setText(LOADING_MSG);
                lastScrollRefreshing = true;
                if (mListViewRefreshListener != null) {
                    mListViewRefreshListener.onRefresh();
                }
            }
        } else {
            if (scrollToRefresh && event.getY() > headRefreshViewHeight/2) {
                smoothLayout.setY(0);
                rotation.start();
                allDataLoaded = false;
                statusText.setText(LOADING_MSG);
                lastScrollRefreshing = true;
                if (mListViewRefreshListener != null) {
                    mListViewRefreshListener.onRefresh();
                }
            } else {
                stopRefresh();
            }
        }
        lastPullY = -1;
        lastRefreshY = -1;
        lastPullX = -1;
        scrollToRefresh = false;
        isRefreshStatus = false;
        isHorizontalDragStatus = false;
    }

    public void stopRefresh() {
        lastScrollRefreshing = false;
        scrollToRefresh = false;
        loadingImage.clearAnimation();
        smoothLayout.setY(-headRefreshViewHeight/3);
    }

    public void setListViewRefreshListener(ListViewRefreshListener mListViewRefreshListener) {
        this.mListViewRefreshListener = mListViewRefreshListener;
    }

    public void setNeedDeleteOperation(boolean needDeleteOperation) {
        this.needDeleteOperation = needDeleteOperation;
    }

    public interface ListViewRefreshListener {
        void onRefresh();
        void onRefreshCancel();
        boolean onLoadMore();  //返回值标识是否已经加载全部数据
    }

    public static class RefreshListViewAdapter<T> extends RecyclerView.Adapter {
        private Context mContext;
        private List<T> datas;
        private @LayoutRes int layoutId;
        private OnItemBind mOnItemBind;
        public RefreshListViewAdapter(Context context, @LayoutRes int layoutId, @NonNull ArrayList datas, OnItemBind onItemBind) {
            mContext = context;
            this.datas = datas;
            this.layoutId = layoutId;
            this.mOnItemBind = onItemBind;
        }

        public void setDatas(List datas) {
            this.datas = datas;
            notifyDataSetChanged();
        }

        public void addDatas(List datas) {
            this.datas.addAll(datas);
            notifyDataSetChanged();
        }

        public void removeAllDatas() {
            this.datas.clear();
            notifyDataSetChanged();
        }

        class VH extends RecyclerView.ViewHolder {
            public VH(@NonNull View itemView) {
                super(itemView);
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
            if (mOnItemBind != null) {
                mOnItemBind.onItemCreate(v,i);
            }

            ScrollLayout parentLayout = new ScrollLayout(mContext, ApplicationUtil.dip2px(mContext,120));
            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, v.getLayoutParams().height);
            parentLayout.setLayoutParams(parentParams);
            parentLayout.addChildView(v, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
            TextView deleteBtn = new TextView(mContext);
            deleteBtn.setText("删除");
            deleteBtn.setTextColor(Color.WHITE);
            deleteBtn.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams btnLayoutParams = new LinearLayout.LayoutParams(ApplicationUtil.dip2px(mContext,120), v.getLayoutParams().height);
            deleteBtn.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            parentLayout.addChildView(deleteBtn, btnLayoutParams);
            parentLayout.setClickable(true);
            return new VH(parentLayout);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            if (mOnItemBind != null) {
                mOnItemBind.onBindData(viewHolder, datas.get(i), i);
            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public interface OnItemBind<T> {
            void onItemCreate(View view, int i);
            void onBindData(RecyclerView.ViewHolder viewHolder, T data, int i);
        }
    }

    public static class ScrollLayout extends LinearLayout {
        private float lastX = -1;
        private boolean isScroll;
        private LinearLayout parent;

        private int scrollWidth = 200;
        public ScrollLayout(Context context, int scrollWidth) {
            super(context);
            this.scrollWidth = scrollWidth;
            init(context, null);
        }

        public ScrollLayout(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            init(context,attrs);
        }

        private void init(Context context, AttributeSet attrs) {
            setOrientation(HORIZONTAL);
            parent = new LinearLayout(context);
            parent.setOrientation(HORIZONTAL);
            LinearLayout.LayoutParams parenParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            addView(parent, parenParams);
        }

        public void addChildView(View view, LinearLayout.LayoutParams params) {
            parent.addView(view,params);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    if (lastX < 0) {
                        lastX = ev.getX();
                    } else {
                        if (!isScroll && ev.getX() - lastX < -ApplicationUtil.dip2px(getContext(), 5)) {
                            isScroll = true;
                        } else {
                            float currentX = parent.getX();
                            if (currentX > -scrollWidth) {
                                float deltaX = ev.getX() - lastX;
                                if (deltaX < -scrollWidth) {
                                    parent.setX(-scrollWidth);
                                } else if (deltaX <= 0) {
                                    parent.setX(currentX + deltaX);
                                } else {
                                    parent.setX(0);
                                }
                            }
                            lastX = ev.getX();
                        }
                    }
                case MotionEvent.ACTION_UP:
                    lastX = -1;
                    isScroll = false;
                    break;
            }
            return super.dispatchTouchEvent(ev);
        }

    }
}



















