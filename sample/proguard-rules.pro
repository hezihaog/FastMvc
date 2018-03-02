# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

### MMC混淆模板 #### 版本为V2.3
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-dontwarn java.awt.**
-dontwarn javax.security.**
-dontwarn java.beans.Beans
-dontwarn android.support.**
-dontwarn com.umeng.**
-dontwarn com.adsmogo.**
-dontwarn com.google.ads.internal.state.**
-dontwarn oms.mmc.ad.views.**
-dontwarn oms.mmc.pay.**
-dontwarn oms.mmc.order.**
-dontwarn oms.mmc.fu.**
-dontwarn cn.domob.android.**
 -dontwarn oms.mmc.app.BaseActionBarActivity
 -dontwarn  oms.mmc.app.BaseMMCActionBarActivity
 -dontwarn  oms.mmc.widget.MMCAdSizeView
 -dontwarn android.view.accessibility.CaptioningManager
 -dontwarn oms.mmc.ad.adapter.MMCIntersitialAdapter
-dontwarn  android.view.accessibility.**
-dontwarn  oms.mmc.ad.utils.AdsMogoInterstitialHelper
-dontwarn oms.mmc.ad.ads.**
-dontwarn com.google.android.gms.ads.**
-dontwarn safiap.**
-dontwarn oms.mmc.pay.**
-dontwarn oms.mmc.order.**
-dontwarn oms.mmc.ad.adapter.**
-dontwarn oms.mmc.app.core.**
-dontwarn oms.mmc.widget.**
-dontwarn oms.mmc.http.**
-dontwarn com.android.**
-dontwarn HttpUtils.**
-dontwarn com.google.**
-dontwarn com.alipay.**
-dontwarn com.ccit.**
-dontwarn oms.mmc.viewpaper.**
-dontwarn org.android.**
-dontwarn u.**
-dontwarn com.tencent.**
-dontwarn com.unionpay.**
-dontwarn oms.mmc.**
-dontwarn com.twm.**
-dontwarn com.taiwanmobile.**
-dontwarn com.ta.**
-dontwarn org.apache.**
-dontwarn com.mmc.feast.**
-dontwarn oms.mmc.push.**

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep class com.android.vending.billing.** { *;}
-keep class oms.mmc.database.** { *;}
-keep class oms.mmc.gm.gmpay.**{ *;}
-keep class oms.mmc.push.** { *;}
-keep public class com.adsmogo.** {*;}
-keep public class cn.domob.android.** {*;}
-keep public class com.suizong.mobile.ads.lite.** {*;}
-keep public class * extends android.app.Service -keep public class * extends android.content.BroadcastReceiver -keep class com.tencent.android.tpush.**  {* ;}

-keep public class * extends oms.mmc.push.adapter.PushAdapter { *; }

#-------- MMCHttp start ----------
#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}

#-------- MMCHttp end ----------

#------------ EventBus start ------------
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#------------ EventBus end ------------