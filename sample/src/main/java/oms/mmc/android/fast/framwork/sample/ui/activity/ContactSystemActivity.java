package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.base.BaseFastActivity;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.ui.fragment.ContactSystemListFragment;
import oms.mmc.android.fast.framwork.util.FragmentFactory;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: ContactSystemActivity
 * Date: on 2018/5/23  下午10:54
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class ContactSystemActivity extends BaseFastActivity {

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_contact_system, container, false);
    }

    @Override
    protected FragmentFactory.FragmentInfoWrapper onSetupFragment() {
        return new FragmentFactory.FragmentInfoWrapper(R.id.container, ContactSystemListFragment.class);
    }
}