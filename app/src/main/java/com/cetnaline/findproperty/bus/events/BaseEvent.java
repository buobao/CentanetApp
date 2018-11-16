package com.cetnaline.findproperty.bus.events;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseEvent implements Parcelable {
    private int code;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
    }

    public BaseEvent() {
    }

    protected BaseEvent(Parcel in) {
        this.code = in.readInt();
    }

    public static final Parcelable.Creator<BaseEvent> CREATOR = new Parcelable.Creator<BaseEvent>() {
        @Override
        public BaseEvent createFromParcel(Parcel source) {
            return new BaseEvent(source);
        }

        @Override
        public BaseEvent[] newArray(int size) {
            return new BaseEvent[size];
        }
    };
}
