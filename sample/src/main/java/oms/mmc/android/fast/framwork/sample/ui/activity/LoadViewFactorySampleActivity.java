package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.magiepooh.recycleritemdecoration.ItemDecorations;
import com.github.magiepooh.recycleritemdecoration.VerticalItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseFastListActivity;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.loadview.SampleLoadMoreViewFactory;
import oms.mmc.android.fast.framwork.sample.loadview.SampleLoadViewFactory;
import oms.mmc.android.fast.framwork.sample.tpl.sample.ListTextSampleTpl;
import oms.mmc.android.fast.framwork.sample.util.LoadStatus;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.factory.load.factory.ILoadViewFactory;

public class LoadViewFactorySampleActivity extends BaseFastListActivity implements View.OnClickListener {
    public static final int TPL_TEXT = 1;

    private Button mShowLoadingBtn;
    private Button mShowErrorBtn;
    private Button mShowEmptyBtn;
    private Button mShowSuccessBtn;

    private Handler mUiHandler;

    private LoadStatus mLoadStatus = LoadStatus.LOADING;

    public boolean hasNextPage = true;

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        mUiHandler = new Handler(getMainLooper());
    }

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_load_view_factory_sample, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
        super.onFindView(finder);
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.showLoadingBtn:
                mLoadStatus = LoadStatus.LOADING;
                getRecyclerViewHelper().refresh();
                break;
            case R.id.showErrorBtn:
                mLoadStatus = LoadStatus.ERROR;
                getRecyclerViewHelper().refresh();
                break;
            case R.id.showEmptyBtn:
                mLoadStatus = LoadStatus.EMPTY;
                getRecyclerViewHelper().refresh();
                break;
            case R.id.showSuccessBtn:
                mLoadStatus = LoadStatus.SUCCESS;
                getRecyclerViewHelper().refresh();
                break;
        }
    }

    @Override
    public IDataSource<BaseItemData> onListDataSourceReady() {
        return new BaseListDataSource<BaseItemData>(getActivity()) {

            @Override
            protected ArrayList<BaseItemData> load(int page, boolean isRefresh) throws Exception {
                ArrayList<BaseItemData> models = new ArrayList<BaseItemData>();
                //这里写清除代码只是为了例子能显示出"加载异常"的界面，因为加载异常界面显示是列表都没有数据的情况下才出现的，否则就是提示Toast而已
                //正常开发不会这样写的！
                if (isRefresh) {
//                    mUiHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            getListData().clear();
//                            getListAdapter().notifyDataSetChanged();
//                        }
//                    });
                }
                ArrayList<String> datas = getData();
                for (int i = 0; i < datas.size(); i++) {
                    String data = datas.get(i);
                    models.add(new ItemDataWrapper(TPL_TEXT, data));
                }
                //强制显示加载更多，显示我们设置LoadMoreViewFactory定义的加载更多尾部布局
//                this.page = page;
//                this.hasMore = true;

                if (hasNextPage) {
                    this.page = page;
                    this.hasMore = true;
                    //这里测试第二页没有更多
                    hasNextPage = false;
                } else {
                    this.page = page;
                    this.hasMore = false;
                }
                return models;
            }
        };
    }

    @Override
    public RecyclerView.LayoutManager onGetListLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public HashMap<Integer, Class> onListTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<Integer, Class>();
        tpls.put(TPL_TEXT, ListTextSampleTpl.class);
        return tpls;
    }

    //重写该函数，切换页面切换时的样式（加载中、异常、空）
    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new SampleLoadViewFactory();
    }

    //重写该函数，切换加载更多更换的样式（正在加载、无数据、异常）
    @Override
    public ILoadMoreViewFactory onLoadMoreViewFactoryReady() {
        return new SampleLoadMoreViewFactory();
    }

    @Override
    public void onListReady() {
        super.onListReady();
        //这里可以增加一个分隔线
        //添加分隔线
        VerticalItemDecoration decoration = ItemDecorations.vertical(getActivity())
                .type(TPL_TEXT, R.drawable.shape_conversation_item_decoration)
                .create();
        getRecyclerView().addItemDecoration(decoration);
    }

    private ArrayList<String> getData() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<String> datas = new ArrayList<String>();
        if (mLoadStatus.ordinal() == LoadStatus.LOADING.ordinal()) {
            for (int i = 0; i < 15; i++) {
                datas.add("item" + i);
            }
            return datas;
        } else if (mLoadStatus.ordinal() == LoadStatus.ERROR.ordinal()) {
            //直接抛出异常，代表异常
            throw new NullPointerException("数据异常啦");
        } else if (mLoadStatus.ordinal() == LoadStatus.EMPTY.ordinal()) {
            //直接返回空集合，代表数据为空
            return datas;
        } else if (mLoadStatus.ordinal() == LoadStatus.SUCCESS.ordinal()) {
            //加载成功，返回有数据的集合
            for (int i = 0; i < 15; i++) {
                datas.add("item" + i);
            }
            return datas;
        }
        return datas;
    }
}
