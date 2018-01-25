package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import butterknife.Bind;
import oms.mmc.android.fast.framwork.adapter.SimpleFragmentPagerAdapter;
import oms.mmc.android.fast.framwork.base.BaseActivity;
import oms.mmc.android.fast.framwork.basiclib.util.FragmentFactory;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.ui.fragment.ContactFragment;
import oms.mmc.android.fast.framwork.sample.ui.fragment.ConversationFragment;
import oms.mmc.android.fast.framwork.sample.ui.fragment.DiscoverFragment;
import oms.mmc.android.fast.framwork.sample.ui.fragment.MeFragment;

/**
 * Package: oms.mmc.android.fast.framwork.sample
 * FileName: MainActivity
 * Date: on 2018/1/18  下午5:09
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class MainActivity extends BaseActivity {
    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    @Override
    public int onLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        toolBar.setTitle(R.string.app_name);
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        //组装fragment
        Fragment conversationFragment = FragmentFactory.newInstance(getActivity(), ConversationFragment.class);
        Fragment contactFragment = FragmentFactory.newInstance(getActivity(), ContactFragment.class);
        Fragment friendCircleFragment = FragmentFactory.newInstance(getActivity(), DiscoverFragment.class);
        Fragment meFragment = FragmentFactory.newInstance(getActivity(), MeFragment.class);
        fragments.add(conversationFragment);
        fragments.add(contactFragment);
        fragments.add(friendCircleFragment);
        fragments.add(meFragment);
        //组装标题
        titles.add("会话");
        titles.add("联系人");
        titles.add("发现");
        titles.add("我");
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(viewPager.getId(), getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.size() - 1);
        for (int i = 0; i < fragments.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(fragments.get(i).getClass().getSimpleName()));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabLayout.setupWithViewPager(viewPager);
    }
}