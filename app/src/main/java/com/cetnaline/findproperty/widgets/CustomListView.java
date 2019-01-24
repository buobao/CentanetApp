package com.cetnaline.findproperty.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cetnaline.findproperty.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

public class CustomListView extends FrameLayout {
    SmartRefreshLayout mRefresh;
    RecyclerView mRecyclerView;

    private TextView emptyText;
    private BaseQuickAdapter mAdapter;

    public CustomListView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CustomListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.custom_list_view_layout, this);
        mRefresh = findViewById(R.id.smartRefreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        View emptyView = inflate(context,R.layout.custom_list_view_empty,null);
        emptyText = emptyView.findViewById(R.id.empty_text);
        addView(emptyView);
    }

    public void setEmptyText(String text) {
        emptyText.setText(text);
    }

    public void setAdapter(BaseQuickAdapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
    }

    public void showEmptyView(boolean show) {
        if (show) {
            findViewById(R.id.lt_empty).setVisibility(VISIBLE);
        } else {
            findViewById(R.id.lt_empty).setVisibility(GONE);
        }
    }

    public SmartRefreshLayout getRefresh() {
        return mRefresh;
    }

    public void finishRefresh() {
        mRefresh.finishRefresh();
    }

    public void finishLoadMoreWithNoMoreData(){
        mRefresh.finishLoadMoreWithNoMoreData();
    }

    public void finishLoadMore(){
        mRefresh.finishLoadMore();
    }

    public void setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener listener) {
        mRefresh.setOnRefreshLoadMoreListener(listener);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mRefresh.setOnLoadMoreListener(listener);
    }

    public void setOnRefreshListener(OnRefreshListener listener){
        mRefresh.setOnRefreshListener(listener);
    }

    public void addHeadView(View head) {
        if (mAdapter != null) {
            mAdapter.addHeaderView(head);
        }
    }

}
