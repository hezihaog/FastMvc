package oms.mmc.android.fast.framwork.sample.util;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executors;

import oms.mmc.android.fast.framwork.sample.event.Event;


/**
 * Created by Hezihao on 2017/6/1.
 * EventBus工具类
 */

public class EventBusUtil {
    public static EventBus init() {
        return EventBus.builder().logNoSubscriberMessages(false).sendNoSubscriberEvent(false)
                .executorService(Executors.newCachedThreadPool()).installDefaultEventBus();
    }

    private static EventBus getInstance() {
        return EventBus.getDefault();
    }

    /**
     * 注册
     *
     * @param subscriber
     */
    public static void register(Object subscriber) {
        if (!getInstance().isRegistered(subscriber)) {
            getInstance().register(subscriber);
        }
    }

    /**
     * 粘性注册
     *
     * @param subscriber
     */
//    public static void registerSticky(Object subscriber) {
//        if (!getInstance().isRegistered(subscriber)) {
//            getInstance().registerSticky(subscriber);
//        }
//    }

    /**
     * 注销
     *
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        if (getInstance().isRegistered(subscriber)) {
            getInstance().unregister(subscriber);
        }
    }

    /**
     * 发送事件
     *
     * @param event
     */
    public static void post(Object event) {
        getInstance().post(event);
    }

    /**
     * 发送粘性事件
     *
     * @param event
     */
    public static void postSticky(Object event) {
        getInstance().postSticky(event);
    }

    /**
     * 获取粘性事件
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public static <T> T getStickyEvent(Class<T> eventType) {
        return getInstance().getStickyEvent(eventType);
    }

    /**
     * 移除粘性事件
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public static <T> T removeStickyEvent(Class<T> eventType) {
        return getInstance().removeStickyEvent(eventType);
    }

    /**
     * 移除粘性事件
     *
     * @param event
     * @return
     */
    public static boolean removeStickyEvent(Object event) {
        return getInstance().removeStickyEvent(event);
    }

    /**
     * 移除所有粘性事件
     */
    public static void removeAllStickyEvents() {
        getInstance().removeAllStickyEvents();
    }

    public static void sendEvent(Event event) {
        EventBusUtil.post(event);
    }
}
