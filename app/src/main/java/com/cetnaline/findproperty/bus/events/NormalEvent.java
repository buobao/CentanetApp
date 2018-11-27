package com.cetnaline.findproperty.bus.events;

import android.os.Parcel;
import android.os.Parcelable;

public class NormalEvent implements Parcelable {
    public static final int NETWORK_CONNECTED = 1;    //网络连接事件
    public static final int NETWORK_DISCONNECTED = 2;  //网络断开事件

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
    }

    public NormalEvent(int code) {
        this.code = code;
    }

    protected NormalEvent(Parcel in) {
        this.code = in.readInt();
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
