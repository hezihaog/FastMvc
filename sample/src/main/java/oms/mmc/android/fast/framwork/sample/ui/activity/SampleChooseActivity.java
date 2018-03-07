package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.util.MMCUIHelper;

public class SampleChooseActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolBar;
    private Button mGroupUseBtn;
    private Button mBaseActivityUse;
    private Button mBaseListActivityUse;
    private Button mBaseListActivityStickyUse;
    private Button mBaseListActivitySingleCheckUse;
    private Button mBaseListActivityMultipleCheckUse;
    private Button mBaseListActivityModeUse;
    private Button mBaseFragmentUse;
    private Button mBaseListFragmentUse;
    private Button mSimpleLoadViewUse;
    private Button mLoadViewFactoryUse;
    private Button mFragmentOperateUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_choose);
        findView();
        bindViewContent();
    }

    public void findView() {
        mToolBar = (Toolbar) findViewById(R.id.toolBar);
        mGroupUseBtn = (Button) findViewById(R.id.groupUse);
        mBaseActivityUse = (Button) findViewById(R.id.baseActivityUse);
        mBaseListActivityUse = (Button) findViewById(R.id.baseListActivityUse);
        mBaseListActivityStickyUse = (Button) findViewById(R.id.baseListActivityStickyUse);
        mBaseListActivitySingleCheckUse = (Button) findViewById(R.id.baseListActivitySingleCheckUse);
        mBaseListActivityMultipleCheckUse = (Button) findViewById(R.id.baseListActivityMultipleCheckUse);
        mBaseListActivityModeUse = (Button) findViewById(R.id.baseListActivityModeUse);
        mBaseFragmentUse = (Button) findViewById(R.id.baseFragmentUse);
        mBaseListFragmentUse = (Button) findViewById(R.id.baseListFragmentUse);
        mSimpleLoadViewUse = (Button) findViewById(R.id.simpleLoadViewUse);
        mLoadViewFactoryUse = (Button) findViewById(R.id.loadViewFactoryUse);
        mFragmentOperateUse = (Button) findViewById(R.id.fragmentOperateUse);
    }

    public void bindViewContent() {
        mToolBar.setTitle(R.string.app_name);
        mToolBar.setTitleTextColor(this.getResources().getColor(R.color.white));
        mGroupUseBtn.setOnClickListener(this);
        mBaseActivityUse.setOnClickListener(this);
        mBaseListActivityUse.setOnClickListener(this);
        mBaseListActivityStickyUse.setOnClickListener(this);
        mBaseListActivitySingleCheckUse.setOnClickListener(this);
        mBaseListActivityMultipleCheckUse.setOnClickListener(this);
        mBaseListActivityModeUse.setOnClickListener(this);
        mBaseFragmentUse.setOnClickListener(this);
        mBaseListFragmentUse.setOnClickListener(this);
        mSimpleLoadViewUse.setOnClickListener(this);
        mLoadViewFactoryUse.setOnClickListener(this);
        mFragmentOperateUse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.groupUse:
                //综合使用
                MMCUIHelper.showMain(this);
                break;
            case R.id.baseActivityUse:
                //简单界面使用
                MMCUIHelper.showActivitySample(this, "10086");
                break;
            case R.id.baseListActivityUse:
                //ListActivity使用
                MMCUIHelper.showListActivitySample(this);
                break;
            case R.id.baseListActivityStickyUse:
                //粘性ListActivity
                MMCUIHelper.showListActivitySampleWithSticky(this);
                break;
            case R.id.baseListActivitySingleCheckUse:
                //列表实现单选
                MMCUIHelper.showListActivitySingleCheckSample(this);
                break;
            case R.id.baseListActivityMultipleCheckUse:
                //列表实现多选
                MMCUIHelper.showListActivityMultipleCheckSample(this);
                break;
            case R.id.baseListActivityModeUse:
                //列表实现编辑模式、普通模式
                MMCUIHelper.showListActivityModeSample(this);
                break;
            case R.id.baseFragmentUse:
                //BaseFragment使用
                MMCUIHelper.showBaseFragmentSample(this, "18");
                break;
            case R.id.baseListFragmentUse:
                //BaseListFragment使用
                MMCUIHelper.showBaseListFragmentSample(this);
                break;
            case R.id.simpleLoadViewUse:
                //普通界面使用手动进行视图切换工厂使用
                MMCUIHelper.showSimpleLoadViewHelperSample(this);
                break;
            case R.id.loadViewFactoryUse:
                //列表界面视图切换工厂使用
                MMCUIHelper.showLoadViewFactorySample(this);
                break;
            case R.id.fragmentOperateUse:
                MMCUIHelper.showFragmentOperate(this);
                break;
            default:
                break;
        }
    }
}
