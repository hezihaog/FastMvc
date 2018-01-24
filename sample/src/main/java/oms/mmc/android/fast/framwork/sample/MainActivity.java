package oms.mmc.android.fast.framwork.sample;

import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import butterknife.Bind;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.BaseStickyListActivity;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.basiclib.util.TDevice;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.sample.constant.Const;
import oms.mmc.android.fast.framwork.sample.loadview.TextLoadViewFactory;
import oms.mmc.android.fast.framwork.sample.tpl.CommentTpl;
import oms.mmc.android.fast.framwork.sample.tpl.HeaderTpl;
import oms.mmc.android.fast.framwork.sample.util.FakeUtil;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.ILoadViewFactory;

/**
 * Package: oms.mmc.android.fast.framwork.sample
 * FileName: MainActivity
 * Date: on 2018/1/18  下午5:09
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class MainActivity extends BaseStickyListActivity {
    //条目类型
    public static final int TPL_STICKY_HEADER = 0;
    public static final int TPL_COMMENT = 1;

    @Bind(R.id.toolBar)
    Toolbar toolBar;

    @Override
    public int onLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        toolBar.setTitle(R.string.app_name);
    }

    @Override
    public IDataSource onListViewDataSourceReady() {
        return new BaseListDataSource(getActivity()) {
            @Override
            protected ArrayList load(int page) throws Exception {
                //模拟后台数据
                Thread.sleep(1000);
                ArrayList<String> datas = new ArrayList<String>();
                for (int i = 0; i < 15; i++) {
                    datas.add(FakeUtil.getRandomComment());
                }
                //拼装需要的数据集
                ArrayList<BaseItemData> models = new ArrayList<BaseItemData>();
                if (page == FIRST_PAGE_NUM) {
                    models.add(new ItemDataWrapper(TPL_STICKY_HEADER, "i am sticky header"));
                    for (int i = 0; i < datas.size(); i++) {
                        models.add(new ItemDataWrapper(TPL_COMMENT, FakeUtil.getRandomAvatar(), datas.get(i)));
                    }
                    models.add(new ItemDataWrapper(TPL_STICKY_HEADER, "i am sticky footer"));
                } else {
                    for (int i = 0; i < datas.size(); i++) {
                        models.add(new ItemDataWrapper(TPL_COMMENT, FakeUtil.getRandomAvatar(), datas.get(i)));
                    }
                }
                //和后台协商，一页返回大于多少条时可以有下一页
                this.page = page;
                this.hasMore = datas.size() >= Const.Config.pageSize;
                return models;
            }
        };
    }

    @Override
    public ArrayList<Class> onListViewTypeClassesReady() {
        ArrayList<Class> tpls = new ArrayList<Class>();
        tpls.add(TPL_STICKY_HEADER, HeaderTpl.class);
        tpls.add(TPL_COMMENT, CommentTpl.class);
        return tpls;
    }

    @Override
    public void onListViewReady() {
        super.onListViewReady();
        listView.setDivider(getResources().getDrawable(android.R.color.transparent));
        listView.setDividerHeight((int) TDevice.dpToPixel(10));
    }

    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new TextLoadViewFactory();
    }
}