package com.cetnaline.findproperty.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.cetnaline.findproperty.bus.RxBus;
import com.cetnaline.findproperty.bus.events.NormalEvent;
import com.cetnaline.findproperty.model.network.bean.responsebean.WXTokenBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.WxUserBean;
import com.cetnaline.findproperty.model.network.services.imp.ApiRequestImp;
import com.cetnaline.findproperty.utils.ThirdShareUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private Set<Disposable> disposables;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThirdShareUtil.getIwxapi().handleIntent(getIntent(), this);
        disposables = new HashSet<>();
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        ThirdShareUtil.getIwxapi().handleIntent(getIntent(),this);
        Log.i("WXEntryActivity","WXEntryActivity onNewIntent");
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.i("WXEntryActivity","WXEntryActivity onReq:"+baseReq);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if(baseResp.getType()== ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){//分享
            Log.i("WXEntryActivity","微信分享操作.....");
        }else if(baseResp.getType()==ConstantsAPI.COMMAND_SENDAUTH){//登陆
            SendAuth.Resp authResp = (SendAuth.Resp) baseResp;
            disposables.add(ApiRequestImp.getUserToken(authResp.code)
            .flatMap((Function<WXTokenBean, ObservableSource<WxUserBean>>) wxTokenBean -> ApiRequestImp.getWxUserInfo(wxTokenBean.getAccess_token(),wxTokenBean.getOpenid()))
                    .subscribe(wxUserBean -> {
                        NormalEvent event = new NormalEvent(NormalEvent.WX_LOGIN);
                        event.addParam("openId", wxUserBean.getOpenid());
                        event.addParam("nickName", wxUserBean.getNickname());
                        event.addParam("picUrl", wxUserBean.getHeadimgurl());
                        event.addParam("sex", wxUserBean.getSex()+"");
                        //发送登录信息
                        RxBus.getInstance().post(event);
                    },throwable -> {
                        Toast.makeText(this, "微信授权失败",Toast.LENGTH_SHORT).show();
                        throwable.printStackTrace();
                    }));
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposables!=null && disposables.size()>0) {
            for (Disposable disposable : disposables) {
                if (!disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
        }
    }
}





















