package oms.mmc.android.fast.framwork.sample;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import com.hzh.logger.L;

import oms.mmc.android.fast.framwork.adapter.ScrollableLayoutFactory;
import oms.mmc.android.fast.framwork.widget.view.ListScrollHelper;
import oms.mmc.android.fast.framwork.widget.view.wrapper.ListViewScrollableViewWrapper;
import oms.mmc.android.fast.framwork.widget.view.adapter.SimpleListScrollAdapter;
import oms.mmc.android.fast.framwork.widget.view.ScrollableListView;

/**
 * Package: oms.mmc.android.fast.framwork.sample
 * FileName: TestListActivity
 * Date: on 2018/2/11  下午6:20
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class TestListActivity extends ListActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ScrollableLayoutFactory.create(this).install();
        super.onCreate(savedInstanceState);
        //无须使用布局文件
        String[] arr = {"孙悟空", "猪八戒", "唐僧", "孙悟空", "猪八戒", "唐僧", "孙悟空", "猪八戒", "唐僧"
                , "孙悟空", "猪八戒", "唐僧", "孙悟空", "猪八戒", "唐僧", "孙悟空", "猪八戒", "唐僧", "孙悟空", "猪八戒", "唐僧", "孙悟空", "猪八戒", "唐僧"};
        //创建ArrayAdapter对象
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_multiple_choice, arr);
        //设置该窗口显示列表
        setListAdapter(arrayAdapter);

        ListScrollHelper scrollHelper = new ListScrollHelper(new ListViewScrollableViewWrapper((ScrollableListView) getListView()));
        scrollHelper.addListScrollListener(new SimpleListScrollAdapter() {
            @Override
            public void onScrolledUp() {
                super.onScrolledUp();
                L.d("list view ::: 上滑");
            }

            @Override
            public void onScrolledDown() {
                super.onScrolledDown();
                L.d("list view ::: 下滑");
            }

            @Override
            public void onScrollTop() {
                super.onScrollTop();
                L.d("list view ::: 滑动到顶部啦");
            }

            @Override
            public void onScrollBottom() {
                super.onScrollBottom();
                L.d("list view ::: 滑动到底部啦");
            }
        });
        ScrollableListView listView = (ScrollableListView) getListView();
        listView.addOnListViewScrollListener(new ScrollableListView.OnListViewScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }
}
