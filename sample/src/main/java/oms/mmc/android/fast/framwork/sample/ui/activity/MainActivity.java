package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.adapter.SimpleFragmentPagerAdapter;
import oms.mmc.android.fast.framwork.base.BaseActivity;
import oms.mmc.android.fast.framwork.base.BaseFragment;
import oms.mmc.android.fast.framwork.basiclib.lazy.PagerVisibleFragment;
import oms.mmc.android.fast.framwork.basiclib.util.FragmentFactory;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.ui.fragment.ContactFragment;
import oms.mmc.android.fast.framwork.sample.ui.fragment.ConversationFragment;
import oms.mmc.android.fast.framwork.sample.ui.fragment.FindFragment;
import oms.mmc.android.fast.framwork.sample.ui.fragment.MeFragment;

/**
 * Package: oms.mmc.android.fast.framwork.sample
 * FileName: MainActivity
 * Date: on 2018/1/18  下午5:09
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolBar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView editModeTv;

    @Override
    public int onLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onFindView(ViewFinder finder) {
        toolBar = finder.get(R.id.toolBar);
        viewPager = finder.get(R.id.viewPager);
        tabLayout = finder.get(R.id.tabLayout);
        editModeTv = finder.get(R.id.editMode);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        toolBar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
        editModeTv.setOnClickListener(this);
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        //组装fragment
        Fragment conversationFragment = FragmentFactory.newInstance(getActivity(), ConversationFragment.class);
        Fragment contactFragment = FragmentFactory.newInstance(getActivity(), ContactFragment.class);
        Fragment friendCircleFragment = FragmentFactory.newInstance(getActivity(), FindFragment.class);
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
        //fragment可见监听
        PagerVisibleFragment.OnFragmentVisibleChangeCallback visibleCallback = new PagerVisibleFragment.OnFragmentVisibleChangeCallback() {
            @Override
            public void onFragmentVisibleChange(String name, boolean isVisible) {
                //L.d("onFragmentVisibleChange ::: name --> " + name + " isVisible ::: " + isVisible);
            }
        };
        for (Fragment fragment : fragments) {
            if (fragment instanceof BaseFragment) {
                ((BaseFragment) fragment).addVisibleChangeCallback(visibleCallback);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}