package com.cetnaline.findproperty.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.cetnaline.findproperty.BuildConfig;
import com.cetnaline.findproperty.FindPropertyApplication;
import com.cetnaline.findproperty.model.network.bean.responsebean.QQTokenBean;
import com.google.gson.Gson;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class ThirdShareUtil {
    private static IWXAPI iwxapi;
    private static Tencent tencent;
    private static IUiListener iUiListener;
    private static SsoHandler ssoHandler;

    public static void init(){
        iwxapi = WXAPIFactory.createWXAPI(FindPropertyApplication.getContext(), BuildConfig.APP_ID_WX, true);
        iwxapi.registerApp(BuildConfig.APP_ID_WX);
        tencent = Tencent.createInstance(BuildConfig.QQ_ID, FindPropertyApplication.getContext());

        //微博sdk
        AuthInfo authInfo = new AuthInfo(FindPropertyApplication.getContext(),
                BuildConfig.SINA_APP_KEY,
                BuildConfig.SINA_CALLBACK_URL,
                BuildConfig.SINA_SCOPE);
        WbSdk.install(FindPropertyApplication.getContext(), authInfo);
    }

    public static IWXAPI getIwxapi() {
        return iwxapi;
    }

    /**
     * 获取微信用户信息
     */
    public static void requireWXUserInfo() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "centaline_wx_login_"+System.currentTimeMillis();
        iwxapi.sendReq(req);
    }

    /**
     * 判断微信是否安装
     * @return
     */
    public static boolean isWXInstalled() {
        if (!iwxapi.isWXAppInstalled()) {
            return false;
        } else
            return true;
    }

    /**
     * 获取qq用户信息
     * @param context
     */
    public static void requireQQUserInfo(Activity context, @NonNull QQRequestListener listener) {
        if (!tencent.isSessionValid()) {
            iUiListener = new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    Gson gson = new Gson();
                    QQTokenBean jo = gson.fromJson(o.toString(), QQTokenBean.class);
                    listener.onComplete(jo);
                }

                @Override
                public void onError(UiError uiError) {
                    listener.onError(uiError);
                }

                @Override
                public void onCancel() {
                    listener.onCancel();
                }
            };
            tencent.login(context, "all", iUiListener);
        }
    }

    public static void handlerTencentResult(Intent intent) {
        Tencent.handleResultData(intent, iUiListener);
    }

    public static void clearTencent(Activity context) {
        if (tencent != null) {
            tencent.logout(context);
        }
    }

    public static void requireSinaUserInfo(Activity context, WbAuthListener listener) {
        ssoHandler = null;
        ssoHandler = new SsoHandler(context);
        ssoHandler.authorizeClientSso(listener);
    }

    public static void handlerSinaresult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 32973 && ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
        ssoHandler = null;
    }

    public interface QQRequestListener {
        void onComplete(QQTokenBean bean);
        void onError(UiError uiError);
        void onCancel();
    }
}
