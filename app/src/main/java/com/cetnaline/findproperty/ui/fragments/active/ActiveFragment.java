package com.cetnaline.findproperty.ui.fragments.active;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseFragment;
import com.cetnaline.findproperty.base.IPresenter;
import com.cetnaline.findproperty.widgets.CustomListView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class ActiveFragment extends BaseFragment {
    @BindView(R.id.list)
    CustomListView listView;

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

        listView.setAdapter(new MyItemAdapter(android.R.layout.simple_list_item_2, data));
    }

    @Override
    protected void lazyLoad() {

    }

    private static class MyItemAdapter extends BaseQuickAdapter {
        public MyItemAdapter(int layoutResId, @Nullable List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {
            HashMap<String, String> map = (HashMap<String, String>) item;
            helper.setText(android.R.id.text1,map.get("name"));
            helper.setText(android.R.id.text2,map.get("age"));
        }
    }

}
