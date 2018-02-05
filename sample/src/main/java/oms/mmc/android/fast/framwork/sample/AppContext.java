package oms.mmc.android.fast.framwork.sample;

import android.content.Context;

import oms.mmc.android.fast.framwork.BaseMMCFastApplication;
import oms.mmc.android.fast.framwork.basiclib.util.WaitDialogHelper;
import oms.mmc.android.fast.framwork.sample.widget.IOSWaitDialogIml;

/**
 * Package: PACKAGE_NAME
 * FileName: AppContext
 * Date: on 2018/1/24  下午2:22
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class AppContext extends BaseMMCFastApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        WaitDialogHelper.init(this, IOSWaitDialogIml.class);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }
}