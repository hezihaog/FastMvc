package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.adapter.SimpleFragmentPagerAdapter;
import oms.mmc.android.fast.framwork.base.BaseFastActivity;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.ui.fragment.LoveFragment;
import oms.mmc.android.fast.framwork.sample.ui.fragment.LuckFragment;
import oms.mmc.android.fast.framwork.sample.ui.fragment.MoneyFragment;
import oms.mmc.android.fast.framwork.util.FragmentFactory;
import oms.mmc.android.fast.framwork.util.IViewFinder;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.activity
 * FileName: NestedFragmentSampleActivity
 * Date: on 2018/4/18  下午11:14
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class NestedFragmentSampleActivity extends BaseFastActivity {
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_nested_fragment_sample, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
        super.onFindView(finder);
        mToolbar = finder.get(R.id.toolBar);
        mTabLayout = finder.get(R.id.tabLayout);
        mViewPager = finder.get(R.id.view_pager);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
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
        for (int i = 0; i < fragments.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(fragments.get(i).getClass().getSimpleName()));
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mTabLayout.setupWithViewPager(mViewPager);
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(mViewPager.getId(), getSupportFragmentManager(), titles, fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }
        });
    }
}