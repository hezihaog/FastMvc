package oms.mmc.android.fast.framwork.base;

import android.support.v7.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.RecyclerViewViewHelper;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: HeaderFooterAdapter
 * Date: on 2018/2/12  下午2:37
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class HeaderFooterAdapter<T extends BaseItemData> extends MultiTypeAdapter<T> implements IHeaderFooterAdapter {
    private boolean hasHeader = false;
    private boolean hasFooter = false;

    public HeaderFooterAdapter(RecyclerView recyclerView, BaseActivity activity, IDataSource<T> dataSource, HashMap<Integer, Class> itemViewClazzMap, RecyclerViewViewHelper recyclerViewHelper) {
        super(recyclerView, activity, dataSource, itemViewClazzMap, recyclerViewHelper);
    }

    @Override
    public void registerHeader(int viewType, Class headerTplClazz, BaseItemData headerData) {
        if (headerTplClazz == null) {
            throw new RuntimeException("headerTpl must not null");
        }
        if (getViewTypeClassMap().get(viewType) != null) {
            return;
        }
        getViewTypeClassMap().put(viewType, headerTplClazz);
        hasHeader = true;
        getListViewData().add(0, (T) headerData);
        notifyDataSetChanged();
    }

    @Override
    public void unRegisterHeader() {
        if (!hasHeader) {
            return;
        }
        this.getListViewData().remove(0);
        hasHeader = false;
        notifyDataSetChanged();
    }


    @Override
    public void registerFooter(int viewType, Class footerTplClazz, BaseItemData footerData) {
        if (hasFooter) {
            return;
        }
        if (footerTplClazz == null) {
            throw new RuntimeException("footerTpl must not null");
        }
        if (getViewTypeClassMap().get(viewType) != null) {
            return;
        }
        getViewTypeClassMap().put(viewType, footerTplClazz);
        List listViewData = getListViewData();
        if (listViewData.size() == 0) {
            listViewData.add(0, footerData);
        } else {
            listViewData.add(listViewData.size(), (T) footerData);
        }
        hasFooter = true;
        notifyDataSetChanged();
    }

    @Override
    public void unRegisterFooter() {
        if (!hasFooter) {
            return;
        }
        List listViewData = getListViewData();
        listViewData.remove(listViewData.size() - 1);
        hasFooter = false;
        notifyDataSetChanged();
    }

    @Override
    public boolean hasHeader() {
        return hasHeader;
    }

    @Override
    public boolean hasFooter() {
        return hasFooter;
    }
}
