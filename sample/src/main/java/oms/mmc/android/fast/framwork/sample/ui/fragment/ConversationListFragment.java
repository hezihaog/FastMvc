package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.magiepooh.recycleritemdecoration.ItemDecorations;
import com.github.magiepooh.recycleritemdecoration.VerticalItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseFastListFragment;
import oms.mmc.android.fast.framwork.base.BaseListAdapter;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataAdapter;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.event.ConversationEditStateChangeEvent;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationChatTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationEditTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationEmailTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationNewsTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationSearchTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationServerMsgTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationSubscriptionMsgTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationWeChatTeamChatMsgTpl;
import oms.mmc.android.fast.framwork.sample.util.EventBusUtil;
import oms.mmc.android.fast.framwork.sample.util.FakeUtil;
import oms.mmc.android.fast.framwork.sample.util.MMCUIHelper;
import oms.mmc.android.fast.framwork.sample.widget.SmartPullRefreshLayout;
import oms.mmc.android.fast.framwork.sample.widget.SmartPullRefreshWrapper;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;
import oms.mmc.android.fast.framwork.widget.list.helper.AssistHelper;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeadersLinearLayoutManager;
import oms.mmc.helper.widget.ScrollableRecyclerView;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: ConversationFragment
 * Date: on 2018/1/25  上午11:06
 * Auther: zihe
 * Descirbe:会话fragment
 * Email: hezihao@linghit.com
 */

public class ConversationListFragment extends BaseFastListFragment<SmartPullRefreshLayout> {
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBusUtil.register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBusUtil.unregister(this);
    }

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_conversation_list, container, false);
    }

    @Override
    public IDataSource<BaseItemData> onListDataSourceReady() {
        return new BaseListDataSource<BaseItemData>(getActivity()) {
            @Override
            protected ArrayList<BaseItemData> load(int page, boolean isRefresh) throws Exception {
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
                if (page == 5) {
                    this.hasMore = false;
                } else {
                    this.hasMore = true;
                }
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
    public IPullRefreshWrapper<SmartPullRefreshLayout> onInitPullRefreshWrapper(SmartPullRefreshLayout pullRefreshAbleView) {
        return new SmartPullRefreshWrapper(pullRefreshAbleView);
    }

    @Override
    public void onPullRefreshWrapperReady(IPullRefreshWrapper<SmartPullRefreshLayout> refreshWrapper, SmartPullRefreshLayout pullRefreshAbleView) {
        super.onPullRefreshWrapperReady(refreshWrapper, pullRefreshAbleView);
        pullRefreshAbleView.setEnableLoadmore(false);
    }

    @Override
    public RecyclerView.LayoutManager onGetListLayoutManager() {
        return new StickyHeadersLinearLayoutManager(getActivity());
    }

    @Override
    public int onStickyTplViewTypeReady() {
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
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("headerView onClick()");
            }
        });
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
        ((ScrollableRecyclerView)getScrollableView()).addItemDecoration(decoration);
    }

    @Override
    public void onListReadyAfter() {
        super.onListReadyAfter();
        //进入后马上刷新一次
        getRefreshWrapper().startRefreshWithAnimation();
    }

    @Override
    public void onItemClick(View view, BaseTpl clickTpl, int position) {
        toast(String.valueOf(position));
        int itemViewType = clickTpl.getItemViewType();
        if (itemViewType == TPL_CHAT) {
            MMCUIHelper.showConversationDetail(getActivity());
        }
    }

    @Override
    public void onStartRefresh(IDataAdapter<BaseItemData> adapter, boolean isFirst, boolean isReverse) {
        super.onStartRefresh(adapter, isFirst, isReverse);
        if (isFirst) {
            //showWaitDialog();
        }
    }

    @Override
    public void onEndRefresh(IDataAdapter<BaseItemData> adapter, ArrayList<BaseItemData> result, boolean isFirst, boolean isReverse) {
        super.onEndRefresh(adapter, result, isFirst, isReverse);
        if (isFirst) {
            //hideWaitDialog();
        }
    }

    /**
     * 会话编辑状态改变时回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ConversationEditStateChangeEvent event) {
        RecyclerViewViewHelper<BaseItemData> recyclerViewHelper = getRecyclerViewHelper();
        BaseListAdapter listAdapter = getListAdapter();
        if (event.isEditMode()) {
            getAssistHelper().setMode(AssistHelper.MODE_EDIT);
            //编辑模式时不能下拉刷新
            recyclerViewHelper.setCanPullToRefresh(false);
        } else {
            getAssistHelper().setMode(AssistHelper.MODE_NORMAL);
            recyclerViewHelper.setCanPullToRefresh(true);
        }
        listAdapter.notifyDataSetChanged();
    }
}