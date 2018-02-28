package oms.mmc.android.fast.framwork.sample.event;

/**
 * Package: oms.mmc.android.fast.framwork.sample.event
 * FileName: ToggleModeEvent
 * Date: on 2018/2/27  下午6:33
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ToggleModeEvent implements Event {
    private boolean isEdit = false;

    public ToggleModeEvent(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public boolean isEdit() {
        return isEdit;
    }
}