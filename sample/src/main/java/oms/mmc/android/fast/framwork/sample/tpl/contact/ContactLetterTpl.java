package oms.mmc.android.fast.framwork.sample.tpl.contact;

import android.widget.TextView;

import butterknife.Bind;
import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.sample.R;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl
 * FileName: ContactLetterTpl
 * Date: on 2018/1/24  下午2:08
 * Auther: zihe
 * Descirbe:联系人字母条目
 * Email: hezihao@linghit.com
 */

public class ContactLetterTpl extends BaseTpl<ItemDataWrapper> {
    @Bind(R.id.letter)
    TextView letter;

    private String data;

    @Override
    public int onLayoutId() {
        return R.layout.item_contact_letter;
    }

    @Override
    public void render() {
        data = (String) bean.getDatas().get(0);
        letter.setText(data);
    }
}