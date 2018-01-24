package oms.mmc.android.fast.framwork.base;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.bean.BaseItemData;

public class ItemDataWrapper extends BaseItemData {
    protected ArrayList<Object> datas = new ArrayList<Object>();

    public ItemDataWrapper() {

    }

    public ItemDataWrapper(int viewType, Object... dataArr) {
        this.viewType = viewType;
        for (Object data : dataArr) {
            this.datas.add(data);
        }
    }

    public ArrayList<Object> getDatas() {
        return datas;
    }
}