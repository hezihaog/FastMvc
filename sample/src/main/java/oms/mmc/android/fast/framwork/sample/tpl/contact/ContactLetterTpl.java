package oms.mmc.android.fast.framwork.sample.tpl.contact;

import android.support.v4.view.ViewCompat;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.base.BaseStickyTpl;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.sample.R;

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
    public int onLayoutId() {
        return R.layout.item_contact_letter;
    }

    @Override
    public void onFindView(ViewFinder finder) {
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