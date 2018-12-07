package com.cetnaline.findproperty.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.utils.ApplicationUtil;

public class CodeInputView extends LinearLayout {
    private int codeNum;
    private float width;
    private float itemPadding;
    private Drawable itemDrawable;

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
        width = ta.getDimension(R.styleable.CodeInputView_input_width,ApplicationUtil.dip2px(context, 24));
        itemPadding = ta.getDimension(R.styleable.CodeInputView_item_padding, ApplicationUtil.dip2px(context, 8));
        itemDrawable = ta.getDrawable(R.styleable.CodeInputView_item_background);
        ta.recycle();

        setOrientation(HORIZONTAL);
        int padding = ApplicationUtil.dip2px(context, 4);
        setPadding(padding,padding,padding,padding);
        for (int i = 0; i < codeNum; i++) {
            AppCompatEditText editText = new AppCompatEditText(context);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setBackground(itemDrawable);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
//            editText.setSelection(editText.getText().length());
            addView(editText);
        }

    }
}



























