package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.adapter.SimpleFragmentPagerAdapter;
import oms.mmc.android.fast.framwork.base.BaseFastFragment;
import oms.mmc.android.fast.framwork.base.BaseFastRecyclerViewListFragment;
import oms.mmc.android.fast.framwork.lazy.PagerVisibleFragment;
import oms.mmc.android.fast.framwork.sample.AppCompatSupportFastActivity;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.ui.fragment.ContactListFragment;
import oms.mmc.android.fast.framwork.sample.ui.fragment.ConversationListFragment;
import oms.mmc.android.fast.framwork.sample.ui.fragment.FindFragment;
import oms.mmc.android.fast.framwork.sample.ui.fragment.MeFragment;
import oms.mmc.android.fast.framwork.util.FragmentFactory;
import oms.mmc.android.fast.framwork.util.IViewFinder;

/**
 * Package: oms.mmc.android.fast.framwork.sample
 * FileName: MainActivity
 * Date: on 2018/1/18  下午5:09
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class MainActivity extends AppCompatSupportFastActivity implements View.OnClickListener {
    private Toolbar toolBar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton floatingActionButton;
    private SimpleFragmentPagerAdapter viewPagerAdapter;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
        toolBar = finder.get(R.id.toolBar);
        viewPager = finder.get(R.id.viewPager);
        tabLayout = finder.get(R.id.tabLayout);
        floatingActionButton = finder.get(R.id.floatingActionButton);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        toolBar.setTitle(R.string.app_name);
        toolBar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
        getViewFinder().setOnClickListener(this, R.id.floatingActionButton);
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        //组装fragment
        Fragment conversationFragment = FragmentFactory.newInstance(getActivity(), ConversationListFragment.class);
        Fragment contactFragment = FragmentFactory.newInstance(getActivity(), ContactListFragment.class);
        Fragment friendCircleFragment = FragmentFactory.newInstance(getActivity(), FindFragment.class);
        Fragment meFragment = FragmentFactory.newInstance(getActivity(), MeFragment.class);
        fragments.add(conversationFragment);
        fragments.add(contactFragment);
        fragments.add(friendCircleFragment);
        fragments.add(meFragment);
        //组装标题
        titles.add(getString(R.string.main_tab_conversation));
        titles.add(getString(R.string.main_tab_contact));
        titles.add(getString(R.string.main_tab_find));
        titles.add(getString(R.string.main_tab_me));
        viewPagerAdapter = new SimpleFragmentPagerAdapter(viewPager.getId(), getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(fragments.size() - 1);
        for (int i = 0; i < fragments.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(fragments.get(i).getClass().getSimpleName()));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Fragment fragment = viewPagerAdapter.findByPagerIndex(position);
                if (fragment != null) {
                    if (SimpleFragmentPagerAdapter.isTargetFragment(fragment, ConversationListFragment.class)
                            || SimpleFragmentPagerAdapter.isTargetFragment(fragment, ContactListFragment.class)) {
                        floatingActionButton.show();
                    } else {
                        floatingActionButton.hide();
                    }
                }
            }
        });
        //fragment可见监听
        PagerVisibleFragment.OnFragmentVisibleChangeCallback visibleCallback = new PagerVisibleFragment.OnFragmentVisibleChangeCallback() {
            @Override
            public void onFragmentVisibleChange(String name, boolean isVisible) {
            }
        };
        for (Fragment fragment : fragments) {
            if (fragment instanceof BaseFastFragment) {
                ((BaseFastFragment) fragment).addVisibleChangeCallback(visibleCallback);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int currentItem = viewPager.getCurrentItem();
        Fragment fragment = viewPagerAdapter.findByPagerIndex(currentItem);
        if (fragment != null && (fragment instanceof ConversationListFragment || fragment instanceof ContactListFragment)) {
            BaseFastRecyclerViewListFragment listFragment = (BaseFastRecyclerViewListFragment) fragment;
            listFragment.smoothMoveToTop(listFragment.getListHelper().isReverse());
        }
    }
}