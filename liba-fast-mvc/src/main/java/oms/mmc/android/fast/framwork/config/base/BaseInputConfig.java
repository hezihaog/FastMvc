package oms.mmc.android.fast.framwork.config.base;

import oms.mmc.android.fast.framwork.config.inter.InputAction;

/**
 * Package: oms.mmc.android.fast.framwork.config.base
 * FileName: BaseInputConfig
 * Date: on 2018/3/15  上午11:22
 * Auther: zihe
 * Descirbe: 通用的EditText使用的配置保存
 * Email: hezihao@linghit.com
 */

public abstract class BaseInputConfig extends BaseConfig implements InputAction {
    /**
     * 获取缓存key
     */
    protected abstract String getConfigKey();

    @Override
    public void saveInput(String inputText) {
        getDiskCache().put(getConfigKey(), inputText);
    }

    @Override
    public String getInput() {
        return getDiskCache().getString(getConfigKey(), "");
    }

    @Override
    public String getInput(String defaultValue) {
        return getDiskCache().getString(getConfigKey(), defaultValue);
    }
}