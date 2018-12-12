package com.cetnaline.findproperty.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.utils.ApplicationUtil;

public class AnimationLayout extends LinearLayout {

    private int showAnimation;
    private int hideAnimation;

    public AnimationLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public AnimationLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.AnimationLayout);
        showAnimation = ta.getResourceId(R.styleable.AnimationLayout_show_animation,R.anim.fullscreendialog_enter);
        hideAnimation = ta.getResourceId(R.styleable.AnimationLayout_show_animation,R.anim.fullscreendialog_exit);
        ta.recycle();
    }

    @Override
    public void setVisibility(int visibility) {
        //显示动画时强制隐藏软键盘
        ApplicationUtil.hideSoftInput(getContext(),this);
        Animation animation = null;
        if (visibility == VISIBLE) {
            animation = AnimationUtils.loadAnimation(getContext(), showAnimation);
        } else {
            animation = AnimationUtils.loadAnimation(getContext(), hideAnimation);
        }
        startAnimation(animation);
        super.setVisibility(visibility);
    }
}
