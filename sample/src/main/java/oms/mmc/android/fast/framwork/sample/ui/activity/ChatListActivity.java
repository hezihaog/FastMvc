package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseFastListActivity;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataAdapter;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.tpl.chat.ChatDateTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ChatTextReceiverTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ChatTextSenderTpl;
import oms.mmc.android.fast.framwork.sample.util.FakeUtil;
import oms.mmc.android.fast.framwork.sample.wait.IOSWaitDialogFactory;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.factory.wait.factory.IWaitViewFactory;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.activity
 * FileName: ConversationDetailActivity
 * Date: on 2018/2/9  下午7:18
 * Auther: zihe
 * Descirbe:聊天界面
 * Email: hezihao@linghit.com
 */

public class ChatListActivity extends BaseFastListActivity<SwipePullRefreshLayout> {
    public static final int TPL_CHAT_DATE = 0;
    public static final int TPL_CHAT_TEXT_SENDER = 1;
    public static final int TPL_CHAT_TEXT_RECEIVER = 2;

    private Toolbar mToolbar;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_chat_list, container, false);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mToolbar.setTitle("会话内容");
        mToolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
        final GestureDetector detector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                smoothMoveToTop(getRecyclerViewHelper().isReverse());
                return true;
            }
        });
        mToolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
    }

    @Override
    public void onFindView(IViewFinder finder) {
        mToolbar = finder.get(R.id.toolBar);
    }

    @Override
    public IDataSource<BaseItemData> onListDataSourceReady() {
        return new BaseListDataSource<BaseItemData>(getActivity()) {
            @Override
            protected ArrayList<BaseItemData> load(int page, boolean isRefresh) throws Exception {
                Thread.sleep(1500);
                ArrayList<BaseItemData> model = new ArrayList<BaseItemData>();
                //可以这里做数据库判断，如果数据库中有数据，则使用数据库的，如果没有，则使用网络的
                for (int i = 0; i < 10; i++) {
                    if (page == FIRST_PAGE_NUM) {
                        String dateTime = FakeUtil.getRandomDate(i);
                        model.add(new ItemDataWrapper(TPL_CHAT_TEXT_SENDER, FakeUtil.getRandomAvatar(i), FakeUtil.getRandomName(i), "第" + i + "条" + "  --- page ::: " + page));
                        model.add(new ItemDataWrapper(TPL_CHAT_TEXT_RECEIVER, FakeUtil.getRandomAvatar(i), FakeUtil.getRandomName(i), "能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间"));
                        model.add(new ItemDataWrapper(TPL_CHAT_DATE, dateTime));
                    } else {
                        String dateTime = FakeUtil.getRandomDate(i);
                        model.add(new ItemDataWrapper(TPL_CHAT_TEXT_SENDER, FakeUtil.getRandomAvatar(i), FakeUtil.getRandomName(i), "第" + i + "条" + " --- page ::: " + page));
                        model.add(new ItemDataWrapper(TPL_CHAT_TEXT_RECEIVER, FakeUtil.getRandomAvatar(i), FakeUtil.getRandomName(i), "能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间"));
                        model.add(new ItemDataWrapper(TPL_CHAT_DATE, dateTime));
                    }
                }
                this.page = page;
                this.hasMore = true;
                return model;
            }
        };
    }

    @Override
    public HashMap<Integer, Class> onListTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<Integer, Class>();
        tpls.put(TPL_CHAT_DATE, ChatDateTpl.class);
        tpls.put(TPL_CHAT_TEXT_SENDER, ChatTextSenderTpl.class);
        tpls.put(TPL_CHAT_TEXT_RECEIVER, ChatTextReceiverTpl.class);
        return tpls;
    }

    @Override
    public RecyclerView.LayoutManager onGetListLayoutManager() {
        return new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
    }

    @Override
    public IWaitViewFactory onWaitDialogFactoryReady() {
        return new IOSWaitDialogFactory();
    }

    @Override
    public void onListReady() {
        super.onListReady();
        //开启反转布局，注意当是线性和网格布局时才有效喔，否则无效
        getListAbleDelegateHelper().reverseListLayout();
        //开启能下拉刷新，必须开启时，才能自动监听到顶，然后加载下一页（默认为true）
        getRecyclerViewHelper().setCanPullToRefresh(true);
        //禁用调加载更多条目，这里可以不需要尾部，由于设置了反转，滚动到顶部时，监听到时会自动加载下一页
        getRecyclerViewHelper().setEnableLoadMoreFooter(false);
    }

    @Override
    public void onStartRefresh(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, boolean isFirst, boolean isReverse) {
        super.onStartRefresh(adapter, isFirst, isReverse);
        if (isFirst) {
            showWaitDialog("正在加载..");
        }
    }

    @Override
    public void onEndRefresh(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, ArrayList<BaseItemData> result, boolean isFirst, boolean isReverse) {
        super.onEndRefresh(adapter, result, isFirst, isReverse);
        if (isFirst) {
            hideWaitDialog();
            //一开始滚动到底部，由于反转布局，位置0就是底部
            getRecyclerView().scrollToPosition(0);
        }
    }
}