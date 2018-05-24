package oms.mmc.android.fast.framwork.sample;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.gson.Gson;
import com.hzh.logger.L;
import com.hzh.nice.http.NiceApiClient;
import com.hzh.nice.http.NiceHttpConfig;
import com.hzh.nice.http.base.Api;
import com.hzh.nice.http.base.ApiParams;
import com.hzh.nice.http.inter.Parser;
import com.hzh.nice.http.inter.Printer;
import com.hzh.nice.http.inter.Result;
import com.hzh.nice.http.okhttp3.connection.ApiByOkHttp;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.squareup.leakcanary.LeakCanary;

import oms.mmc.android.fast.framwork.BaseFastApplication;
import oms.mmc.android.fast.framwork.config.AppConfigManager;
import oms.mmc.android.fast.framwork.manager.factory.ManagerFactory;
import oms.mmc.cache.CacheManager;


/**
 * Package: PACKAGE_NAME
 * FileName: AppContext
 * Date: on 2018/1/24  下午2:22
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class AppContext extends BaseFastApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        //管理器工厂
        ManagerFactory.getInstance().init(this);
        CacheManager.getInstance().init(this);
        AppConfigManager.init(this);
        //设置演示的下拉刷新库的刷新头
        TwinklingRefreshLayout.setDefaultHeader(SinaRefreshView.class.getName());

        //http实现层
        Api httpIml = new ApiByOkHttp(getApplicationContext());
        //gson
        Parser gsonParser = new GsonParser();
        NiceApiClient.init(getApplicationContext(),
                NiceHttpConfig
                        .newBuild(httpIml, gsonParser)
                        .customPrinter(new MyLogPrinter())
                        .setDebug(BuildConfig.DEBUG).build());
    }

    /**
     * 使用者自定义配置打印Log
     */
    private static class MyLogPrinter implements Printer {

        @Override
        public void setDebug(boolean isDebug) {
            L.configAllowLog(isDebug);
        }

        @Override
        public void printRequest(String url, ApiParams params) {
            L.d(url);
            L.d(params);
        }

        @Override
        public void printResult(String clazzName, String json) {
            L.d(clazzName);
            L.json(json);
        }

        @Override
        public void v(String msg, Object... args) {
            L.v(msg, args);
        }

        @Override
        public void v(Object object) {
            L.v(object);
        }

        @Override
        public void d(String msg, Object... args) {
            L.d(msg, args);
        }

        @Override
        public void d(Object object) {
            L.d(object);
        }

        @Override
        public void i(String msg, Object... args) {
            L.i(msg, args);
        }

        @Override
        public void i(Object object) {
            L.i(object);
        }

        @Override
        public void w(String msg, Object... args) {
            L.w(msg, args);
        }

        @Override
        public void w(Object object) {
            L.w(object);
        }

        @Override
        public void e(String msg, Object... args) {
            L.e(msg, args);
        }

        @Override
        public void e(Object object) {
            L.e(object);
        }

        @Override
        public void wtf(String msg, Object... args) {
            L.wtf(msg, args);
        }

        @Override
        public void wtf(Object object) {
            L.wtf(object);
        }

        @Override
        public void json(String json) {
            L.json(json);
        }
    }

    /**
     * Gson反序列化Json转换器
     */
    private static class GsonParser implements Parser {

        @Override
        public <T extends Result> T parse(String json, Class<T> clazz) {
            return new Gson().fromJson(json, clazz);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}