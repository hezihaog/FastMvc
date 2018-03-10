package oms.mmc.android.fast.framwork.sample.wait;

import android.app.Activity;

import oms.mmc.android.fast.framwork.sample.widget.IOSWaitDialogIml;
import oms.mmc.factory.wait.WaitDialogController;
import oms.mmc.factory.wait.factory.BaseWaitDialogFactory;

/**
 * Package: oms.mmc.android.fast.framwork.sample.wait
 * FileName: IOSWaitDialogFactory
 * Date: on 2018/3/4  下午3:30
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class IOSWaitDialogFactory extends BaseWaitDialogFactory {

    @Override
    public WaitDialogController madeWaitDialogController(Activity activity) {
        return new WaitDialogController(activity, IOSWaitDialogIml.class);
    }
}