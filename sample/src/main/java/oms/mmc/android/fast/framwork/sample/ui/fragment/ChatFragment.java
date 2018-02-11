package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.hzh.logger.L;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.BaseListFragment;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.widget.view.ListScrollHelper;
import oms.mmc.android.fast.framwork.widget.view.wrapper.ScrollableRecyclerViewWrapper;
import oms.mmc.android.fast.framwork.widget.view.adapter.SimpleListScrollAdapter;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.tpl.chat.ChatDateTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ChatTextReceiverTpl;
import oms.mmc.android.fast.framwork.sample.tpl.conversation.ChatTextSenderTpl;
import oms.mmc.android.fast.framwork.sample.util.FakeUtil;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataAdapter;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.view.ScrollableRecyclerView;

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
    public void onLayoutBefore() {
        super.onLayoutBefore();
    }

    @Override
    public int onLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        toolbar.setTitle("会话详情");
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
    }

    @Override
    public void onFindView(ViewFinder finder) {
        toolbar = finder.get(R.id.toolBar);
    }

    @Override
    public IDataSource<ItemDataWrapper> onListDataSourceReady() {
        return new BaseListDataSource<ItemDataWrapper>(getActivity()) {
            @Override
            protected ArrayList<ItemDataWrapper> load(int page) throws Exception {
                Thread.sleep(1500);
                ArrayList<ItemDataWrapper> model = new ArrayList<ItemDataWrapper>();
                for (int i = 0; i < 10; i++) {
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
    protected void onListScrollHelperReady(ListScrollHelper listScrollHelper) {
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
            }

            @Override
            public void onScrollBottom() {

            }
        });
    }

    @Override
    public ListScrollHelper onGetScrollHelper() {
        return new ListScrollHelper(new ScrollableRecyclerViewWrapper((ScrollableRecyclerView) recyclerView));
    }

    @Override
    public void onStartRefresh(IDataAdapter<ArrayList<ItemDataWrapper>> adapter, boolean isFirst) {
        super.onStartRefresh(adapter, isFirst);
        showWaitDialog();
    }

    @Override
    public void onEndRefresh(IDataAdapter<ArrayList<ItemDataWrapper>> adapter, ArrayList<ItemDataWrapper> result, boolean isFirst) {
        super.onEndRefresh(adapter, result, isFirst);
        hideWaitDialog();
    }
}