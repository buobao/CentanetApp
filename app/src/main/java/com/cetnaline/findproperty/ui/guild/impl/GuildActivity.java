package com.cetnaline.findproperty.ui.guild.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.ui.guild.GuildPresenter;
import com.cetnaline.findproperty.ui.guild.GuildView;
import com.cetnaline.findproperty.widgets.DotIndicator;

import butterknife.BindView;

public class GuildActivity extends BaseActivity<GuildPresenter> implements GuildView {
    @BindView(R.id.view_pager)
    protected ViewPager viewPager;
    @BindView(R.id.dot_indicator)
    protected DotIndicator dotIndicator;
    protected int[] guildImages = new int[]{R.drawable.ic_lead_1, R.drawable.ic_lead_2, R.drawable.ic_lead_3, R.drawable.ic_lead_4};

    @Override
    protected int getLayout() {
        return R.layout.activity_guild;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return guildImages.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public View instantiateItem(ViewGroup container, int position) {
                ImageView image = new ImageView(getApplicationContext());
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                image.setImageResource(guildImages[position]);
                container.addView(image, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                return image;
            }
        });

        dotIndicator.setViewPager(viewPager);
        dotIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
//                if (position == 3) {
//                    ll_bottom_bt.setVisibility(View.VISIBLE);
//                } else {
//                    ll_bottom_bt.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected GuildPresenter createPresenter() {
        return null;
    }
}
