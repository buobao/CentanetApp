package com.cetnaline.findproperty.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.utils.ApplicationUtil;

public class CodeInputView extends LinearLayout {
    private int codeNum;
    private float width;
    private float itemPadding;
    private Drawable itemFocusDrawable;
    private Drawable itemInputedDrawable;
    private Drawable itemUninputDrawable;
    private int focusIndex = 0;

    public CodeInputView(Context context) {
        super(context);
        init(context, null);
    }

    public CodeInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.CodeInputView);
        codeNum = ta.getInt(R.styleable.CodeInputView_code_num, 4);
        width = ta.getDimension(R.styleable.CodeInputView_input_width,ApplicationUtil.dip2px(context, 48));
        itemPadding = ta.getDimension(R.styleable.CodeInputView_item_padding, ApplicationUtil.dip2px(context, 8));
        itemFocusDrawable = ta.getDrawable(R.styleable.CodeInputView_item_focus_drawable);
        if (itemFocusDrawable == null) {
            itemFocusDrawable = getResources().getDrawable(R.drawable.ic_code_focus);
        }
        itemInputedDrawable = ta.getDrawable(R.styleable.CodeInputView_item_inputed_drawable);
        if (itemInputedDrawable == null) {
            itemInputedDrawable = getResources().getDrawable(R.drawable.ic_code_inputed);
        }
        itemUninputDrawable = ta.getDrawable(R.styleable.CodeInputView_item_uninput_drawable);
        if (itemUninputDrawable == null) {
            itemUninputDrawable = getResources().getDrawable(R.drawable.ic_code_uninput);
        }
        ta.recycle();

        setOrientation(HORIZONTAL);
        int padding = ApplicationUtil.dip2px(context, 4);
        setPadding(padding,padding,padding,padding);
        for (int i = 0; i < codeNum; i++) {
            AppCompatEditText editText = new AppCompatEditText(context);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            if(focusIndex == i) {
                editText.setBackgroundDrawable(itemFocusDrawable);
            } else {
                editText.setBackgroundDrawable(itemUninputDrawable);
            }
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
//            editText.setSelection(editText.getText().length());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)width,(int)width);
            editText.setGravity(Gravity.CENTER);
            if (i < codeNum-1) {
                params.setMargins(0,0,(int)itemPadding,0);
            }
            addView(editText, params);
        }

    }
}



























