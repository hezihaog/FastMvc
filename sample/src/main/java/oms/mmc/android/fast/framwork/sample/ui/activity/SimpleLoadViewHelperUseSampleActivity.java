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
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.factory.load.base.IVaryViewHelper;
import oms.mmc.factory.load.base.SimpleLoadViewHelper;

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
        //创建一个视图切换帮助类，默认会使用默认的加载动画，需要替换时，复写对应的填充方法返回对应的布局即可
        //如果太多的控件操作，建议建一个子类文件，封装在里面，同时其他界面也使用相同的切换时，方便复用
        mLoadViewHelper = new SimpleLoadViewHelper() {
            @Override
            protected View onInflateLoadingLayout(IVaryViewHelper helper, View.OnClickListener onClickRefreshListener) {
                return helper.inflate(R.layout.layout_loading_view_sample_loading);
            }

            @Override
            protected View onInflateErrorLayout(IVaryViewHelper helper, View.OnClickListener onClickRefreshListener) {
                View layout = helper.inflate(R.layout.layout_sample_load_view_error);
                TextView refreshTv = (TextView) layout.findViewById(R.id.base_list_error_refresh);
                refreshTv.setOnClickListener(onClickRefreshListener);
                return layout;
            }

            @Override
            protected View onInflateEmptyLayout(IVaryViewHelper helper, View.OnClickListener onClickRefreshListener) {
                View layout = helper.inflate(R.layout.layout_sample_load_view_empty);
                TextView refreshTv = (TextView) layout.findViewById(R.id.base_list_empty_refresh);
                refreshTv.setOnClickListener(onClickRefreshListener);
                return layout;
            }
        };
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