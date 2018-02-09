package oms.mmc.android.fast.framwork.base;

import java.util.List;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: RecyclerViewScroller
 * Date: on 2018/2/9  下午3:45
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class RecyclerViewScroller {
    private List<IListScrollListener> listeners;

    public RecyclerViewScroller(List<IListScrollListener> listeners) {
        this.listeners = listeners;
        setup();
    }

    private void setup() {

    }
}