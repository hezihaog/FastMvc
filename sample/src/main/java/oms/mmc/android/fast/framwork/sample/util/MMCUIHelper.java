package oms.mmc.android.fast.framwork.sample.util;

import android.app.Activity;
import android.content.Intent;

import oms.mmc.android.fast.framwork.sample.ui.activity.ActivitySampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.BaseFragmentSampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.BaseListFragmentSampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.ChatListActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.FragmentOperateActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.ListActivityModeSampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.ListActivityMultipleCheckSampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.ListActivitySampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.ListActivitySingleCheckSampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.ListStickyActivitySampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.LoadViewFactorySampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.MainActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.SampleChooseActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.SimpleLoadViewHelperUseSampleActivity;

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
        Intent intent = new Intent(activity, ListStickyActivitySampleActivity.class);
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
     * 多选例子
     */
    public static void showListActivityMultipleCheckSample(Activity activity) {
        Intent intent = new Intent(activity, ListActivityMultipleCheckSampleActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 编辑模式、普通模式例子
     */
    public static void showListActivityModeSample(Activity activity) {
        Intent intent = new Intent(activity, ListActivityModeSampleActivity.class);
        activity.startActivity(intent);
    }

    /**
     * base fragment使用
     */
    public static void showBaseFragmentSample(Activity activity, String age) {
        Intent intent = new Intent(activity, BaseFragmentSampleActivity.class);
        intent.putExtra(BaseFragmentSampleActivity.BUNDLE_KEY_AGE, age);
        activity.startActivity(intent);
    }

    /**
     * base list fragment使用
     */
    public static void showBaseListFragmentSample(Activity activity) {
        Intent intent = new Intent(activity, BaseListFragmentSampleActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 手动控制切换动画
     */
    public static void showSimpleLoadViewHelperSample(Activity activity) {
        Intent intent = new Intent(activity, SimpleLoadViewHelperUseSampleActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 列表界面带切换工厂
     */
    public static void showLoadViewFactorySample(Activity activity) {
        Intent intent = new Intent(activity, LoadViewFactorySampleActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 跳转到会话详情
     */
    public static void showConversationDetail(Activity activity) {
        Intent intent = new Intent(activity, ChatListActivity.class);
        activity.startActivity(intent);
    }

    /**
     * Fragment操作例子
     */
    public static void showFragmentOperate(Activity activity) {
        Intent intent = new Intent(activity, FragmentOperateActivity.class);
        activity.startActivity(intent);
    }
}