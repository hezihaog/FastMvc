package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import oms.mmc.android.fast.framwork.base.BaseFastActivity;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.ui.fragment.MainSampleFragment;
import oms.mmc.android.fast.framwork.sample.ui.fragment.MyInfoSampleFragment;
import oms.mmc.android.fast.framwork.sample.ui.fragment.ShoppingCartSampleFragment;
import oms.mmc.android.fast.framwork.sample.ui.fragment.StoreSampleFragment;
import oms.mmc.android.fast.framwork.util.ViewFinder;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.activity
 * FileName: FragmentOperateActivity
 * Date: on 2018/3/7  下午6:48
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class FragmentOperateActivity extends BaseFastActivity implements RadioGroup.OnCheckedChangeListener {
    private Fragment mMainFragment;
    private Fragment mStoreFragment;
    private Fragment mShoppingCarFragment;
    private Fragment mMyInfoFragment;
    private RadioButton mMainRadioButton;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_operate_activity, container, false);
    }

    @Override
    public void onFindView(ViewFinder finder) {
        super.onFindView(finder);
        RadioGroup mainActionRadioGroup = finder.get(R.id.actionRadioGroup);
        mainActionRadioGroup.setOnCheckedChangeListener(this);
        mMainRadioButton = finder.get(R.id.mainRadioButton);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mMainRadioButton.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.actionRadioGroup) {
            //显示之前，先隐藏其他的Fragment
            hideAllFragments();
            switch (checkedId) {
                case R.id.mainRadioButton:
                    if (mMainFragment == null) {
                        mMainFragment = createFragment(MainSampleFragment.class);
                    }
                    //如果没有添加过，先添加，再展示
                    if (!mMainFragment.isAdded()) {
                        addFragment(mMainFragment, R.id.contentContainer);
                    }
                    showFragment(mMainFragment);
                    break;
                case R.id.storeRadioButton:
                    if (mStoreFragment == null) {
                        mStoreFragment = createFragment(StoreSampleFragment.class);
                    }
                    if (!mStoreFragment.isAdded()) {
                        addFragment(mStoreFragment, R.id.contentContainer);
                    }
                    showFragment(mStoreFragment);
                    break;
                case R.id.shoppingCartRadioButton:
                    if (mShoppingCarFragment == null) {
                        mShoppingCarFragment = createFragment(ShoppingCartSampleFragment.class);
                    }
                    if (!mShoppingCarFragment.isAdded()) {
                        addFragment(mShoppingCarFragment, R.id.contentContainer);
                    }
                    showFragment(mShoppingCarFragment);
                    break;
                case R.id.myInfoRadioButton:
                    if (mMyInfoFragment == null) {
                        mMyInfoFragment = createFragment(MyInfoSampleFragment.class);
                    }
                    if (!mMyInfoFragment.isAdded()) {
                        addFragment(mMyInfoFragment, R.id.contentContainer);
                    }
                    showFragment(mMyInfoFragment);
                    break;
                default:
                    break;
            }
        }
    }
}