package oms.mmc.android.fast.framwork.sample.tpl.contact;

import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseStickyTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl
 * FileName: ContactLetterTpl
 * Date: on 2018/1/24  下午2:08
 * Auther: zihe
 * Descirbe:联系人字母条目
 * Email: hezihao@linghit.com
 */

public class ContactLetterTpl extends BaseStickyTpl<ItemDataWrapper> {
    TextView letter;

    private String data;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_contact_letter, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
        super.onFindView(finder);
        letter = finder.get(R.id.letter);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
        data = (String) itemData.getDatas().get(0);
        letter.setText(data);
    }

    @Override
    public void onAttachSticky() {
        ViewCompat.setElevation(getRoot(), 10);
    }

    @Override
    public void onDetachedSticky() {
        ViewCompat.setElevation(getRoot(), 0);
    }
}