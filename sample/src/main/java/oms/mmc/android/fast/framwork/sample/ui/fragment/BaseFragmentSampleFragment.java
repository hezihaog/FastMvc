package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.base.BaseFastFragment;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.ui.activity.BaseFragmentSampleActivity;
import oms.mmc.android.fast.framwork.util.ToastUtil;
import oms.mmc.android.fast.framwork.util.ViewFinder;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: BaseFragmentSampleFragment
 * Date: on 2018/2/28  下午12:27
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class BaseFragmentSampleFragment extends BaseFastFragment {
    private String mAge;
    private TextView mAgeTv;

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        mAge = getArguments().getString(BaseFragmentSampleActivity.BUNDLE_KEY_AGE, "-1");
        ToastUtil.showToast(getActivity(), "收到跳转参数 :--> " + mAge);
    }

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_base_fragment_sample, container, false);
    }

    @Override
    public void onFindView(ViewFinder finder) {
        mAgeTv = finder.get(R.id.ageTv);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mAgeTv.setText(mAge);
    }
}
