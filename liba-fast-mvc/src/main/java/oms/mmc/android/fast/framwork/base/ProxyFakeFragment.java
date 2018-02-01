package oms.mmc.android.fast.framwork.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.R;


/**
 * Created by Hezihao on 2017/7/16.
 * 代理形式懒加载fragment
 */

public class ProxyFakeFragment extends Fragment {
    private static final String REAL_FRAGMENT_NAME = "realFragmentName";

    private Fragment realFragment;
    private String realFragmentName;
    private LayoutInflater inflater;
    private boolean isRealFragmentAdded = false;
    private boolean isCurrentVisiable = false;


    public ProxyFakeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param realFragmentName 需要替换的真实fragment.
     * @return A new instance of fragment FakeFragment.
     */
    @SuppressWarnings("unused")
    public static ProxyFakeFragment newInstance(String realFragmentName) {
        ProxyFakeFragment fragment = new ProxyFakeFragment();
        Bundle args = new Bundle();
        args.putString(REAL_FRAGMENT_NAME, realFragmentName);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param realFragmentName 需要替换的真实fragment.
     * @param bundle           放入真实fragment 需要的bundle
     * @return A new instance of fragment FakeFragment.
     */
    @SuppressWarnings("unused")
    public static ProxyFakeFragment newInstance(String realFragmentName, Bundle bundle) {
        ProxyFakeFragment fragment = new ProxyFakeFragment();
        Bundle args = new Bundle();
        args.putString(REAL_FRAGMENT_NAME, realFragmentName);
        if (bundle != null) {
            args.putAll(bundle);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (TextUtils.isEmpty(realFragmentName) && getArguments() != null) {
            realFragmentName = getArguments().getString(REAL_FRAGMENT_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.frag_proxy_fake, container, false);
        setUserVisibleHint(isCurrentVisiable);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCurrentVisiable = isVisibleToUser;
        if (TextUtils.isEmpty(realFragmentName) && getArguments() != null) {
            realFragmentName = getArguments().getString(REAL_FRAGMENT_NAME);
        }
        if (!TextUtils.isEmpty(realFragmentName) && isVisibleToUser && !isRealFragmentAdded) {
            getRealFragment();
            if (inflater != null) {
                addRealFragment();
            }
        }
        if (isRealFragmentAdded) {
            realFragment.setUserVisibleHint(isVisibleToUser);
        }
    }

    /**
     * 获取对应的真正的fragment实体
     *
     * @return 真正的fragment实体
     */
    public Fragment getRealFragment() {
        if (TextUtils.isEmpty(realFragmentName) && getArguments() != null) {
            realFragmentName = getArguments().getString(REAL_FRAGMENT_NAME);
        }
        if (!TextUtils.isEmpty(realFragmentName) && realFragment == null) {
            try {
                realFragment = (Fragment) Class.forName(realFragmentName).newInstance();
                realFragment.setArguments(getArguments());
                return realFragment;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else if (realFragment != null) {
            return realFragment;
        } else {
            return null;
        }
    }

    public String getRealFragmentName() {
        return realFragmentName;
    }

    private void addRealFragment() {
        if (realFragment != null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.fakeContainer, realFragment)
                    .commit();
            getChildFragmentManager().executePendingTransactions();
            isRealFragmentAdded = true;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (TextUtils.isEmpty(realFragmentName) && getArguments() != null) {
            realFragmentName = getArguments().getString(REAL_FRAGMENT_NAME);
        }
    }
}