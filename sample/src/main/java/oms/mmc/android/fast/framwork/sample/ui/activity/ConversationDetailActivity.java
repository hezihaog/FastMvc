package oms.mmc.android.fast.framwork.sample.ui.activity;

import oms.mmc.android.fast.framwork.base.BaseActivity;
import oms.mmc.android.fast.framwork.basiclib.util.FragmentFactory;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.ui.fragment.ConversationDetailFragment;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.activity
 * FileName: ConversationDetailActivity
 * Date: on 2018/2/9  下午7:18
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ConversationDetailActivity extends BaseActivity {

    @Override
    public int onLayoutId() {
        return R.layout.activity_conversation_detail;
    }

    @Override
    protected FragmentFactory.FragmentInfoWrapper onGetFragmentInfo() {
        return new FragmentFactory.FragmentInfoWrapper(ConversationDetailFragment.class, transformActivityData());
    }
}