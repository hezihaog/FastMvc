package oms.mmc.android.fast.framwork.config.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Hezihao on 2017/8/4.
 * Config生成类
 */

public class ConfigFactory {
    private static final ConcurrentHashMap<Class<?>, IConfig> mConfigMap = new ConcurrentHashMap<Class<?>, IConfig>();

    private ConfigFactory() {
    }

    private static class Singleton {
        private static final ConfigFactory instance = new ConfigFactory();
    }

    public static ConfigFactory getInstance() {
        return Singleton.instance;
    }

    /**
     * 构建一个Config类
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends IConfig> T create(Class<T> clazz) {
        IConfig config = mConfigMap.get(clazz);
        if (config == null) {
            try {
                Constructor<T> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                config = constructor.newInstance();
                mConfigMap.put(clazz, config);
                return (T) config;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return (T) config;
    }
}
