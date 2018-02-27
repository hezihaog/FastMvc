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
    }

    public void bindViewContent() {
        mToolBar.setTitle(R.string.app_name);
        mToolBar.setTitleTextColor(this.getResources().getColor(R.color.white));
        mGroupUseBtn.setOnClickListener(this);
        mBaseActivityUse.setOnClickListener(this);
        mBaseListActivityUse.setOnClickListener(this);
        mBaseListActivityStickyUse.setOnClickListener(this);
        mBaseListActivitySingleCheckUse.setOnClickListener(this);
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
                MMCUIHelper.showListActivitySample(this);
                break;
            case R.id.baseListActivityStickyUse:
                MMCUIHelper.showListActivitySampleWithSticky(this);
                break;
            case R.id.baseListActivitySingleCheckUse:
                MMCUIHelper.showListActivitySingleCheckSample(this);
                break;
        }
    }
}
