package oms.mmc.android.fast.framwork.sample.broadcast;

import oms.mmc.android.fast.framwork.broadcast.BaseBroadcast;

/**
 * Package: oms.mmc.android.fast.framwork.sample.broadcast
 * FileName: ConversationEditBroadcast
 * Date: on 2018/2/7  下午2:40
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ConversationEditStateChangeBroadcast extends BaseBroadcast {
    public static final String KEY_MODE = "key_mode";
    public static final int EDIT_MODE = 1;
    public static final int NOMAL_MODE = 2;

    @Override
    protected String onGetBroadcastAction() {
        return ConversationEditStateChangeBroadcast.class.getName();
    }

    public ConversationEditStateChangeBroadcast setEditMode() {
        put(KEY_MODE, EDIT_MODE);
        return this;
    }

    public ConversationEditStateChangeBroadcast setNomalMode() {
        put(KEY_MODE, NOMAL_MODE);
        return this;
    }

    public static boolean isEditMode(int mode) {
        return mode == EDIT_MODE;
    }

    public static boolean isNomalMode(int mode) {
        return mode == NOMAL_MODE;
    }
}