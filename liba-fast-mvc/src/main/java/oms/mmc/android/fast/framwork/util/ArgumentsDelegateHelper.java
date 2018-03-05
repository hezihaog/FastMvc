package oms.mmc.android.fast.framwork.util;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: ArgumentsDelegateHelper
 * Date: on 2018/3/5  上午11:17
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ArgumentsDelegateHelper implements IArgumentsDelegate {
    private ArgumentsDelegateHelper() {
    }

    private Bundle mExtras;

    private ArgumentsDelegateHelper(Bundle extras) {
        mExtras = extras;
    }

    public static ArgumentsDelegateHelper newInstance(Bundle extras) {
        return new ArgumentsDelegateHelper(extras);
    }

    @Override
    public void setExtras(Bundle extras) {
        mExtras = extras;
    }

    @Override
    public Bundle getExtras() {
        return (mExtras != null)
                ? new Bundle(mExtras)
                : null;
    }

    @Override
    public String intentStr(String key) {
        return intentStr(key, "");
    }

    @Override
    public String intentStr(String key, String defaultValue) {
        Bundle args = getExtras();
        if (args != null) {
            return args.getString(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public int intentInt(String key) {
        return intentInt(key, 0);
    }

    @Override
    public int intentInt(String key, int defaultValue) {
        Bundle args = getExtras();
        if (args != null) {
            return args.getInt(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public boolean intentBoolean(String key) {
        return intentBoolean(key);
    }

    @Override
    public boolean intentBoolean(String key, boolean defaultValue) {
        Bundle args = getExtras();
        if (args != null) {
            return args.getBoolean(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public Serializable intentSerializable(String key) {
        return intentSerializable(key);
    }

    @Override
    public Serializable intentSerializable(String key, Serializable defaultValue) {
        Bundle args = getExtras();
        if (args != null) {
            Serializable value = args.getSerializable(key);
            if (value != null) {
                return value;
            } else {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    @Override
    public float intentFloat(String key) {
        return intentFloat(key, 0.0f);
    }

    @Override
    public float intentFloat(String key, float defaultValue) {
        Bundle args = getExtras();
        if (args != null) {
            return args.getFloat(key, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public <T extends Parcelable> T intentParcelable(String key) {
        return intentParcelable(key, null);
    }

    @Override
    public <T extends Parcelable> T intentParcelable(String key, Parcelable defaultValue) {
        Bundle args = getExtras();
        if (args != null) {
            Parcelable parcelable = args.getParcelable(key);
            if (parcelable != null) {
                return (T) parcelable;
            } else {
                return (T) defaultValue;
            }
        }
        return (T) defaultValue;
    }
}