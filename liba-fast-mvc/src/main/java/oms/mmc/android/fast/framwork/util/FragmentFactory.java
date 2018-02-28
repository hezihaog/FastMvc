package oms.mmc.android.fast.framwork.util;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Package: com.hzh.lazy.fragment.sample
 * FileName: FragmentFactory
 * Date: on 2017/11/24  上午11:13
 * Auther: zihe
 * Descirbe: Fragment实例生成工厂
 * Email: hezihao@linghit.com
 */

public class FragmentFactory {
    private FragmentFactory() {
    }

    /**
     * 无需传入Arguments参数时使用
     *
     * @param context 上下文
     * @param clazz   要实例化的Fragment的Class对象
     * @return Fragment实例
     */
    public static <T extends Fragment> Fragment newInstance(Context context, Class<T> clazz) {
        return newInstance(context, clazz, null);
    }

    /**
     * 有Arguments参数时使用
     *
     * @param context 上下文
     * @param clazz   要实例化的Fragment的Class对象
     * @param args    Bundle参数
     * @return Fragment实例
     */
    public static <T extends Fragment> Fragment newInstance(Context context, Class<T> clazz, @Nullable Bundle args) {
        Fragment fragment = Fragment.instantiate(context, clazz.getName(), args);
        if (args != null) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    /**
     * 支持类对象和Arg参数包裹类
     */
    public static Fragment newInstance(Context context, FragmentInfoWrapper infoWrapper) {
        if (infoWrapper == null) {
            return null;
        }
        return newInstance(context, infoWrapper.getClazz(), infoWrapper.getArgs());
    }

    public static class FragmentInfoWrapper {
        private Class clazz;
        private Bundle args;

        public FragmentInfoWrapper(Class clazz) {
            this.clazz = clazz;
        }

        public FragmentInfoWrapper(Class clazz, Bundle args) {
            this.clazz = clazz;
            this.args = args;
        }

        public Class getClazz() {
            return clazz;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }

        public Bundle getArgs() {
            return args;
        }

        public void setArgs(Bundle args) {
            this.args = args;
        }
    }
}
