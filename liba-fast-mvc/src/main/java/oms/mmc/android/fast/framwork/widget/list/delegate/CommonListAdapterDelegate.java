package oms.mmc.android.fast.framwork.widget.list.delegate;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;

/**
 * Created by wally on 18/3/25.
 */

public class CommonListAdapterDelegate extends AbsCommonListAdapterDelegate<BaseItemData, BaseTpl> {
    public CommonListAdapterDelegate(ArrayList<BaseItemData> mListData, HashMap<Integer, Class> viewTypeClassMap) {
        super(mListData, viewTypeClassMap);
    }

    @Override
    public BaseTpl createTpl(int viewType) {
        return null;
    }

    @Override
    public void updateTpl(ArrayList<BaseItemData> listData, int position) {

    }
}
