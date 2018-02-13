package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.magiepooh.recycleritemdecoration.ItemDecorations;
import com.github.magiepooh.recycleritemdecoration.VerticalItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseListAdapter;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.BaseListFragment;
import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.recyclerview.sticky.StickyHeadersLinearLayoutManager;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.broadcast.ConversationEditStateChangeBroadcast;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationChatTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationEditTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationEmailTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationNewsTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationSearchTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationServerMsgTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationSubscriptionMsgTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationWeChatTeamChatMsgTpl;
import oms.mmc.android.fast.framwork.sample.util.FakeUtil;
import oms.mmc.android.fast.framwork.sample.util.MMCUIHelper;
import oms.mmc.android.fast.framwork.util.BroadcastHelper;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataAdapter;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.view.ListScrollHelper;
import oms.mmc.android.fast.framwork.widget.view.ScrollableRecyclerView;
import oms.mmc.android.fast.framwork.widget.view.wrapper.ScrollableRecyclerViewWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: ConversationFragment
 * Date: on 2018/1/25  上午11:06
 * Auther: zihe
 * Descirbe:会话fragment
 * Email: hezihao@linghit.com
 */

public class ConversationFragment extends BaseListFragment<ItemDataWrapper> {
    //搜索条目，是一个header类型。
    public static final int TPL_HEADER_SEARCH = 1;
    //编辑条目
    public static final int TPL_EDIT = 2;
    //微信团队
    public static final int TPL_WE_CHAT_TEAM_MSG = 3;
    //订阅号
    public static final int TPL_SUBSCRIPTION = 4;
    //新闻
    public static final int TPL_NEWS = 5;
    //服务通知
    public static final int TPL_SERVER_MSG = 6;
    //邮箱
    public static final int TPL_EMAIL = 7;
    //具体聊天
    public static final int TPL_CHAT = 8;

    private BroadcastReceiver receiver;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int mode = intent.getIntExtra(ConversationEditStateChangeBroadcast.KEY_MODE, ConversationEditStateChangeBroadcast.NOMAL_MODE);
                if (ConversationEditStateChangeBroadcast.isEditMode(mode)) {
                    listViewAdapter.setMode(BaseListAdapter.MODE_EDIT);
                    //编辑模式时不能下拉刷新
                    recyclerViewHelper.setCanPullToRefresh(false);
                } else {
                    listViewAdapter.setMode(BaseListAdapter.MODE_NORMAL);
                    recyclerViewHelper.setCanPullToRefresh(true);
                }
                listViewAdapter.notifyDataSetChanged();
            }
        };
        BroadcastHelper.register(activity, ConversationEditStateChangeBroadcast.class.getName(), receiver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        BroadcastHelper.unRegister(getActivity(), receiver);
    }

    @Override
    public ListScrollHelper onGetScrollHelper() {
        return new ListScrollHelper(new ScrollableRecyclerViewWrapper((ScrollableRecyclerView) recyclerView));
    }

    @Override
    public void onFindView(ViewFinder finder) {
    }

    @Override
    public IDataSource onListDataSourceReady() {
        return new BaseListDataSource(getActivity()) {
            @Override
            protected ArrayList load(int page) throws Exception {
                Thread.sleep(1500);
                ArrayList<BaseItemData> models = new ArrayList<BaseItemData>();
                if (page == FIRST_PAGE_NUM) {
                    models.add(new BaseItemData(TPL_EDIT));
                    models.add(new BaseItemData(TPL_SUBSCRIPTION));
                    models.add(new BaseItemData(TPL_NEWS));
                    models.add(new BaseItemData(TPL_SERVER_MSG));
                    models.add(new BaseItemData(TPL_EMAIL));
                    for (int i = 0; i < 15; i++) {
                        models.add(new ItemDataWrapper(TPL_CHAT, FakeUtil.getRandomAvatar(i), FakeUtil.getRandomName(i), FakeUtil.getRandomComment(i)));
                    }
                    models.add(new BaseItemData(TPL_WE_CHAT_TEAM_MSG));
                } else {
                    for (int i = 0; i < 15; i++) {
                        models.add(new ItemDataWrapper(TPL_CHAT, FakeUtil.getRandomAvatar(i), FakeUtil.getRandomName(i), FakeUtil.getRandomComment(i)));
                    }
                }
                //分页，需要和后台协商，一页返回大于多少条时可以有下一页
//                this.page = page;
//                this.hasMore = datas.size() >= Const.Config.pageSize;
                this.page = page;
                this.hasMore = true;
                return models;
            }
        };
    }

    @Override
    public HashMap<Integer, Class> onListTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<Integer, Class>();
        tpls.put(TPL_EDIT, ConversationEditTpl.class);
        tpls.put(TPL_WE_CHAT_TEAM_MSG, ConversationWeChatTeamChatMsgTpl.class);
        tpls.put(TPL_SUBSCRIPTION, ConversationSubscriptionMsgTpl.class);
        tpls.put(TPL_NEWS, ConversationNewsTpl.class);
        tpls.put(TPL_SERVER_MSG, ConversationServerMsgTpl.class);
        tpls.put(TPL_EMAIL, ConversationEmailTpl.class);
        tpls.put(TPL_CHAT, ConversationChatTpl.class);
        return tpls;
    }

    @Override
    public RecyclerView.LayoutManager getListLayoutManager() {
        return new StickyHeadersLinearLayoutManager(getActivity());
    }

    @Override
    public int onGetStickyTplViewType() {
        return TPL_EDIT;
    }

    @Override
    public void onListReady() {
        super.onListReady();
        getRecyclerViewAdapter().registerHeader(TPL_HEADER_SEARCH, ConversationSearchTpl.class, new BaseItemData(TPL_HEADER_SEARCH));
        //添加分隔线
        VerticalItemDecoration decoration = ItemDecorations.vertical(getActivity())
                .type(TPL_HEADER_SEARCH, R.drawable.shape_conversation_item_decoration)
                .type(TPL_EDIT, R.drawable.shape_conversation_item_decoration)
                .type(TPL_WE_CHAT_TEAM_MSG, R.drawable.shape_conversation_item_decoration)
                .type(TPL_SUBSCRIPTION, R.drawable.shape_conversation_item_decoration)
                .type(TPL_NEWS, R.drawable.shape_conversation_item_decoration)
                .type(TPL_SERVER_MSG, R.drawable.shape_conversation_item_decoration)
                .type(TPL_EMAIL, R.drawable.shape_conversation_item_decoration)
                .type(TPL_CHAT, R.drawable.shape_conversation_item_decoration)
                .create();
        getRecyclerView().addItemDecoration(decoration);
    }

    @Override
    public void onItemClick(View view, BaseTpl clickTpl, int position) {
        int itemViewType = clickTpl.getItemViewType();
        if (itemViewType == TPL_CHAT) {
            MMCUIHelper.showConversationDetail(getActivity());
        }
    }

    @Override
    public void onStartRefresh(IDataAdapter<ArrayList<ItemDataWrapper>> adapter, boolean isFirst, boolean isReverse) {
        super.onStartRefresh(adapter, isFirst, isReverse);
        if (isFirst) {
            showWaitDialog();
        }
    }

    @Override
    public void onEndRefresh(IDataAdapter<ArrayList<ItemDataWrapper>> adapter, ArrayList<ItemDataWrapper> result, boolean isFirst, boolean isReverse) {
        super.onEndRefresh(adapter, result, isFirst, isReverse);
        if (isFirst) {
            hideWaitDialog();
        }
    }
}