package oms.mmc.android.fast.framwork.sample.util;

import android.app.Activity;
import android.content.Intent;

import oms.mmc.android.fast.framwork.sample.MainActivity;

/**
 * Package: oms.mmc.android.fast.framwork.sample.util
 * FileName: MMCUIHelper
 * Date: on 2018/1/18  下午5:11
 * Auther: zihe
 * Descirbe:跳转帮助类
 * Email: hezihao@linghit.com
 */

public class MMCUIHelper {
    /**
     * 跳转到主页
     */
    public static void showMain(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }
}