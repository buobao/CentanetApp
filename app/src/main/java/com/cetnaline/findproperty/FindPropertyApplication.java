package com.cetnaline.findproperty;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.cetnaline.findproperty.model.cache.CacheHolder;
import com.cetnaline.findproperty.model.database.DatabaseHelper;
import com.cetnaline.findproperty.utils.ApplicationUtil;
import com.cetnaline.findproperty.utils.ThirdShareUtil;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class FindPropertyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        if (BuildConfig.APPLICATION_ID.equals(ApplicationUtil.getCurProcessName(this))) {
            //init app cache
            CacheHolder.init(this);
            //init database
            DatabaseHelper.init(this);
            ThirdShareUtil.init();

            RongIM.init(this, BuildConfig.RONG_CLOUD_KEY);
            //如果用户已经登录则直接连接融云服务器
            if (!TextUtils.isEmpty(CacheHolder.getInstance().getRcToken())) {
                RongIM.connect(CacheHolder.getInstance().getRcToken(), new RongIMClient.ConnectCallback() {

                    /**
                     * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                     *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                     */
                    @Override
                    public void onTokenIncorrect() {

                    }

                    /**
                     * 连接融云成功
                     * @param userid 当前 token 对应的用户 id
                     */
                    @Override
                    public void onSuccess(String userid) {

                    }

                    /**
                     * 连接融云失败
                     * @param errorCode 错误码，可到官网 查看错误码对应的注释
                     */
                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getContext() {
        return mContext;
    }
}
