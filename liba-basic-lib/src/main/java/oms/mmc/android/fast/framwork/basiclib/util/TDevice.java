package oms.mmc.android.fast.framwork.basiclib.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.List;

import oms.mmc.android.fast.framwork.basiclib.R;
import oms.mmc.android.fast.framwork.basiclib.base.BaseApplication;

/**
 * 设备相关工具类
 */
public class TDevice {

    // 手机网络类型
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;

    public static boolean GTE_HC;
    public static boolean GTE_ICS;
    public static boolean PRE_HC;
    public static float displayDensity = 0.0F;
    private static Boolean _hasBigScreen = null;
    private static Boolean _hasCamera = null;
    private static Boolean _isTablet = null;
    private static Integer _loadFactor = null;
    private static int _pageSize = -1;

    static {
        GTE_ICS = Build.VERSION.SDK_INT >= 14;
        GTE_HC = Build.VERSION.SDK_INT >= 11;
        PRE_HC = Build.VERSION.SDK_INT < 11;
    }

    public TDevice() {
    }

    public static float dpToPixel(float dp) {
        return dp * (getDisplayMetrics().densityDpi / 160F);
    }

    public static int spToPixel(float spValue) {
        final float fontScale = getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getDefaultLoadFactor() {
        if (_loadFactor == null) {
            Integer integer = 0xf & BaseApplication.context().getResources().getConfiguration().screenLayout;
            _loadFactor = integer;
            _loadFactor = Math.max(integer, 1);
        }
        return _loadFactor;
    }

    public static float getDensity() {
        if (displayDensity == 0.0)
            displayDensity = getDisplayMetrics().density;
        return displayDensity;
    }

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displaymetrics = BaseApplication.context().getResources().getDisplayMetrics();
        return displaymetrics;
    }

