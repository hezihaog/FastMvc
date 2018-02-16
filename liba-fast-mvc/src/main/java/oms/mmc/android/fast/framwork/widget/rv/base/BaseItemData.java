package oms.mmc.android.fast.framwork.widget.rv.base;

import java.io.Serializable;

/**
 * 列表条目需要的实体基类
 * 渲染列表条目的实体需要继承ItemBase
 */
public class BaseItemData implements Serializable {
    /**
     * 用于列表多类型类型条目显示,对应listview adapter中条目类型（getItemViewType）
     */
    protected int viewType;

    public BaseItemData() {
    }

    public BaseItemData(int viewType) {
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
