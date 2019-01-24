package com.cetnaline.findproperty.ui.conversation;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.cetnaline.findproperty.R;

public class ConversationActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Uri uri = getIntent().getData();
        if (uri != null && !TextUtils.isEmpty(uri.getQueryParameter("title"))) {
//            toolbar.setTitle(uri.getQueryParameter("title"));
        } else {
//            toolbar.setTitle("咨询");
        }
    }
}
