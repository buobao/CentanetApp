package com.cetnaline.findproperty.ui.fragments.active;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseFragment;
import com.cetnaline.findproperty.base.IPresenter;
import com.cetnaline.findproperty.utils.ApplicationUtil;
import com.cetnaline.findproperty.widgets.RefreshListView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class ActiveFragment extends BaseFragment {
    @BindView(R.id.listview)
    protected RefreshListView mListView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    protected void init() {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>(){
            {
                add(new HashMap(){{
                    put("name", "Jack");
                    put("age","20"); }});
                add(new HashMap(){{
                    put("name", "Jack1");
                    put("age","21"); }});
                add(new HashMap(){{
                    put("name", "Jack2");
                    put("age","22"); }});
                add(new HashMap(){{
                    put("name", "Jack3");
                    put("age","23"); }});
                add(new HashMap(){{
                    put("name", "Jack4");
                    put("age","24"); }});
                add(new HashMap(){{
                    put("name", "Jack5");
                    put("age","25"); }});
                add(new HashMap(){{
                    put("name", "Jack6");
                    put("age","26"); }});
                add(new HashMap(){{
                    put("name", "Jack7");
                    put("age","27"); }});
                add(new HashMap(){{
                    put("name", "Jack7");
                    put("age","27"); }});
                add(new HashMap(){{
                    put("name", "Jack7");
                    put("age","27"); }});
                add(new HashMap(){{
                    put("name", "Jack7");
                    put("age","27"); }});
                add(new HashMap(){{
                    put("name", "Jack7");
                    put("age","27"); }});
                add(new HashMap(){{
                    put("name", "Jack7");
                    put("age","27"); }});
                add(new HashMap(){{
                    put("name", "Jack7");
                    put("age","27"); }});
                add(new HashMap(){{
                    put("name", "Jack7");
                    put("age","27"); }});
                add(new HashMap(){{
                    put("name", "Jack7");
                    put("age","27"); }});
                add(new HashMap(){{
                    put("name", "Jack7");
                    put("age","27"); }});
                add(new HashMap(){{
                    put("name", "Jack7");
                    put("age","27"); }});
                add(new HashMap(){{
                    put("name", "Jack7");
                    put("age","27"); }});
                add(new HashMap(){{
                    put("name", "Jack7");
                    put("age","27"); }});
                add(new HashMap(){{
                    put("name", "Last");
                    put("age","27"); }});
            }
        };
//        mListView.setAdapter(new SimpleAdapter(getActivity(), data,android.R.layout.simple_list_item_1, new String[]{"name"}, new int[]{android.R.id.text1}));

        RefreshListView.RefreshListViewAdapter adapter = new RefreshListView.RefreshListViewAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                data,
                new RefreshListView.RefreshListViewAdapter.OnItemBind<HashMap<String, String>>() {
                    @Override
                    public void onItemCreate(View view, int i) {
                        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                        layoutParams.height = ApplicationUtil.dip2px(getActivity(), 80);
                        view.setLayoutParams(layoutParams);
                    }

                    @Override
                    public void onBindData(RecyclerView.ViewHolder viewHolder, HashMap<String, String> data, int i) {
//                        viewHolder.itemView.setOnClickListener(v -> showMessage("click"));
                        ((TextView)viewHolder.itemView.findViewById(android.R.id.text1)).setText(data.get("name"));
                    }
                });
        mListView.setAdapter(adapter);
        mListView.setListViewRefreshListener(new RefreshListView.ListViewRefreshListener() {
            @Override
            public void onRefresh() {
                //添加一条数据
                adapter.addDatas(new ArrayList(){
                    {
                        add(new HashMap<String,String>(){
                            {
                                put("name", "Lebron");
                            }
                        });
                    }
                });
                mListView.stopRefresh();
            }

            @Override
            public void onRefreshCancel() {
                showMessage("已取消刷新");
            }

            @Override
            public boolean onLoadMore() {
                adapter.addDatas(new ArrayList(){
                    {
                        add(new HashMap<String,String>(){
                            {
                                put("name", "More");
                            }
                        });
                    }
                });
                return false;
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }


}
