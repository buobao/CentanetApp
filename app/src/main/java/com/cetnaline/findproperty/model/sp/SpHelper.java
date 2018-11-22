package com.cetnaline.findproperty.model.sp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SpHelper {
    private Context mContext;

    private SharedPreferences mSharedPreferences = null;
    private SharedPreferences.Editor mSharedEditor;
    private static SpHelper spHelper;

    private SpHelper(Context context) {
        mContext = context;
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SpContents.SP_NAME, Activity.MODE_PRIVATE|Activity.MODE_MULTI_PROCESS);
            mSharedEditor = mSharedPreferences.edit();
        }
    }

    public static SpHelper getInstance(Context context) {
        if (spHelper == null) {
            synchronized (SpHelper.class) {
                spHelper = new SpHelper(context);
            }
        }
        return spHelper;
    }

    public void saveString(String name, String value) {
        mSharedEditor.putString(name, value);
        mSharedEditor.commit();
    }

    public void saveInt(String name, int value) {
        mSharedEditor.putInt(name, value);
        mSharedEditor.commit();
    }

    public void saveFloat(String name, float value) {
        mSharedEditor.putFloat(name, value);
        mSharedEditor.commit();
    }

    public void saveLong(String name, Long value) {
        mSharedEditor.putLong(name, value);
        mSharedEditor.commit();
    }

    public void saveBoolean(String name, boolean value) {
        mSharedEditor.putBoolean(name, value);
        mSharedEditor.commit();
    }

    public String getString(String name) {
        return mSharedPreferences.getString(name, "");
    }

    public float getFloat(String name) {
        return mSharedPreferences.getFloat(name,0.0f);
    }

    public long getLong(String name) {
        return mSharedPreferences.getLong(name, 0);
    }

    public boolean getBoolean(String name) {
        return mSharedPreferences.getBoolean(name,false);
    }

    public int getInt(String name) {
        return mSharedPreferences.getInt(name, -1);
    }

    public int getInt(String name, int def) {
        return mSharedPreferences.getInt(name, def);
    }
}
