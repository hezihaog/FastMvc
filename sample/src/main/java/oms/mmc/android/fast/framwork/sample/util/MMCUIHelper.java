package oms.mmc.android.fast.framwork.sample.util;

import android.app.Activity;
import android.content.Intent;

import oms.mmc.android.fast.framwork.sample.ui.activity.ActivitySampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.ChatListActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.ListActivitySampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.ListActivitySingleCheckSampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.MainActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.SampleChooseActivity;

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

    /**
     * 跳转到例子选择界面
     */
    public static void showSampleChoose(Activity activity) {
        activity.startActivity(new Intent(activity, SampleChooseActivity.class));
    }

    /**
     * 跳转到BaseFastActivity使用
     */
    public static void showActivitySample(Activity activity, String userId) {
        Intent intent = new Intent(activity, ActivitySampleActivity.class);
        intent.putExtra(ActivitySampleActivity.BUNDLE_KEY_USER_ID, userId);
        activity.startActivity(intent);
    }

    /**
     * 跳转到基础BaseFastListActivity使用
     */
    public static void showListActivitySample(Activity activity) {
        Intent intent = new Intent(activity, ListActivitySampleActivity.class);
        intent.putExtra(ListActivitySampleActivity.BUNDLE_KEY_HAS_STICKY, false);
        activity.startActivity(intent);
    }

    /**
     * 带粘性的列表
     */
    public static void showListActivitySampleWithSticky(Activity activity) {
        Intent intent = new Intent(activity, ListActivitySampleActivity.class);
        intent.putExtra(ListActivitySampleActivity.BUNDLE_KEY_HAS_STICKY, true);
        activity.startActivity(intent);
    }

    /**
     * 单选例子
     */
    public static void showListActivitySingleCheckSample(Activity activity) {
        Intent intent = new Intent(activity, ListActivitySingleCheckSampleActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 跳转到会话详情
     */
    public static void showConversationDetail(Activity activity) {
        Intent intent = new Intent(activity, ChatListActivity.class);
        activity.startActivity(intent);
    }
}