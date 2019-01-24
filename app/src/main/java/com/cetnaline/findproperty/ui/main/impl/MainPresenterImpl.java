package com.cetnaline.findproperty.ui.main.impl;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.cetnaline.findproperty.FindPropertyApplication;
import com.cetnaline.findproperty.base.BasePresenter;
import com.cetnaline.findproperty.bus.RxBus;
import com.cetnaline.findproperty.bus.events.NormalEvent;
import com.cetnaline.findproperty.model.cache.CacheHolder;
import com.cetnaline.findproperty.model.network.bean.BaseResponseBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.StaffListBean;
import com.cetnaline.findproperty.model.network.services.imp.ApiRequestImp;
import com.cetnaline.findproperty.ui.conversation.ConversationActivity;
import com.cetnaline.findproperty.ui.main.MainPresenter;
import com.cetnaline.findproperty.ui.main.MainView;
import com.cetnaline.findproperty.utils.ApplicationUtil;
import com.cetnaline.findproperty.widgets.NotificationHelper;

import io.reactivex.functions.Consumer;
import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.Event;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;

public class MainPresenterImpl extends BasePresenter<MainView> implements MainPresenter {
    @Override
    public void connectRongCloudServer() {
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
                    RxBus.getInstance().post(new NormalEvent(NormalEvent.RONG_CONNECT_SUCCESS));

                    //融云相关初始化工作
                    UserInfo userInfo = new UserInfo("u_" + CacheHolder.getInstance().getCurrentUserInfo().getUserId().toLowerCase(),
                            CacheHolder.getInstance().getCurrentUserInfo().getNickName(),
                            Uri.parse(CacheHolder.getInstance().getCurrentUserInfo().getUserPhotoUrl()));
                    //设置当前用户信息
                    RongIM.getInstance().setCurrentUserInfo(userInfo);
                    RongIM.getInstance().setMessageAttachedUserInfo(true);
                    //接收消息处理 这里处理一种特殊情况，虽然上面设置了发送消息添加用户信息，但是在大多数情况下，接收到的消息仍然没有获取到用户信息
                    //这里做一个统一的处理，当接收到的消息没有携带发送者信息时向服务器请求发送者信息并附加到消息中
                    RongIMClient.setOnReceiveMessageListener((message, i) -> {
                        String content = "";
                        if ("RC:TxtMsg".equals(message.getObjectName())) {
                            content = ((TextMessage) message.getContent()).getContent();
                        } else if ("RC:ImgMsg".equals(message.getObjectName())) {
                            content = "[图片]";
                        } else if ("RC:VcMsg".equals(message.getObjectName())){
                            content = "[视屏]";
                        } else if ("RC:FileMsg".equals(message.getObjectName())) {
                            content = "[文件]";
                        } else {
                            content = "[不支持展示的类型，请打开聊天页面查看]";
                        }

                        if (message.getContent().getUserInfo()!= null) {
                            setMessageResult(message.getContent().getUserInfo(), message, content);
                        } else {
                            getStaffDetail(message.getSenderUserId(), message, content);
                        }
                        return false;
                    });
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

    private void getStaffDetail(String staffNo, Message message, String content) {
        staffNo = staffNo.replace("u_","");
        String finalStaffNo = staffNo;
        addDisposable(ApiRequestImp.getStaffInfo(staffNo)
                .subscribe(resultBean -> {
                    if (resultBean.getResultNo() == BaseResponseBean.SUCCESS_CODE) {
                        UserInfo userInfo = new UserInfo(finalStaffNo,
                                resultBean.getResult().getCnName(),
                                Uri.parse(resultBean.getResult().getStaffImg()));
                        setMessageResult(userInfo,message, content);
                    }
                }));
    }

    private void setMessageResult(UserInfo userInfo, Message message, String content) {
        if (!ApplicationUtil.isForeground(FindPropertyApplication.getContext(), ConversationActivity.class.getName())) {
            NotificationHelper.show(FindPropertyApplication.getContext(), userInfo.getUserId(), userInfo.getName(), content);
        }
        //这里用以向融云组件发送消息通知
        EventBus.getDefault().post(new Event.OnReceiveMessageEvent(message, 0));
        RongIM.setUserInfoProvider(s -> userInfo, true);
    }

}
