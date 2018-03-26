package oms.mmc.android.fast.framwork.widget.list.delegate;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;

/**
 * Created by wally on 18/3/25.
 */

public class CommonListAdapterDelegate extends AbsCommonListAdapterDelegate<BaseItemData, BaseTpl> {
    private static final String TAG = CommonListAdapterDelegate.class.getSimpleName();

    public CommonListAdapterDelegate(ArrayList<BaseItemData> listData, HashMap<Integer, Class> viewTypeClassMap) {
        super(listData, viewTypeClassMap);
    }

    @Override
    public BaseTpl createTpl(int viewType) {
        //反射构造条目类
        Constructor constructor = null;
        try {
            constructor = getViewTypeClassMap().get(viewType).getConstructor();
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.e(TAG, "无法获取Tpl构造方法，请注意不能修改省略无参构造方法");
        }
        BaseTpl tpl = null;
        try {
            tpl = (BaseTpl) constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "反射实例化Tpl出错，请查看Tpl构造方法是否是无参");
        }
        if (tpl == null) {
            throw new NullPointerException("反射实例化Tpl失败，请检查Tpl构造方法是否公开，并且不是非静态匿名内部类");
        }
        return tpl;
    }

    @Override
    public void updateTpl(BaseTpl tpl, ArrayList<BaseItemData> listData, int position) {
        tpl.setBeanPosition(listData, getItem(position), position);
        try {
            tpl.render();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
