package oms.mmc.android.fast.framwork.widget.list.delegate;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.widget.list.AdapterListenerInterface;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.helper.base.IScrollableAdapterView;

/**
 * Package: oms.mmc.android.fast.framwork.widget.list.delegate
 * FileName: IAdapterDelegater
 * Date: on 2018/3/28  下午5:16
 * Auther: zihe
 * Descirbe:适配器监听委托，用于统一适配器中的监听器统一
 * Email: hezihao@linghit.com
 */

public interface IAdapterListenerDelegate<T> extends AdapterListenerInterface {
    /**
     * 开始委托
     *
     * @param adapter 要委托的适配器
     */
    void startDelegateAdapterListener(IScrollableAdapterView scrollableAdapterView, ICommonListAdapter<T> adapter);

    ArrayList<OnScrollableViewItemClickListener> getItemClickListeners();

    ArrayList<OnScrollableViewItemLongClickListener> getItemLongClickListeners();

    boolean hasItemClickListener();

    boolean hasItemLongClickListener();
}