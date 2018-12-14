package com.cetnaline.findproperty.utils;

import com.cetnaline.findproperty.BuildConfig;
import com.cetnaline.findproperty.FindPropertyApplication;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class ThirdShareUtil {
    private static IWXAPI iwxapi;

    public static void init(){
        iwxapi = WXAPIFactory.createWXAPI(FindPropertyApplication.getContext(), BuildConfig.APP_ID_WX, true);
        iwxapi.registerApp(BuildConfig.APP_ID_WX);
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
}
