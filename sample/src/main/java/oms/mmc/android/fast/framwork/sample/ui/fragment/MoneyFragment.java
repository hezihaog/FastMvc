package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.adapter.SimpleFragmentPagerAdapter;
import oms.mmc.android.fast.framwork.base.BaseFastFragment;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.FragmentFactory;
import oms.mmc.android.fast.framwork.util.IViewFinder;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: MoneyFragment
 * Date: on 2018/4/18  下午11:39
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class MoneyFragment extends BaseFastFragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_money, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
        super.onFindView(finder);
        mTabLayout = finder.get(R.id.tabLayout);
        mViewPager = finder.get(R.id.view_pager);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        Fragment mainSampleFragment = FragmentFactory.newInstance(getActivity(), MainSampleFragment.class);
        Fragment storeSampleFragment = FragmentFactory.newInstance(getActivity(), StoreSampleFragment.class);
        fragments.add(mainSampleFragment);
        fragments.add(storeSampleFragment);
        titles.add("Main");
        titles.add("Store");
        for (int i = 0; i < fragments.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(fragments.get(i).getClass().getSimpleName()));
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mTabLayout.setupWithViewPager(mViewPager);
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(mViewPager.getId(), getChildFragmentManager(), titles, fragments);
        mViewPager.setAdapter(adapter);
    }
}
