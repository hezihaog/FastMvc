package oms.mmc.android.fast.framwork.util;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: IArgumentsDelegate
 * Date: on 2018/3/5  上午11:43
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IArgumentsDelegate {
    void setExtras(Bundle extras);

    Bundle getExtras();

    String intentStr(String key);

    String intentStr(String key, String defaultValue);

    int intentInt(String key);

    int intentInt(String key, int defaultValue);

    boolean intentBoolean(String key);

    boolean intentBoolean(String key, boolean defaultValue);

    Serializable intentSerializable(String key);

    Serializable intentSerializable(String key, Serializable defaultValue);

    float intentFloat(String key);

    float intentFloat(String key, float defaultValue);

    <T extends Parcelable> T intentParcelable(String key);

    <T extends Parcelable> T intentParcelable(String key, Parcelable defaultValue);
}