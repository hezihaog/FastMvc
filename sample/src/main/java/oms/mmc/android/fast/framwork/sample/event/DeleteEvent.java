package oms.mmc.android.fast.framwork.sample.event;

/**
 * Package: oms.mmc.android.fast.framwork.sample.event
 * FileName: DeleteEvent
 * Date: on 2018/2/28  上午9:50
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class DeleteEvent implements Event {
    private boolean isCheck;
    private int position;

    public DeleteEvent(int position, boolean isCheck) {
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