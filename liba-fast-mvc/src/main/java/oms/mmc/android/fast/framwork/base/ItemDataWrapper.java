package oms.mmc.android.fast.framwork.base;

import java.util.ArrayList;
import java.util.Arrays;

import oms.mmc.android.fast.framwork.bean.BaseItemData;

public class ItemDataWrapper extends BaseItemData {
    protected ArrayList<Object> datas = new ArrayList<Object>();

    public ItemDataWrapper() {
    }

    public ItemDataWrapper(int viewType, Object... datas) {
        this.viewType = viewType;
        this.datas.addAll(Arrays.asList(datas));
    }

    public ArrayList<Object> getDatas() {
        return datas;
    }
}