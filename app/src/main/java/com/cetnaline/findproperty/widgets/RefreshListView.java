package com.cetnaline.findproperty.widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.utils.ApplicationUtil;

public class RefreshListView extends FrameLayout {
    private static final String LOADING_MSG = "数据载入中...";
    private static final String DEFAULT_MSG = "下拉刷新数据";

    private RecyclerView mRecyclerView;
    private LinearLayout msgBar;
    private ImageView loadingImage;
    private TextView statusText;
    private int headRefreshViewHeight;
    private RecyclerView.Adapter mAdapter;

    private boolean isOnTop;
    private boolean isOnBottom;

    private boolean scrollToRefresh;  //标识是否需要下拉刷新
    private boolean lastScrollRefreshing; //标识上一次下拉刷新仍在执行

    private float lastPullY = -1;
    private float lastRefreshY = -1;
    private float msgBarLocationY = -1;

    private GestureDetector mGestureDetector;
    private ObjectAnimator rotation;

    public RefreshListView(Context context) {
        this(context,null);
    }

    public RefreshListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attr) {
        headRefreshViewHeight = ApplicationUtil.dip2px(context,120);
        isOnTop = true;
        isOnBottom = false;
        mGestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                //拖动
                if (e2.getY() - e1.getY() > 5) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e2.getY() - e1.getY() > 5) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        mGestureDetector.setIsLongpressEnabled(false);

        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setBackgroundColor(Color.parseColor("#eeeeee"));
        FrameLayout.LayoutParams headLoadingViewParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, headRefreshViewHeight);
        this.addView(relativeLayout,headLoadingViewParams);
        msgBar = new LinearLayout(context);
        RelativeLayout.LayoutParams msgBarLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, headRefreshViewHeight/3);
        msgBarLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        msgBar.setGravity(Gravity.CENTER);
        msgBar.setOrientation(LinearLayout.HORIZONTAL);
        relativeLayout.addView(msgBar, msgBarLayoutParams);
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
        FrameLayout.LayoutParams listParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setBackgroundColor(Color.WHITE);
//        mRecyclerView.setNestedScrollingEnabled(true);
        this.addView(mRecyclerView, listParams);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!mRecyclerView.canScrollVertically(1)){
                    isOnBottom = true;
                } else {
                    isOnBottom = false;
                }
                if(!mRecyclerView.canScrollVertically(-1)){
                    isOnTop = true;
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
        if (isOnTop && mGestureDetector.onTouchEvent(ev)) {
            return true;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                rotation.cancel();
                statusText.setText(DEFAULT_MSG);
                if (msgBarLocationY < 0) {
                    msgBarLocationY = msgBar.getY();
                }
                if (mRecyclerView.getY() >= headRefreshViewHeight) {
                    break;
                } else if (mRecyclerView.getY() >= headRefreshViewHeight/2) {
                    scrollToRefresh = true;
                }
                if (lastScrollRefreshing && lastRefreshY < 0) {
                    lastRefreshY = event.getY();
                }

                if (lastPullY < 0) {
                    lastPullY = event.getY();
                } else {
                    float deltaY = event.getY() - lastPullY;
                    loadingImage.setRotation(loadingImage.getRotation() + deltaY);
                    float lastY = mRecyclerView.getY();
                    double facter = Math.pow(headRefreshViewHeight - lastY,2)/Math.pow(headRefreshViewHeight,2);
                    lastPullY = event.getY();
                    float locationY = (float) (lastY + deltaY * facter);
                    if (locationY >= 0) {
                        mRecyclerView.setY(locationY);
                        if (mRecyclerView.getY() > headRefreshViewHeight/3) {
                            msgBar.setY((float) (msgBar.getY() + deltaY * facter));
                        } else {
                            msgBar.setY(msgBarLocationY);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (lastScrollRefreshing) {
                    if (event.getY() - lastRefreshY < headRefreshViewHeight/2) {
                        //轻拉取消
                        stopRefresh();
                    } else {
                        mRecyclerView.setY(headRefreshViewHeight/3);
                        rotation.start();
                        statusText.setText(LOADING_MSG);
                        lastScrollRefreshing = true;
                    }
                } else {
                    if (scrollToRefresh && event.getY() > headRefreshViewHeight/2) {
                        mRecyclerView.setY(headRefreshViewHeight/3);
                        rotation.start();
                        statusText.setText(LOADING_MSG);
                        lastScrollRefreshing = true;
                    } else {
                        stopRefresh();
                    }
                }
                lastPullY = -1;
                lastRefreshY = -1;
                scrollToRefresh = false;
                msgBar.setY(msgBarLocationY);
                break;
        }

        return super.onTouchEvent(event);
    }

    public void stopRefresh() {
        lastScrollRefreshing = false;
        scrollToRefresh = false;
        loadingImage.clearAnimation();
        mRecyclerView.setY(0);
    }
}



















