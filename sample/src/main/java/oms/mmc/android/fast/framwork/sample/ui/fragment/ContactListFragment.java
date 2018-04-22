package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseFastRecyclerViewListFragment;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.loadview.MyContactLoadViewFactory;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactGroupChatTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactLabelTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactLetterTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactOfficialAccountsTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactRecommendTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactSumCountTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.ContactTpl;
import oms.mmc.android.fast.framwork.sample.tpl.contact.NewFriendTpl;
import oms.mmc.android.fast.framwork.sample.util.FakeUtil;
import oms.mmc.android.fast.framwork.sample.widget.SlideBar;
import oms.mmc.android.fast.framwork.util.CollectionUtil;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.factory.load.factory.ILoadViewFactory;
import oms.mmc.helper.widget.ScrollableRecyclerView;
import oms.mmc.smart.pullrefresh.SmartPullRefreshLayout;
import oms.mmc.smart.pullrefresh.SmartPullRefreshWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: HomeFragment
 * Date: on 2018/1/25  上午11:03
 * Auther: zihe
 * Descirbe:联系人fragment
 * Email: hezihao@linghit.com
 */

public class ContactListFragment extends BaseFastRecyclerViewListFragment<SmartPullRefreshLayout, ScrollableRecyclerView> {
    //推荐卡片
    public static final int TPL_RECOMMENT = 1;
    //新的好友条目
    public static final int TPL_NEW_FRIEND = 2;
    //群聊条目
    public static final int TPL_GROUP_CHAT = 3;
    //标签条目
    public static final int TPL_LABLE = 4;
    //公众号条目
    public static final int TPL_OFFICIAL_ACCOUNTS = 5;
    //悬浮的字母类型
    public static final int TPL_STICKY_LETTER = 6;
    //我的联系人条目
    public static final int TPL_CONTACT = 7;
    //联系人总数条目
    public static final int TPL_SUM_CONTACT_COUNT = 8;

