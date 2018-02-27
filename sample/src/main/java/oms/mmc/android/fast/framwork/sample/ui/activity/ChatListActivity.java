package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzh.logger.L;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseFastListActivity;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.tpl.chat.ChatDateTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ChatTextReceiverTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ChatTextSenderTpl;
import oms.mmc.android.fast.framwork.sample.util.FakeUtil;
import oms.mmc.android.fast.framwork.sample.widget.IOSWaitDialogIml;
import oms.mmc.android.fast.framwork.base.IDataAdapter;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.factory.wait.WaitDialogController;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.adapter.SimpleListScrollAdapter;
import oms.mmc.helper.widget.ScrollableRecyclerView;
import oms.mmc.helper.wrapper.ScrollableRecyclerViewWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.activity
 * FileName: ConversationDetailActivity
 * Date: on 2018/2/9  下午7:18
 * Auther: zihe
 * Descirbe:聊天界面
 * Email: hezihao@linghit.com
 */

public class ChatListActivity extends BaseFastListActivity {
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
        mToolbar.setTitle("会话详情");
        mToolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
    }

    @Override
    public void onFindView(ViewFinder finder) {
        mToolbar = finder.get(R.id.toolBar);
    }

    @Override
    public IDataSource<BaseItemData> onListDataSourceReady() {
        return new BaseListDataSource<BaseItemData>(getActivity()) {
            @Override
            protected ArrayList<BaseItemData> load(int page) throws Exception {
                Thread.sleep(1500);
                ArrayList<BaseItemData> model = new ArrayList<BaseItemData>();
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
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public ListScrollHelper onGetScrollHelper() {
        return new ListScrollHelper(new ScrollableRecyclerViewWrapper((ScrollableRecyclerView) getRecyclerView()));
    }

    @Override
    public void onListScrollHelperReady(ListScrollHelper listScrollHelper) {
        super.onListScrollHelperReady(listScrollHelper);
        listScrollHelper.addListScrollListener(new SimpleListScrollAdapter() {
            @Override
            public void onScrolledUp() {
                L.d("onScrolledUp ::: ");
            }

            @Override
            public void onScrolledDown() {
                L.d("onScrolledDown ::: ");
            }

            @Override
            public void onScrollTop() {
                L.d("onScrollTop ::: ");
                //滚动到顶部，刷新聊天数据
                getRecyclerViewHelper().startRefresh();
            }

            @Override
            public void onScrollBottom() {
                L.d("onScrollBottom ::: ");
            }
        });
    }

    @Override
    protected WaitDialogController onGetWaitDialogController() {
        return new WaitDialogController(this, IOSWaitDialogIml.class);
    }

    @Override
    public void onListReady() {
        super.onListReady();
        getListAbleDelegateHelper().reverseListLayout();
    }

    @Override
    public void onStartRefresh(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, boolean isFirst, boolean isReverse) {
        super.onStartRefresh(adapter, isFirst, isReverse);
        if (isFirst) {
            showWaitDialog();
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