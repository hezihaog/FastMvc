package oms.mmc.android.fast.framwork.broadcast;

/**
 * Package: oms.mmc.android.fast.framwork.broadcast
 * FileName: LoadMoreBroadcase
 * Date: on 2018/2/6  下午5:37
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class LoadMoreBroadcast extends BaseBroadcast {
    public static final String BUNDLE_KEY_STATE = "load_more_state";
    /**
     * viewhelper的HashCode用来分区哪个界面
     */
    public static final String BUNDLE_KEY_HELPER_HASH = "list_helper_hash_code";

    /**
     * 加载更多的状态
     */
    public static final int NOMAL = 1;
    public static final int LOADING = 2;
    public static final int FAIL = 3;
    public static final int NO_MORE = 4;

    @Override
    protected String onGetBroadcastAction() {
        return LoadMoreBroadcast.class.getName();
    }
}