    /**
     * 字母侧滑栏
     */
    private SlideBar mSlideBar;
    /**
     * 提示View
     */
    private TextView mCheckLetterView;
    /**
     * 用于保存联系人首字母在列表的位置
     */
    private HashMap<String, Integer> letterMap = new HashMap<String, Integer>();
    /**
     * 联系人数组
     */
    public static String[] mNames = new String[]{"宋江", "卢俊义", "吴用",
            "公孙胜", "关胜", "林冲", "秦明", "呼延灼", "花荣", "柴进", "李应", "朱仝", "鲁智深",
            "武松", "董平", "张清", "杨志", "徐宁", "索超", "戴宗", "刘唐", "李逵", "史进", "穆弘",
            "雷横", "李俊", "阮小二", "张横", "阮小五", "张顺", "阮小七", "杨雄", "石秀", "解珍",
            "解宝", "燕青", "朱武", "黄信", "孙立", "宣赞", "郝思文", "韩滔", "彭玘", "单廷珪",
            "魏定国", "萧让", "裴宣", "欧鹏", "邓飞", "燕顺", "杨林", "凌振", "蒋敬", "吕方",
            "郭 盛", "安道全", "皇甫端", "王英", "扈三娘", "鲍旭", "樊瑞", "孔明", "孔亮", "项充",
            "李衮", "金大坚", "马麟", "童威", "童猛", "孟康", "侯健", "陈达", "杨春", "郑天寿",
            "陶宗旺", "宋清", "乐和", "龚旺", "丁得孙", "穆春", "曹正", "宋万", "杜迁", "薛永", "施恩",
            "周通", "李忠", "杜兴", "汤隆", "邹渊", "邹润", "朱富", "朱贵", "蔡福", "蔡庆", "李立",
            "李云", "焦挺", "石勇", "孙新", "顾大嫂", "张青", "孙二娘", "王定六", "郁保四", "白胜",
            "时迁", "段景柱", "&张三", "11级李四", "12级小明"};

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
        super.onFindView(finder);
        mSlideBar = finder.get(R.id.slide_bar);
        mCheckLetterView = finder.get(R.id.check_letter);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mSlideBar.setOnSelectItemListener(new SlideBar.OnSelectItemListener() {
            @Override
            public void onItemSelect(int position, String selectLetter) {
                if (mCheckLetterView.getVisibility() != View.VISIBLE) {
                    mCheckLetterView.setVisibility(View.VISIBLE);
                }
                mCheckLetterView.setText(selectLetter);
                Integer letterStickyPosition = letterMap.get(selectLetter);
                //这里可能拿不到，因为并不是所有的字母联系人名字上都有
                if (letterStickyPosition != null) {
                    getScrollableView().scrollToPosition(letterStickyPosition);
                }
            }

            @Override
            public void onItemUnSelect() {
                mCheckLetterView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public IDataSource<BaseItemData> onListDataSourceReady() {
        return new BaseListDataSource<BaseItemData>(getActivity()) {
            @Override
            protected ArrayList<BaseItemData> load(int page, boolean isRefresh) throws Exception {
                //模拟后台数据
                Thread.sleep(1000);
                letterMap.clear();
                //拼装需要的数据集
                ArrayList<BaseItemData> models = new ArrayList<BaseItemData>();
                models.add(new ItemDataWrapper(TPL_RECOMMENT));
                //插入固定数据在顶部
                models.add(new BaseItemData(TPL_NEW_FRIEND));
                models.add(new BaseItemData(TPL_GROUP_CHAT));
                models.add(new BaseItemData(TPL_LABLE));
                models.add(new BaseItemData(TPL_OFFICIAL_ACCOUNTS));
                //开始添加联系人数据
                ArrayList<String> datas = new ArrayList<String>();
                datas.addAll(CollectionUtil.arrayToList(mNames));
                //按字母排序
                Collections.sort(datas, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        char letter = Character.toUpperCase(Pinyin.toPinyin(o1.charAt(0)).charAt(0));
                        char nextLetter = Character.toUpperCase(Pinyin.toPinyin(o2.charAt(0)).charAt(0));
                        if (letter == nextLetter) {
                            return 0;
                        } else {
                            return letter - nextLetter;
                        }
                    }
                });
                char letter = 0;
                for (int i = 0; i < datas.size(); i++) {
                    //首次肯定有一个字母
                    if (i == 0) {
                        letter = Character.toUpperCase(Pinyin.toPinyin(datas.get(i).charAt(0)).charAt(0));
                        models.add(new ItemDataWrapper(TPL_STICKY_LETTER, String.valueOf(letter)));
                    } else {
                        //如果下一个条目的首字母和上一个的不一样，则插入一条新的悬浮字母条目
                        char nextLetter = Character.toUpperCase(Pinyin.toPinyin(datas.get(i).charAt(0)).charAt(0));
                        if (!(nextLetter == letter)) {
                            letter = nextLetter;
                            models.add(new ItemDataWrapper(TPL_STICKY_LETTER, String.valueOf(letter)));
                            //记录悬浮条目的位置，后续拉动字母选择条时跳转位置
                            letterMap.put(String.valueOf(letter).toUpperCase(), models.size() - 1);
                        }
                    }
                    models.add(new ItemDataWrapper(TPL_CONTACT, FakeUtil.getRandomAvatar(i), datas.get(i)));
                }
                //最后插入一条联系人总数
                models.add(new ItemDataWrapper(TPL_SUM_CONTACT_COUNT, String.valueOf(datas.size())));
                return models;
            }
        };
    }

    @Override
    public IPullRefreshWrapper<SmartPullRefreshLayout> onInitPullRefreshWrapper(SmartPullRefreshLayout pullRefreshAbleView) {
        return new SmartPullRefreshWrapper(pullRefreshAbleView);
    }

    @Override
    public void onPullRefreshWrapperReady(IPullRefreshWrapper<SmartPullRefreshLayout> refreshWrapper, SmartPullRefreshLayout pullRefreshAbleView) {
        super.onPullRefreshWrapperReady(refreshWrapper, pullRefreshAbleView);
        pullRefreshAbleView.setEnableLoadMore(false);
    }

    /**
     * 修改懒加载进场布局，用于替换懒加载在显示之前的空白瞬间
     */
    @Override
    protected View onGetLazyLoadingView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_ziwei_load_loading, null);
    }

    @Override
    public HashMap<Integer, Class> onListTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<Integer, Class>();
        tpls.put(TPL_RECOMMENT, ContactRecommendTpl.class);
        tpls.put(TPL_NEW_FRIEND, NewFriendTpl.class);
        tpls.put(TPL_GROUP_CHAT, ContactGroupChatTpl.class);
        tpls.put(TPL_LABLE, ContactLabelTpl.class);
        tpls.put(TPL_OFFICIAL_ACCOUNTS, ContactOfficialAccountsTpl.class);
        tpls.put(TPL_STICKY_LETTER, ContactLetterTpl.class);
        tpls.put(TPL_CONTACT, ContactTpl.class);
        tpls.put(TPL_SUM_CONTACT_COUNT, ContactSumCountTpl.class);
        return tpls;
    }

    @Override
    public int onStickyTplViewTypeReady() {
        return TPL_STICKY_LETTER;
    }

    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new MyContactLoadViewFactory();
    }

    @Override
    public void onListReady() {
        super.onListReady();
        //禁止下拉刷新，使用我们自己的滚动加载更多
        getPullRefreshLayoutWrapper().setRefreshEnable();
//        getListHelper().setCanPullToRefresh(false);
    }

    @Override
    public void onListReadyAfter() {
        super.onListReadyAfter();
        //进入后马上刷新一次
//        getPullRefreshWrapper().startRefreshWithAnimation();
    }

    @Override
    public void onEndRefresh(ICommonListAdapter<BaseItemData> adapter, ArrayList<BaseItemData> result, boolean isFirst, boolean isReverse) {
        super.onEndRefresh(adapter, result, isFirst, isReverse);
        //默认隐藏，第一次刷新完毕后，就显示出来
        if (isFirst) {
            mSlideBar.setVisibility(View.VISIBLE);
        }
    }
}