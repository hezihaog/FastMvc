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
    public static <T extends Fragment> T newInstance(Context context, Class<T> clazz, @Nullable Bundle args) {
        Fragment fragment = Fragment.instantiate(context, clazz.getName(), args);
        if (args != null) {
            fragment.setArguments(args);
        }
        return (T) fragment;
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
        private int containerViewId;
        private Class<? extends Fragment> clazz;
        private Bundle args;

        public FragmentInfoWrapper(Class<? extends Fragment> clazz) {
            this.clazz = clazz;
        }

        public FragmentInfoWrapper(int containerViewId, Class<? extends Fragment> clazz) {
            this.containerViewId = containerViewId;
            this.clazz = clazz;
        }

        public FragmentInfoWrapper(int containerViewId, Class<? extends Fragment> clazz, Bundle args) {
            this.containerViewId = containerViewId;
            this.clazz = clazz;
            this.args = args;
        }

        public FragmentInfoWrapper(Class<? extends Fragment> clazz, Bundle args) {
            this.clazz = clazz;
            this.args = args;
        }

        public int getContainerViewId() {
            return containerViewId;
        }

        public void setContainerViewId(int containerViewId) {
            this.containerViewId = containerViewId;
        }

        public Class<? extends Fragment> getClazz() {
            return clazz;
        }

        public void setClazz(Class<? extends Fragment> clazz) {
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
