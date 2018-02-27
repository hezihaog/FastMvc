package oms.mmc.android.fast.framwork.sample.event;

/**
 * Package: oms.mmc.android.fast.framwork.sample.event
 * FileName: SingleCheckEvent
 * Date: on 2018/2/27  下午5:08
 * Auther: zihe
 * Descirbe:多选事件
 * Email: hezihao@linghit.com
 */

public class MultipleCheckEvent implements Event {
    private boolean isCheck;
    private int position;

    public MultipleCheckEvent(int position, boolean isCheck) {
        this.position = position;
        this.isCheck = isCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}