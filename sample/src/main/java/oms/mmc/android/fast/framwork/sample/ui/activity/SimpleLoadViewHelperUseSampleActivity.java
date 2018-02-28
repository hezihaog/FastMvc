package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.base.BaseFastActivity;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.util.LoadStatus;
import oms.mmc.android.fast.framwork.util.SimpleLoadViewHelper;
import oms.mmc.android.fast.framwork.util.ViewFinder;

public class SimpleLoadViewHelperUseSampleActivity extends BaseFastActivity implements View.OnClickListener {
    private LinearLayout mContainerLayout;
    private TextView mContentTv;
    private Button mShowLoadingBtn;
    private Button mShowErrorBtn;
    private Button mShowEmptyBtn;
    private Button mShowSuccessBtn;

    private Handler mUiHandler;

    private SimpleLoadViewHelper mLoadViewHelper;

    private LoadStatus mLoadStatus = LoadStatus.LOADING;

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        mUiHandler = new Handler(getMainLooper());
    }

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_sample_load_view_helper_sample, container, false);
    }

    @Override
    public void onFindView(ViewFinder finder) {
        super.onFindView(finder);
        mContainerLayout = finder.get(R.id.container);
        mContentTv = finder.get(R.id.contentTv);
        //操作按钮
        mShowLoadingBtn = finder.get(R.id.showLoadingBtn);
        mShowErrorBtn = finder.get(R.id.showErrorBtn);
        mShowEmptyBtn = finder.get(R.id.showEmptyBtn);
        mShowSuccessBtn = finder.get(R.id.showSuccessBtn);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mShowLoadingBtn.setOnClickListener(this);
        mShowErrorBtn.setOnClickListener(this);
        mShowEmptyBtn.setOnClickListener(this);
        mShowSuccessBtn.setOnClickListener(this);
        //创建一个视图切换帮助类
        mLoadViewHelper = new SimpleLoadViewHelper();
        mLoadViewHelper.init(mContainerLayout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadStatus = LoadStatus.SUCCESS;
                startRequest();
            }
        });
        startRequest();
    }

    /**
     * 开始请求
     */
    private void startRequest() {
        //开始请求前，先展示加载页面
        mLoadViewHelper.showLoading();
        //开始请求数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String data = getData();
                mUiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //发生异常
                        if (data == null) {
                            mLoadViewHelper.showError();
                        } else if ("".equals(data)) {
                            //数据为空
                            mLoadViewHelper.showEmpty();
                        } else {
                            mContentTv.setText(data);
                            //获取成功，设置回设置的界面
                            mLoadViewHelper.restore();
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.showLoadingBtn:
                mLoadStatus = LoadStatus.LOADING;
                startRequest();
                break;
            case R.id.showErrorBtn:
                mLoadStatus = LoadStatus.ERROR;
                startRequest();
                break;
            case R.id.showEmptyBtn:
                mLoadStatus = LoadStatus.EMPTY;
                startRequest();
                break;
            case R.id.showSuccessBtn:
                mLoadStatus = LoadStatus.SUCCESS;
                startRequest();
                break;
        }
    }

    /**
     * 获取数据
     */
    private String getData() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mLoadStatus.ordinal() == LoadStatus.LOADING.ordinal()) {
            return "加载成功...";
        } else if (mLoadStatus.ordinal() == LoadStatus.ERROR.ordinal()) {
            //用null代表异常
            return null;
        } else if (mLoadStatus.ordinal() == LoadStatus.EMPTY.ordinal()) {
            //用空字符串代表空
            return "";
        } else if (mLoadStatus.ordinal() == LoadStatus.SUCCESS.ordinal()) {
            return "加载成功...";
        }
        return null;
    }
}