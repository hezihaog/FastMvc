package oms.mmc.android.fast.framwork.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.base.ProxyFakeFragment;

/**
 * 使用fragment的viewpager的适配器
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private int viewPagerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private ArrayList<String> fragmentNameList;

    public SimpleFragmentPagerAdapter(int viewPagerId, FragmentManager fm, ArrayList<String> titles, ArrayList<Fragment> fragments) {
        super(fm);
        this.viewPagerId = viewPagerId;
        this.fm = fm;
        this.fragments = fragments;
        this.titles = titles;
        fragmentNameList = new ArrayList<String>();
        for (Fragment fragment : fragments) {
            fragmentNameList.add(fragment.getClass().getName());
        }
    }

    /**
     * 从外部指定保存的Fragment名字，
     *
     * @param viewPagerId
     * @param fm
     * @param titles
     * @param fragments
     * @param realFragmentNameList
     */
    public SimpleFragmentPagerAdapter(int viewPagerId, FragmentManager fm, ArrayList<String> titles, ArrayList<Fragment> fragments, ArrayList<String> realFragmentNameList) {
        super(fm);
        this.viewPagerId = viewPagerId;
        this.fm = fm;
        this.fragments = fragments;
        this.titles = titles;
        fragmentNameList = new ArrayList<String>();
        fragmentNameList.addAll(realFragmentNameList);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position % titles.size());
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    /**
     * 复写定义fragmentTag的方法
     */
    @Override
    protected String makeFragmentName(int viewId, long id) {
        return createFragmentTag(viewId, id);
    }

    /**
     * 生成tag
     */
    private static String createFragmentTag(int viewId, long id) {
        return "mmc:fragment:pager:" + viewId + ":" + id;
    }

    /**
     * 按角标获取tag
     */
    public static String getFragmentTag(int viewPagerId, int index) {
        return createFragmentTag(viewPagerId, index);
    }

    /**
     * 按Fragment名字查找MainActivity上Tab管理的fragment
     */
    public Fragment findPagerByName(String fragmentName) {
        for (int i = 0; i < fragmentNameList.size(); i++) {
            if (fragmentNameList.get(i).equals(fragmentName)) {
                return fm.findFragmentByTag(SimpleFragmentPagerAdapter.getFragmentTag(viewPagerId, i));
            } else if (fragmentNameList.get(i).equals(ProxyFakeFragment.class.getName())) {
                //是懒加载代理fragment的话，找出里面包裹的fragment
                ProxyFakeFragment proxyFragment = (ProxyFakeFragment) fm.findFragmentByTag(SimpleFragmentPagerAdapter.getFragmentTag(viewPagerId, i));
                if (fragmentNameList.get(i).equals(proxyFragment.getRealFragmentName())) {
                    return proxyFragment.getRealFragment();
                }
            }
        }
        return null;
    }

    /**
     * 按角标查找Fragment
     */
    public Fragment findByPagerIndex(int index) {
        if (index < 0) {
            return null;
        }
        return fragments.get(index);
    }
}
