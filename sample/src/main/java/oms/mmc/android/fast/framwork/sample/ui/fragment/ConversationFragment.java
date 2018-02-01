package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseListAdapter;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.BaseListFragment;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.recyclerview.sticky.StickyHeadersLinearLayoutManager;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationChatTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationEmailTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationNewsTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationServerMsgTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationSubscriptionMsgTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ConversationWeChatTeamChatMsgTpl;
import oms.mmc.android.fast.framwork.sample.util.FakeUtil;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: ConversationFragment
 * Date: on 2018/1/25  上午11:06
 * Auther: zihe
 * Descirbe:会话fragment
 * Email: hezihao@linghit.com
 */

public class ConversationFragment extends BaseListFragment {
    //微信团队
    public static final int TPL_WE_CHAT_TEAM_MSG = 0;
    //订阅号
    public static final int TPL_SUBSCRIPTION = 1;
    //新闻
    public static final int TPL_NEWS = 2;
    //服务通知
    public static final int TPL_SERVER_MSG = 3;
    //邮箱
    public static final int TPL_EMAIL = 4;
    //具体聊天
    public static final int TPL_CHAT = 5;

    @Override
    public IDataSource onListViewDataSourceReady() {
        return new BaseListDataSource(getActivity()) {
            @Override
            protected ArrayList load(int page) throws Exception {
                Thread.sleep(1500);
                ArrayList<BaseItemData> models = new ArrayList<BaseItemData>();
                if (page == FIRST_PAGE_NUM) {
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
    public HashMap<Integer, Class> onListViewTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<Integer, Class>();
        tpls.put(TPL_WE_CHAT_TEAM_MSG, ConversationWeChatTeamChatMsgTpl.class);
        tpls.put(TPL_SUBSCRIPTION, ConversationSubscriptionMsgTpl.class);
        tpls.put(TPL_NEWS, ConversationNewsTpl.class);
        tpls.put(TPL_SERVER_MSG, ConversationServerMsgTpl.class);
        tpls.put(TPL_EMAIL, ConversationEmailTpl.class);
        tpls.put(TPL_CHAT, ConversationChatTpl.class);
        return tpls;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new StickyHeadersLinearLayoutManager<BaseListAdapter>(getActivity());
    }
}
