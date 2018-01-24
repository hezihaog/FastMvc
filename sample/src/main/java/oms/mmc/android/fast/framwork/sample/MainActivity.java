package oms.mmc.android.fast.framwork.sample;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.base.BaseListActivity;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.ObjectWrapper;
import oms.mmc.android.fast.framwork.basiclib.util.TDevice;
import oms.mmc.android.fast.framwork.bean.TplBase;
import oms.mmc.android.fast.framwork.sample.constant.Const;
import oms.mmc.android.fast.framwork.sample.loadview.TextLoadViewFactory;
import oms.mmc.android.fast.framwork.sample.tpl.FooterTpl;
import oms.mmc.android.fast.framwork.sample.tpl.HeaderTpl;
import oms.mmc.android.fast.framwork.sample.tpl.TextTpl;
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

public class MainActivity extends BaseListActivity {
    public static final int TPL_HEADER = 0;
    public static final int TPL_TEXT = 1;
    public static final int TPL_FOOTER = 2;

    @Override
    public IDataSource onListViewDataSourceReady() {
        return new BaseListDataSource(this) {
            @Override
            protected ArrayList load(int page) throws Exception {
                //模拟后台数据
                ArrayList<String> datas = new ArrayList<String>();
                for (int i = 0; i < 15; i++) {
                    datas.add(String.valueOf(i));
                }
                //拼装需要的数据集
                ArrayList<TplBase> models = new ArrayList<TplBase>();
                models.add(new ObjectWrapper(TPL_HEADER, null));
                for (int i = 0; i < datas.size(); i++) {
                    models.add(new ObjectWrapper(TPL_TEXT, null));
                }
                models.add(new ObjectWrapper(TPL_FOOTER, null));
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
        tpls.add(HeaderTpl.class);
        tpls.add(TextTpl.class);
        tpls.add(FooterTpl.class);
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