package com.cetnaline.findproperty.ui.wxcallpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.base.IPresenter;
import com.cetnaline.findproperty.utils.ThirdShareUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        ThirdShareUtil.getIwxapi().handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        ThirdShareUtil.getIwxapi().handleIntent(getIntent(),this);
        Log.i("ansen","WXEntryActivity onNewIntent");
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.i("ansen","WXEntryActivity onReq:"+baseReq);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if(baseResp.getType()== ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){//分享
            Log.i("ansen","微信分享操作.....");
        }else if(baseResp.getType()==ConstantsAPI.COMMAND_SENDAUTH){//登陆
            Log.i("ansen", "微信登录操作.....");
            SendAuth.Resp authResp = (SendAuth.Resp) baseResp;
            //authResp.code
        }
        finish();
    }
}





















