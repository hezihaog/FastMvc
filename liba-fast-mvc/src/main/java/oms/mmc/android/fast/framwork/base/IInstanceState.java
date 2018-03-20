package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IInstanceState
 * Date: on 2018/3/19  下午11:50
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IInstanceState {
    void onSaveState(Bundle stateBundle);

    void onRestoreState(Bundle stateBundle);
}