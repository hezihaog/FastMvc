package oms.mmc.android.fast.framwork.widget.list.lv;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;

/**
 * Created by wally on 18/3/25.
 */

public class CommonListViewAdapter extends BaseAdapter implements ICommonListAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}