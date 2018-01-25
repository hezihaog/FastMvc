package oms.mmc.android.fast.framwork.sample.ui.fragment;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.BaseListFragment;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.sample.R;
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
                ArrayList<BaseItemData> models = new ArrayList<BaseItemData>();
                models.add(new BaseItemData(TPL_SUBSCRIPTION));
                models.add(new BaseItemData(TPL_NEWS));
                models.add(new BaseItemData(TPL_SERVER_MSG));
                models.add(new BaseItemData(TPL_EMAIL));
                for (int i = 0; i < 15; i++) {
                    models.add(new ItemDataWrapper(TPL_CHAT, FakeUtil.getRandomAvatar(), FakeUtil.getRandomName(), FakeUtil.getRandomComment()));
                }
                models.add(new BaseItemData(TPL_WE_CHAT_TEAM_MSG));
                return models;
            }
        };
    }

    @Override
    public ArrayList<Class> onListViewTypeClassesReady() {
        ArrayList<Class> tpls = new ArrayList<Class>();
        tpls.add(TPL_WE_CHAT_TEAM_MSG, ConversationWeChatTeamChatMsgTpl.class);
        tpls.add(TPL_SUBSCRIPTION, ConversationSubscriptionMsgTpl.class);
        tpls.add(TPL_NEWS, ConversationNewsTpl.class);
        tpls.add(TPL_SERVER_MSG, ConversationServerMsgTpl.class);
        tpls.add(TPL_EMAIL, ConversationEmailTpl.class);
        tpls.add(TPL_CHAT, ConversationChatTpl.class);
        return tpls;
    }

    @Override
    public void onListViewReady() {
        super.onListViewReady();
        listView.setBackgroundColor(getResources().getColor(R.color.white));
    }
}