    public static float getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    public static float getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    public static int[] getRealScreenSize(Activity activity) {
        int[] size = new int[2];
        int screenWidth, screenHeight;
        WindowManager w = activity.getWindowManager();
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                screenWidth = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                screenHeight = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {
            }
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17)
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                screenWidth = realSize.x;
                screenHeight = realSize.y;
            } catch (Exception ignored) {
            }
        size[0] = screenWidth;
        size[1] = screenHeight;
        return size;
    }

    public static boolean hasBigScreen() {
        boolean flag = true;
        if (_hasBigScreen == null) {
            boolean flag1;
            flag1 = (0xf & BaseApplication.context().getResources().getConfiguration().screenLayout) >= 3;
            Boolean boolean1 = flag1;
            _hasBigScreen = boolean1;
            if (!boolean1) {
                if (getDensity() <= 1.5F)
                    flag = false;
                _hasBigScreen = flag;
            }
        }
        return _hasBigScreen;
    }

    public static boolean hasCamera() {
        if (_hasCamera == null) {
            PackageManager pckMgr = BaseApplication.context().getPackageManager();
            boolean flag = pckMgr.hasSystemFeature("android.hardware.camera.front");
            boolean flag1 = pckMgr.hasSystemFeature("android.hardware.camera");
            boolean flag2;
            flag2 = flag || flag1;
            _hasCamera = flag2;
        }
        return _hasCamera;
    }

    public static boolean hasHardwareMenuKey(Context context) {
        boolean flag;
        flag = PRE_HC || GTE_ICS && ViewConfiguration.get(context).hasPermanentMenuKey();
        return flag;
    }

    public static boolean gotoGoogleMarket(Activity activity, String pck) {
        try {
            Intent intent = new Intent();
            intent.setPackage("com.android.vending");
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + pck));
            activity.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void hideSoftKeyboard(View view) {
        if (view == null)
            return;
        ((InputMethodManager) BaseApplication.context().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void copyInClipboard(@NonNull Context context, String target) {
        ClipboardManager clm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clm.setText(target);
    }

    public static String readClipboardText(Context context) {
        ClipboardManager clm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return clm.getText().toString();
    }

    public static boolean isLandscape() {
        boolean flag;
        flag = BaseApplication.context().getResources().getConfiguration().orientation == 2;
        return flag;
    }

    public static boolean isPortrait() {
        boolean flag = true;
        if (BaseApplication.context().getResources().getConfiguration().orientation != 1)
            flag = false;
        return flag;
    }

    public static boolean isTablet() {
        if (_isTablet == null) {
            boolean flag;
            flag = (0xf & BaseApplication.context().getResources().getConfiguration().screenLayout) >= 3;
            _isTablet = flag;
        }
        return _isTablet;
    }

    public static float pixelsToDp(float f) {
        return f / (getDisplayMetrics().densityDpi / 160F);
    }

    public static void showAnimatedView(View view) {
        if (PRE_HC && view != null)
            view.setPadding(0, 0, 0, 0);
    }

    public static void showSoftKeyboard(Dialog dialog) {
        dialog.getWindow().setSoftInputMode(4);
    }

    public static void showSoftKeyboard(View view) {
        ((InputMethodManager) BaseApplication.context().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);
    }

    public static void toogleSoftKeyboard(View view) {
        ((InputMethodManager) BaseApplication.context().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isSdcardReady() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static String getCurCountryLan() {
        return BaseApplication.context().getResources().getConfiguration().locale.getLanguage() + "-" + BaseApplication.context().getResources().getConfiguration().locale.getCountry();
    }

    public static boolean isZhCN() {
        String lang = BaseApplication.context().getResources().getConfiguration().locale.getCountry();
        return lang.equalsIgnoreCase("CN");
    }

    public static String percent(double p1, double p2) {
        String str;
        double p3 = p1 / p2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        str = nf.format(p3);
        return str;
    }

    public static String percent2(double p1, double p2) {
        String str;
        double p3 = p1 / p2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(0);
        str = nf.format(p3);
        return str;
    }

    public static void setFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(params);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static void cancelFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(params);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static int getVersionCode() {
        int versionCode;
        try {
            versionCode = BaseApplication.context().getPackageManager().getPackageInfo(BaseApplication.context().getPackageName(), 0).versionCode;
        } catch (NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    public static int getVersionCode(String packageName) {
        int versionCode;
        try {
            versionCode = BaseApplication.context().getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    public static String getVersionName() {
        String name;
        try {
            name = BaseApplication.context().getPackageManager().getPackageInfo(BaseApplication.context().getPackageName(), 0).versionName;
        } catch (NameNotFoundException ex) {
            name = "";
        }
        return name;
    }

    public static boolean isScreenOn() {
        PowerManager pm = (PowerManager) BaseApplication.context().getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
    }

    public static Intent getInstallApkIntent(File file) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        return intent;
    }

    public static void openDial(Context context, String number) {
        Uri uri = Uri.parse("tel:" + number);
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(it);
    }

    public static void openSMS(Context context, String smsBody, String tel) {
        Uri uri = Uri.parse("smsto:" + tel);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", smsBody);
        context.startActivity(it);
    }

    public static void openDail(Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void openSendMsg(Context context) {
        Uri uri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static String getPhoneType() {
        return Build.MODEL;
    }

    public static boolean openAppActivity(Context context, String packageName, String activityName) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, activityName);
        intent.setComponent(cn);
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 发送邮件
     *
     * @param context
     * @param subject 主题
     * @param content 内容
     * @param emails  邮件地址
     */
    public static void sendEmail(Context context, String subject, String content, String... emails) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            // 模拟器
            // intent.setType("text/plain");
            intent.setType("message/rfc822"); // 真机
            intent.putExtra(Intent.EXTRA_EMAIL, emails);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, content);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int getStatusBarHeight() {
        Class<?> c;
        Object obj;
        Field field;
        int x, sbar = 38;// 默认为38，貌似大部分是这样的
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = BaseApplication.context().getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }


    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());

        if (actionBarHeight == 0 && context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }

        return actionBarHeight;
    }

    public static boolean hasStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        return (attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    /**
     * 调用系统安装了的应用分享
     *
     * @param context
     * @param title
     * @param url
     */
    public static void showSystemShareOption(Activity context, final String title, final String url, final String urlPic) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        if (urlPic == null || urlPic.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(urlPic);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享：" + title);
        intent.putExtra(Intent.EXTRA_TEXT, title + " " + url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "选择分享"));
    }

    public static boolean hasPermission(Context context, String permission) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            if (info.requestedPermissions != null) {
                for (String p : info.requestedPermissions) {
                    if (p.equals(permission)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isMiui() {
        return DeviceHelper.isMIUI();
    }

    public static boolean isMeizu() {
        return DeviceHelper.isMeizu();
    }

    /**
     * 修改状态栏字体颜色，支持miui、flyme
     */
    public static void setStatusBarMode(Activity activity, boolean darkmode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (TDevice.isMiui()) {
                TDevice.setMiuiStatusBarDarkMode(activity, darkmode);
            }
            if (TDevice.isMeizu()) {
                TDevice.setMeizuStatusBarDarkIcon(activity, darkmode);
            }
        }
    }

    /**
     * 修改MIUI状态栏字体颜色
     *
     * @param activity
     * @param darkmode
     * @return
     */
    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        //兼容miui最新版本改回原生方式设置黑色字体
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();
            int option = decorView.getSystemUiVisibility();
            if (darkmode) {
                option |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                option &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(option);
        }
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 修改FlyMe状态栏字体颜色
     *
     * @param activity
     * @param dark
     * @return
     */
    public static boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 跳转到设置界面
     *
     * @param context
     */
    public static void goToSetting(Context context) {
        Intent intent;
        if (Build.VERSION.SDK_INT > 10) {
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.newAction.VIEW");
        }
        context.startActivity(intent);
    }

    /**
     * 获取androidmanifest文件中meta值
     *
     * @param context
     * @param name
     * @return
     */
    public static StringBuilder getMetaValue(Context context, String name) {
        String value = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(name);

            if (TextUtils.isEmpty(value) || "null".equals(value)) {
                value = appInfo.metaData.getLong(name) + "";
            }

            if (TextUtils.isEmpty(value) || "0".equals(value)) {
                value = appInfo.metaData.getInt(name) + "";

            }

            if (TextUtils.isEmpty(value) || "0".equals(value)) {
                value = appInfo.metaData.getFloat(name) + "";
            }

            if (TextUtils.isEmpty(value) || "0.0".equals(value)) {
                value = appInfo.metaData.getBoolean(name) + "";
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return new StringBuilder(value);
    }

    public static void hideStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            activity.getWindow().setAttributes(attrs);
        }
    }

    public static void showStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            activity.getWindow().setAttributes(attrs);
        }
    }

    /**
     * 根据pid获取进程名称
     */
    public static String getAppProcessName(Context context) {
        String processName;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        //        PackageManager pm = context.getPackageManager();
        for (Object aL : l) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (aL);
            try {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return null;
    }

    /**
     * 判断当前进程是不是主进程
     *
     * @param context
     * @return
     */
    public static boolean isMainProcess(Context context) {
        String processAppName = TDevice.getAppProcessName(context);
        return !(processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName()));
    }
}
