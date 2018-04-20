package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.adapter.SimpleFragmentPagerAdapter;
import oms.mmc.android.fast.framwork.base.BaseFastFragment;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.FragmentFactory;
import oms.mmc.android.fast.framwork.util.IViewFinder;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: NestedFragmentContainerFragment
 * Date: on 2018/4/19  下午10:04
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class NestedFragmentContainerFragment extends BaseFastFragment implements RadioGroup.OnCheckedChangeListener {
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private RadioGroup mGroup;
    private RadioButton mMoneyRadioBtn;
    private RadioButton mLoveRadioBtn;
    private RadioButton mLuckRadioBtn;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_nested_fragment_contaier, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
        super.onFindView(finder);
        mToolbar = finder.get(R.id.toolBar);
        mViewPager = finder.get(R.id.view_pager);
        mGroup = finder.get(R.id.actionRadioGroup);
        mMoneyRadioBtn = finder.get(R.id.moneyRadioButton);
        mLoveRadioBtn = finder.get(R.id.loveRadioButton);
        mLuckRadioBtn = finder.get(R.id.luckRadioButton);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
        mGroup.setOnCheckedChangeListener(this);
        mMoneyRadioBtn.setChecked(true);
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        Fragment moneyFragment = FragmentFactory.newInstance(getActivity(), MoneyFragment.class);
        Fragment loveFragment = FragmentFactory.newInstance(getActivity(), LoveFragment.class);
        Fragment luckFragment = FragmentFactory.newInstance(getActivity(), LuckFragment.class);
        fragments.add(moneyFragment);
        fragments.add(loveFragment);
        fragments.add(luckFragment);
        titles.add("Money");
        titles.add("Love");
        titles.add("Luck");
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(mViewPager.getId(), getChildFragmentManager(), titles, fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mMoneyRadioBtn.setChecked(true);
                } else if (position == 1) {
                    mLoveRadioBtn.setChecked(true);
                } else if (position == 2) {
                    mLuckRadioBtn.setChecked(true);
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //切换按钮颜色
        int childCount = mGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RadioButton button = (RadioButton) mGroup.getChildAt(i);
            if (button.getId() == checkedId) {
                button.setTextColor(Color.parseColor("#FFFFFF"));
                button.setBackgroundColor(Color.parseColor("#909090"));
            } else {
                button.setTextColor(Color.parseColor("#909090"));
                button.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        }
        if (checkedId == R.id.moneyRadioButton) {
            mViewPager.setCurrentItem(0, false);
        } else if (checkedId == R.id.loveRadioButton) {
            mViewPager.setCurrentItem(1, false);
        } else if (checkedId == R.id.luckRadioButton) {
            mViewPager.setCurrentItem(2, false);
        }
    }
}
