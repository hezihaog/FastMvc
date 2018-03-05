package oms.mmc.android.fast.framwork.widget.rv.base;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

import oms.mmc.android.fast.framwork.util.ArgumentsDelegateHelper;
import oms.mmc.android.fast.framwork.util.IArgumentsDelegate;

/**
 * Package: oms.mmc.android.fast.framwork.widget.rv.base
 * FileName: ArgumentsDelegateTpl
 * Date: on 2018/3/5  上午11:59
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ArgumentsDelegateTpl implements IArgumentsDelegate {
    private ArgumentsDelegateHelper mArgumentsDelegateHelper;
    private Bundle mBundle;

    public ArgumentsDelegateTpl() {
        mArgumentsDelegateHelper = ensureInit();
    }

    public void setBundle(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        mBundle = bundle;
    }

    /**
     * 确保初始化
     */
    public ArgumentsDelegateHelper ensureInit() {
        if (mArgumentsDelegateHelper == null) {
            mArgumentsDelegateHelper = ArgumentsDelegateHelper.newInstance(mBundle);
        }
        return mArgumentsDelegateHelper;
    }

    @Override
    public void setExtras(Bundle extras) {
        ensureInit();
        mArgumentsDelegateHelper.setExtras(extras);
    }

    @Override
    public Bundle getExtras() {
        ensureInit();
        return mArgumentsDelegateHelper.getExtras();
    }

    @Override
    public String intentStr(String key) {
        ensureInit();
        return mArgumentsDelegateHelper.intentStr(key);
    }

    @Override
    public String intentStr(String key, String defaultValue) {
        ensureInit();
        return mArgumentsDelegateHelper.intentStr(key, defaultValue);
    }

    @Override
    public int intentInt(String key) {
        ensureInit();
        return mArgumentsDelegateHelper.intentInt(key);
    }

    @Override
    public int intentInt(String key, int defaultValue) {
        ensureInit();
        return mArgumentsDelegateHelper.intentInt(key, defaultValue);
    }

    @Override
    public boolean intentBoolean(String key) {
        ensureInit();
        return mArgumentsDelegateHelper.intentBoolean(key);
    }

    @Override
    public boolean intentBoolean(String key, boolean defaultValue) {
        ensureInit();
        return mArgumentsDelegateHelper.intentBoolean(key, defaultValue);
    }

    @Override
    public Serializable intentSerializable(String key) {
        ensureInit();
        return mArgumentsDelegateHelper.intentSerializable(key);
    }

    @Override
    public Serializable intentSerializable(String key, Serializable defaultValue) {
        ensureInit();
        return mArgumentsDelegateHelper.intentSerializable(key, defaultValue);
    }

    @Override
    public float intentFloat(String key) {
        ensureInit();
        return mArgumentsDelegateHelper.intentFloat(key);
    }

    @Override
    public float intentFloat(String key, float defaultValue) {
        ensureInit();
        return mArgumentsDelegateHelper.intentFloat(key, defaultValue);
    }

    @Override
    public <T extends Parcelable> T intentParcelable(String key) {
        ensureInit();
        return mArgumentsDelegateHelper.intentParcelable(key);
    }

    @Override
    public <T extends Parcelable> T intentParcelable(String key, Parcelable defaultValue) {
        ensureInit();
        return mArgumentsDelegateHelper.intentParcelable(key, defaultValue);
    }
}
