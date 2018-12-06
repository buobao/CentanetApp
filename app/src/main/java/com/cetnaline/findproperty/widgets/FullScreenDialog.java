package com.cetnaline.findproperty.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.cetnaline.findproperty.R;

public class FullScreenDialog extends Dialog {
    private OnViewInitCallback initCallback;

    public FullScreenDialog(@NonNull Context context, @LayoutRes int layout, OnViewInitCallback callback) {
        this(context, layout, R.style.FullScreenDialogTheme, callback);
    }

    public FullScreenDialog(@NonNull Context context, @LayoutRes int layout, int themeResId, OnViewInitCallback callback) {
        super(context, themeResId);
        setCancelable(true);
        this.initCallback = callback;
        init(context, layout);
    }

    private void init(Context context, int layout) {
        View viewDialog = getLayoutInflater().inflate(layout, null);
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        ViewGroup.LayoutParams layoutParams = new  ViewGroup.LayoutParams(width, height);
        setContentView(viewDialog, layoutParams);
        Window window = getWindow();
        window.setWindowAnimations(R.style.FullScreenDialogTheme_WindowAnimation);
        if (initCallback != null) {
            initCallback.onViewInit(viewDialog);
        }
    }

    public interface OnViewInitCallback {
        void onViewInit(View view);
    }
}
