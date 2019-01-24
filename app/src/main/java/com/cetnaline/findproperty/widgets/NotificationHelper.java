package com.cetnaline.findproperty.widgets;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.cetnaline.findproperty.R;

import io.rong.imlib.model.Conversation;

public class NotificationHelper {
    private static final String CHANNEL_ID="centanet_channel_id";   //通道渠道id
    public static final String  CHANEL_NAME="centanet_chanel_name"; //通道渠道名称

    @SuppressLint("WrongConstant")
    @TargetApi(Build.VERSION_CODES.O)
    public static  void  show(Context context, String userId, String title, String content){
        NotificationChannel channel = null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            channel = new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setShowBadge(true);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        }
        Notification notification;


        Uri uri = Uri.parse("rong://" + context.getApplicationInfo().processName).buildUpon()
                .appendPath("conversation")
                .appendPath(Conversation.ConversationType.PRIVATE.getName().toLowerCase())
                .appendQueryParameter("targetId", userId)
                .appendQueryParameter("title", title)
                .build();
        Intent sintent = new Intent("android.intent.action.VIEW", uri);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                sintent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //向上兼容 用Notification.Builder构造notification对象
            notification = new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setContentTitle(title)
                    .setChannelId(CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
                    .setContentText(content)
//                    .setFullScreenIntent(intent,true)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setColor(Color.parseColor("#FEDA26"))
//                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                    .setAutoCancel(true)
                    .setGroupSummary(false)
                    .setGroup("group")
                    .build();
        }else {
            //向下兼容 用NotificationCompat.Builder构造notification对象
            notification = new Notification.Builder(context)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(pendingIntent)
//                    .setFullScreenIntent(intent,true)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setColor(Color.parseColor("#FEDA26"))
//                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                    .setTicker(content)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL|Notification.DEFAULT_VIBRATE|Notification.DEFAULT_SOUND)
                    .build();
        }

        //发送通知
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(CHANNEL_ID,1008,notification);
    }
}
