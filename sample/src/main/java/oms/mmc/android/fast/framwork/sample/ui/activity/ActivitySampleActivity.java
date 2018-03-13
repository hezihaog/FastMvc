package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.base.BaseFastActivity;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.ViewFinder;

public class ActivitySampleActivity extends BaseFastActivity {
    public static final String BUNDLE_KEY_USER_ID = "key_user_id";

    private String mUserId;
    private TextView mContent;

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        //获取传递过来的用户id
        mUserId = intentStr(ActivitySampleActivity.BUNDLE_KEY_USER_ID);
        toast("收到前面传递过来的userId -> " + mUserId);
    }

    /**
     * 该函数返回布局View
     */
    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_base_activity_sample, null);
    }

    @Override
    public void onFindView(ViewFinder finder) {
        super.onFindView(finder);
        //onFindView回调上找控件
        mContent = finder.get(R.id.content);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        //最后设置数据给控件
        mContent.setText(mUserId);
        //如果后续有继续请求，请在这里写
    }
}
