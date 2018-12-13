package com.cetnaline.findproperty.model.network;

import com.cetnaline.findproperty.BuildConfig;

public class NetWorkContents {
    //服务器地址
    public static final String CENTANET_BASE_HOST = BuildConfig.API_URL;
    //连接超时时间
    public static final int CONNECT_TIMEOUT = 3;
    public static final int READ_TIMEOUT = 5;
    public static final int WRITE_TIMEOUT = 8;

    public static final String APP_NO = "APP_ANDROID_APUSH";
    //加密公钥私钥
    public final static String PUBLICSECRET = "F53DB798524C44AF8DE106DB744EC07";
    public final static String PRIVATESECRET = "2Fo1URzMx2qw3zV2juhcvM7LfjG";
}
