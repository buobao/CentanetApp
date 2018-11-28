package com.cetnaline.findproperty.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.cetnaline.findproperty.R;

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        this(context,true);
    }

    public LoadingDialog(Context context, boolean cancelable) {
        super(context, R.style.LoadingdialogTheme);
        this.setCancelable(cancelable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
    }
}
