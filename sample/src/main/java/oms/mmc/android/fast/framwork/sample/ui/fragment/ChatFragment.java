package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.BaseListFragment;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.base.ListScrollHelper;
import oms.mmc.android.fast.framwork.base.RecyclerViewScrollableViewWrapper;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.tpl.chat.ChatDateTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ChatTextReceiverTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ChatTextSenderTpl;
import oms.mmc.android.fast.framwork.sample.util.FakeUtil;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: ConversationDetailFragment
 * Date: on 2018/2/9  下午7:23
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ChatFragment extends BaseListFragment<ItemDataWrapper> {
    public static final int TPL_CHAT_DATE = 0;
    public static final int TPL_CHAT_TEXT_SENDER = 1;
    public static final int TPL_CHAT_TEXT_RECEIVER = 2;

    private Toolbar toolbar;

    @Override
    public int onLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public void onFindView(ViewFinder finder) {
        toolbar = finder.get(R.id.toolBar);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        toolbar.setTitle("会话详情");
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
    }

    @Override
    public IDataSource<ItemDataWrapper> onListDataSourceReady() {
        return new BaseListDataSource<ItemDataWrapper>(getActivity()) {
            @Override
            protected ArrayList<ItemDataWrapper> load(int page) throws Exception {
                ArrayList<ItemDataWrapper> model = new ArrayList<ItemDataWrapper>();
                for (int i = 0; i < 2; i++) {
                    String dateTime = FakeUtil.getRandomDate();
                    model.add(new ItemDataWrapper(TPL_CHAT_DATE, dateTime));
                    model.add(new ItemDataWrapper(TPL_CHAT_TEXT_SENDER, FakeUtil.getRandomAvatar(), FakeUtil.getRandomName(), "能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间"));
                    model.add(new ItemDataWrapper(TPL_CHAT_TEXT_RECEIVER, FakeUtil.getRandomAvatar(), FakeUtil.getRandomName(), "能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间能不能给我一首歌的时间"));
                }
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
    public RecyclerView.LayoutManager getListLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public ListScrollHelper onGetScrollHelper() {
        return new ListScrollHelper(new RecyclerViewScrollableViewWrapper(recyclerView));
    }
}