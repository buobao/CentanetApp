package com.cetnaline.findproperty.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
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
    private OnInputFinished onInputFinished;

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
            InputItem editText = new InputItem(context);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
//            editText.setOnClickListener(v-> setFocusIndex(focusIndex));
            editText.setOnFocusChangeListener((v, hasFocus) -> {
                           if (hasFocus) {
                               setFocusIndex(focusIndex);
                           } else {
                               if (TextUtils.isEmpty(((InputItem)v).getText())) {
                                   v.setBackground(itemUninputDrawable);
                               } else {
                                   v.setBackground(itemInputedDrawable);
                               }
                           }
            });
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (start == 0 && before == 0) {
                        if (focusIndex < codeNum-1) {
                            setFocusIndex(focusIndex+1);
                        } else {
                            if (onInputFinished != null) {
                                String inputCode = "";
                                for (int i = 0; i < codeNum; i++) {
                                    inputCode +=((InputItem)CodeInputView.this.getChildAt(i)).getText().toString();
                                }
                                onInputFinished.onFinish(inputCode);
                            }
                        }
                    }

//                    if (start == 0 && before == 1 && focusIndex > 0) {
//                        editText.setText("");
//                        if (focusIndex > 0) {
//                            setFocusIndex(focusIndex - 1);
//                        }
//                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            editText.setOnKeyListener((v, keyCode, event) -> {
                if (editText.hasFocus() &&
                        keyCode == KeyEvent.KEYCODE_DEL &&
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                        TextUtils.isEmpty(((InputItem)v).getText().toString())) {
                    if (focusIndex > 0) {
                        ((InputItem)getChildAt(focusIndex-1)).setText("");
                        setFocusIndex(focusIndex - 1);
                    }
                    return true;
                }
                return false;
            });

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

    private void setFocusIndex(int index) {
        InputItem lastItem = (InputItem) getChildAt(focusIndex);
        if (TextUtils.isEmpty(lastItem.getText().toString())) {
            lastItem.setBackground(itemUninputDrawable);
        } else {
            lastItem.setBackground(itemInputedDrawable);
        }
        this.focusIndex = index;
        getChildAt(focusIndex).requestFocus();
        getChildAt(focusIndex).setBackground(itemFocusDrawable);
    }

    public void setOnInputFinished(OnInputFinished onInputFinished) {
        this.onInputFinished = onInputFinished;
    }

    private static class InputItem extends AppCompatEditText {

        public InputItem(Context context) {
            super(context);
        }

        public InputItem(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onSelectionChanged(int selStart, int selEnd) {
            super.onSelectionChanged(selStart, selEnd);
            if(selStart==selEnd){
                setSelection(getText().length());
            }
        }
    }

    public interface OnInputFinished {
        void onFinish(String code);
    }
}



























