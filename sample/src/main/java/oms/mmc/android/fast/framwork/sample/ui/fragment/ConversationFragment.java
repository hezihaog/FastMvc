package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseActivity;
import oms.mmc.android.fast.framwork.base.BaseListAdapter;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.BaseListFragment;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.recyclerview.sticky.StickyHeadersLinearLayoutManager;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.broadcast.ConversationEditStateChangeBroadcast;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationChatTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationDividerTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationEditTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationEmailTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationNewsTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationSearchTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationServerMsgTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationSubscriptionMsgTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationWeChatTeamChatMsgTpl;
import oms.mmc.android.fast.framwork.sample.util.FakeUtil;
import oms.mmc.android.fast.framwork.util.BroadcastHelper;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataAdapter;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: ConversationFragment
 * Date: on 2018/1/25  上午11:06
 * Auther: zihe
 * Descirbe:会话fragment
 * Email: hezihao@linghit.com
 */

public class ConversationFragment extends BaseListFragment<ItemDataWrapper> {
    public static final int TPL_DIVIDER = -1;
    //编辑功能条目
    public static final int TPL_SEARCH = 0;
    //编辑条目
    public static final int TPL_EDIT = 1;
    //微信团队
    public static final int TPL_WE_CHAT_TEAM_MSG = 2;
    //订阅号
    public static final int TPL_SUBSCRIPTION = 3;
    //新闻
    public static final int TPL_NEWS = 4;
    //服务通知
    public static final int TPL_SERVER_MSG = 5;
    //邮箱
    public static final int TPL_EMAIL = 6;
    //具体聊天
    public static final int TPL_CHAT = 7;

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
    public int onLayoutId() {
        return R.layout.fragment_conversation;
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
                    models.add(new BaseItemData(TPL_SEARCH));
                    models.add(new BaseItemData(TPL_DIVIDER));
                    models.add(new BaseItemData(TPL_EDIT));
                    models.add(new BaseItemData(TPL_DIVIDER));
                    models.add(new BaseItemData(TPL_SUBSCRIPTION));
                    models.add(new BaseItemData(TPL_DIVIDER));
                    models.add(new BaseItemData(TPL_NEWS));
                    models.add(new BaseItemData(TPL_DIVIDER));
                    models.add(new BaseItemData(TPL_SERVER_MSG));
                    models.add(new BaseItemData(TPL_DIVIDER));
                    models.add(new BaseItemData(TPL_EMAIL));
                    models.add(new BaseItemData(TPL_DIVIDER));
                    for (int i = 0; i < 15; i++) {
                        models.add(new ItemDataWrapper(TPL_CHAT, FakeUtil.getRandomAvatar(i), FakeUtil.getRandomName(i), FakeUtil.getRandomComment(i)));
                        models.add(new BaseItemData(TPL_DIVIDER));
                    }
                    models.add(new BaseItemData(TPL_WE_CHAT_TEAM_MSG));
                    models.add(new BaseItemData(TPL_DIVIDER));
                } else {
                    for (int i = 0; i < 15; i++) {
                        models.add(new ItemDataWrapper(TPL_CHAT, FakeUtil.getRandomAvatar(i), FakeUtil.getRandomName(i), FakeUtil.getRandomComment(i)));
                        models.add(new BaseItemData(TPL_DIVIDER));
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
        tpls.put(TPL_DIVIDER, ConversationDividerTpl.class);
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
        return new StickyHeadersLinearLayoutManager<BaseListAdapter>(getActivity());
    }

    @Override
    public int onGetStickyTplViewType() {
        return TPL_EDIT;
    }

    @Override
    public void onListReady() {
        super.onListReady();
        compatNestedScroll();
    }

    @Override
    public void onStartRefresh(IDataAdapter adapter, boolean isFirst) {
        super.onStartRefresh(adapter, isFirst);
        ((BaseActivity) getActivity()).showWaitDialog();
    }

    @Override
    public void onEndRefresh(IDataAdapter adapter, ArrayList result, boolean isFirstRefresh) {
        super.onEndRefresh(adapter, result, isFirstRefresh);
        ((BaseActivity) getActivity()).hideWiatDialog();
    }
}