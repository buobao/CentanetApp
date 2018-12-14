package com.cetnaline.findproperty.bus.events;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class NormalEvent implements Parcelable {
    public static final int NETWORK_CONNECTED = 1;    //网络连接事件
    public static final int NETWORK_DISCONNECTED = 2;  //网络断开事件
    public static final int LOGIN_SUCCESS = 3;         //登录成功事件
    public static final int WX_LOGIN = 4;              //微信登录

    private int code;
    private Map<String, String> params;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void addParam(String name, String value) {
        if (params == null) {
            params = new HashMap<>();
        }
        if (!TextUtils.isEmpty(name)) {
            params.put(name, value);
        }
    }

    public String getParam(String name) {
        if (params != null && params.containsKey(name)) {
            return params.get(name);
        } else {
            return null;
        }
    }

    public NormalEvent(int code) {
        this.code = code;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeInt(this.params.size());
        for (Map.Entry<String, String> entry : this.params.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    protected NormalEvent(Parcel in) {
        this.code = in.readInt();
        int paramsSize = in.readInt();
        this.params = new HashMap<>(paramsSize);
        for (int i = 0; i < paramsSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.params.put(key, value);
        }
    }

    public static final Parcelable.Creator<NormalEvent> CREATOR = new Parcelable.Creator<NormalEvent>() {
        @Override
        public NormalEvent createFromParcel(Parcel source) {
            return new NormalEvent(source);
        }

        @Override
        public NormalEvent[] newArray(int size) {
            return new NormalEvent[size];
        }
    };
}
