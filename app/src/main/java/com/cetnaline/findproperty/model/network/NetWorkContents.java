package com.cetnaline.findproperty.model.network;

import com.cetnaline.findproperty.BuildConfig;

public class NetWorkContents {
    //服务器地址
    public static final String CENTANET_BASE_HOST = BuildConfig.API_URL;
    //连接超时时间
    public static final int CONNECT_TIMEOUT = 3;
    public static final int READ_TIMEOUT = 5;
    public static final int WRITE_TIMEOUT = 8;

}
