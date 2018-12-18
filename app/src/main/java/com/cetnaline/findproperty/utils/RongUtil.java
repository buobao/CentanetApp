package com.cetnaline.findproperty.utils;

import android.text.TextUtils;
import android.util.Log;

import com.cetnaline.findproperty.model.cache.CacheHolder;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class RongUtil {
    /**
     * 连接融云服务器
     */
    public static void connectServer() {
        if (!TextUtils.isEmpty(CacheHolder.getInstance().getRcToken())) {
            RongIM.connect(CacheHolder.getInstance().getRcToken(), new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.i("application","token无效");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.i("application","连接成功："+userid);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.i("application","连接错误:"+errorCode);
                }
            });
        }
    }
}
