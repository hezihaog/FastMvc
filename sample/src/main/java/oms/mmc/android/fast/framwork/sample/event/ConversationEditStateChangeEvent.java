package oms.mmc.android.fast.framwork.sample.event;

/**
 * Package: oms.mmc.android.fast.framwork.sample.event
 * FileName: ConversationEditStateChangeEvent
 * Date: on 2018/2/26  下午6:33
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ConversationEditStateChangeEvent implements Event {
    public static final int EDIT_MODE = 1;
    public static final int NOMAL_MODE = 2;

    private int mode = EDIT_MODE;

    public ConversationEditStateChangeEvent setEditMode() {
        mode = EDIT_MODE;
        return this;
    }

    public ConversationEditStateChangeEvent setNomalMode() {
        mode = NOMAL_MODE;
        return this;
    }

    public boolean isEditMode() {
        return this.mode == EDIT_MODE;
    }

    public boolean isNomalMode() {
        return this.mode == NOMAL_MODE;
    }
}