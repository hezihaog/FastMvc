package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.magiepooh.recycleritemdecoration.ItemDecorations;
import com.github.magiepooh.recycleritemdecoration.VerticalItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseFastListFragment;
import oms.mmc.android.fast.framwork.base.BaseListAdapter;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
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
import oms.mmc.android.fast.framwork.util.IDataAdapter;
import oms.mmc.android.fast.framwork.util.IDataSource;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;
import oms.mmc.android.fast.framwork.util.ToastUtil;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeadersLinearLayoutManager;
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

public class ConversationListFragment extends BaseFastListFragment {
    //搜索条目
    public static final int TPL_SEARCH = 1;
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
                RecyclerViewViewHelper<BaseItemData> recyclerViewHelper = getRecyclerViewHelper();
                BaseListAdapter<BaseItemData> listAdapter = getListAdapter();
                int mode = intent.getIntExtra(ConversationEditStateChangeBroadcast.KEY_MODE, ConversationEditStateChangeBroadcast.NOMAL_MODE);
                if (ConversationEditStateChangeBroadcast.isEditMode(mode)) {
                    getListAdapter().setMode(BaseListAdapter.MODE_EDIT);
                    //编辑模式时不能下拉刷新
                    recyclerViewHelper.setCanPullToRefresh(false);
                } else {
                    listAdapter.setMode(BaseListAdapter.MODE_NORMAL);
                    recyclerViewHelper.setCanPullToRefresh(true);
                }
                listAdapter.notifyDataSetChanged();
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
        return new ListScrollHelper(new ScrollableRecyclerViewWrapper((ScrollableRecyclerView) getRecyclerView()));
    }

    @Override
    public void onFindView(ViewFinder finder) {
    }

    @Override
    public IDataSource<BaseItemData> onListDataSourceReady() {
        return new BaseListDataSource<BaseItemData>(getActivity()) {
            @Override
            protected ArrayList load(int page) throws Exception {
                Thread.sleep(1500);
                ArrayList<BaseItemData> models = new ArrayList<BaseItemData>();
                if (page == FIRST_PAGE_NUM) {
                    models.add(new ItemDataWrapper(TPL_SEARCH));
                    models.add(new ItemDataWrapper(TPL_EDIT));
                    models.add(new ItemDataWrapper(TPL_SUBSCRIPTION));
                    models.add(new ItemDataWrapper(TPL_NEWS));
                    models.add(new ItemDataWrapper(TPL_SERVER_MSG));
                    models.add(new ItemDataWrapper(TPL_EMAIL));
                    for (int i = 0; i < 15; i++) {
                        models.add(new ItemDataWrapper(TPL_CHAT, FakeUtil.getRandomAvatar(i), FakeUtil.getRandomName(i), FakeUtil.getRandomComment(i)));
                    }
                    models.add(new ItemDataWrapper(TPL_WE_CHAT_TEAM_MSG));
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
        tpls.put(TPL_SEARCH, ConversationSearchTpl.class);
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
        //插入一个淡红色的View作为头部
        TextView headerView = new TextView(this.getContext());
        headerView.setText("我是添加的头部布局视图");
        headerView.setGravity(Gravity.CENTER);
        headerView.setBackgroundColor(Color.parseColor("#66FF0000"));
        headerView.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, 150));
        getRecyclerViewAdapter().addHeaderView(headerView);
        //添加分隔线
        VerticalItemDecoration decoration = ItemDecorations.vertical(getActivity())
                .type(TPL_SEARCH, R.drawable.shape_conversation_item_decoration)
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
        ToastUtil.showToast(getActivity(), String.valueOf(position));
        int itemViewType = clickTpl.getItemViewType();
        if (itemViewType == TPL_CHAT) {
            MMCUIHelper.showConversationDetail(getActivity());
        }
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
        }
    }
}