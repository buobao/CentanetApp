package com.cetnaline.findproperty.ui.fragments.active;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
                    put("name", "Jack7");
                    put("age","27"); }});
            }
        };
//        mListView.setAdapter(new SimpleAdapter(getActivity(), data,android.R.layout.simple_list_item_1, new String[]{"name"}, new int[]{android.R.id.text1}));
        mListView.setAdapter(new RecyclerView.Adapter() {
            class VH extends RecyclerView.ViewHolder {
                public VH(@NonNull View itemView) {
                    super(itemView);
                }
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View v = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = ApplicationUtil.dip2px(getActivity(), 40);
                v.setLayoutParams(layoutParams);
                return new VH(v);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                viewHolder.itemView.setOnClickListener(v -> showMessage("click"));
                ((TextView)viewHolder.itemView.findViewById(android.R.id.text1)).setText(data.get(i).get("name"));
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        });


    }

    @Override
    protected void lazyLoad() {

    }


}
