package com.cetnaline.findproperty.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.utils.ApplicationUtil;

public class ClearableEditView extends AppCompatEditText implements TextWatcher {
    private boolean isClearButtonShow;
    private ClearActionListener clearActionListener;
    private OnTextChangedListener onTextChangedListener;

    public ClearableEditView(Context context) {
        super(context);
        init(context, null);
    }

    public ClearableEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ClearableEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN &&
                 isClearButtonShow &&
                getWidth() - getPaddingLeft() - event.getX() < ApplicationUtil.dip2px(getContext(), 30)) {
                if (clearActionListener != null) {
                    clearActionListener.onClear(this);
                } else {
                    setText("");
                    isClearButtonShow = false;
                    Drawable[] drawables = getCompoundDrawables();
                    setCompoundDrawables(drawables[0],drawables[1],null,drawables[3]);
                }
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Drawable[] drawables = getCompoundDrawables();
        if (!TextUtils.isEmpty(s) && !isClearButtonShow) {
            drawables[2] = getResources().getDrawable(R.drawable.ic_edt_close_res);
            int width = ApplicationUtil.dip2px(getContext(), 24);
            drawables[2].setBounds(0,0,width,width);
            setCompoundDrawables(drawables[0],drawables[1],drawables[2],drawables[3]);
            isClearButtonShow = true;
        }

        if (isClearButtonShow && TextUtils.isEmpty(s)) {
            setCompoundDrawables(drawables[0],drawables[1],null,drawables[3]);
            isClearButtonShow = false;
        }

        if (onTextChangedListener != null) {
            onTextChangedListener.onTextChanged(s);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setClearActionListener(ClearActionListener clearActionListener) {
        this.clearActionListener = clearActionListener;
    }

    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
        this.onTextChangedListener = onTextChangedListener;
    }

    public interface ClearActionListener {
        void onClear(ClearableEditView clearableEditView);
    }

    public interface OnTextChangedListener {
        void onTextChanged(CharSequence s);
    }

}





































