# FastMvc开发框架说明文档

------

| 更新内容        | 更新时间   |  作者  |
| --------   | -----:  | :----:  |
|文档初始化编写|2018.03.05|子和

### 以前的框架，我们是怎样进行开发的？

 - 以前的都是在MMCSDK上的基类Activity、Fragment上进行子类开发，但是例如 **查找控件** ，**给控件进行相关设置**，没有一个明确的地方进行设置，大家都是自己起函数或者直接在查找控件后，直接就写上了。

### 这样的方式有什么问题呢？

- 没有一个约定俗成的函数名，大量相同的操作，每个人都是自己写一套，下一个组员进行迭代开发的时候，想找相关查找控件时或者进行相关操作时，首先只能搜索onCreate方法，然后从中再来看，可能是写一起的，也有可能是分拆成其他函数了，每个界面都要重新找一遍，造成了时间浪费，如果找不到，那只能找上次开发的组员了。
- 如果加上函数安放顺序也可能不一样，函数调用顺序和代码上的不一致，看逻辑时只能跳来跳去，一个Activity上千行的话（老项目还真有），可想而知，那感觉真难受。

### 所以？

- 要改变这样的现状。让项目迭代组员沟通量小，代码风格统一，BUG量减少。所以，我定义了这样的一套开发框架，规定好共有的逻辑和回调方法，统一开发书写样式。

- 由于面对的都是一套Api，Activity切换到Fragment只需要改继承的基类，从BaseFastActivity改为BaseFastFragment即可，无报错，切换基本无成本。

- 使用时，如果是已经更新了mmcsdk到2.0.0，则依赖此库，Activity使用BaseFastActivity进行开发。如果还是旧版本，由于以前都需要继承MMC开头的Activity不能继承本库的BaseFastActivity，所以要兼容旧版本，使用继承BaseFastFragment进行嵌入到之前的Activity进行开发。

------

# 使用
1. gradle 引用

```java
        compile 'oms.mmc:fast-mvc:1.0.2-SNAPSHOT@aar'
    //-------------------------- fast mvc 依赖的库 start ----------------------------
    //生命周期监听库
    compile 'oms.mmc:lifecycle-dispatch:1.0.0-SNAPSHOT@aar'
    //通用滚动监听库
    compile('oms.mmc:list-scroll-helper:1.0.2-SNAPSHOT@aar', {
        exclude group: 'com.android.support'
    })
    //等待弹窗库
    compile 'oms.mmc:wait-view-factory:1.0.1-SNAPSHOT@aar'
    //界面切换状态库
    compile 'oms.mmc:load-view-factory:1.0.3-SNAPSHOT@aar'
    //-------------------------- fast mvc 依赖的库 end
```

2. 由于使用ListScrollHelper，如果使用BaseFastFragment时不是使用BaseFastActivity为宿主，则需要在Activity的onCreate()方法的第一行调用以下代码：

```java
ScrollableViewFactory.create(this, new AppCompatScrollableReplaceAdapter()).install();
```

3. 如果使用的RecyclerView、ListView、GridView、ScrollView、NestedScrollView不是直接是官方的，而是修改的，则需要将他们的继承改为以下表格上的类。如果是直接使用官方的，则不需要！（上面的工厂替换方法，会自动给我们替换）

| 类名        | 需要改为继承的类
| --------   | -----:
|ListView|ScrollableListView
|GridView|ScrollableGridView
|ScrollView|ScrollableScrollView
|RecyclerView|ScrollableRecyclerView
|NestedScrollView|ScrollableNestedScrollView

4. **注意** 如果使用时ListAbleDelegateHelper.setupRecyclerView()抛出异常，尝试将ListScrollHelper引用代码排除掉内部使用版本，引用你项目中使用的！

```java
	//库中使用的rv是26的版本，sample使用25编译，所以排除掉库中的rv，引用25的rv
    compile('oms.mmc:list-scroll-helper:1.0.0-SNAPSHOT@aar', {
        exclude group: 'com.android.support'
    })
    //相应的例如你项目使用的是23.4.0就改为23.4.0
    compile 'com.android.support:recyclerview-v7:25.4.0'
```

- 具体原因

```java
	/**
     * 设置列表控件相关配置
     */
    public void setupRecyclerView() {
        //rv在25版本加入了预缓冲，粘性头部在该功能上不兼容，用此开关关闭该功能
        try {
            mRecyclerView.getLayoutManager().setItemPrefetchEnabled(false);
        } catch (Throwable e) {
            //这里try-catch是因为如果使用者使用排除进行替换低版本的rv时，调用该方法会可能找不到方法抛出异常
            e.printStackTrace();
        }
        ...
    }
```

# 要面对的Api

- Activity基类：BaseFastActivity
- Fragment基类：BaseFastFragment
- 自带列表的Activity基类：BaseFastListActivity
- 自带列表的Fragment基类：BaseFastListFragment
- 列表条目类：BaseTpl、BaseStickyTpl

# 约定俗称的函数

> 不带列表的Activity，继承BaseFastActivity

| 函数名        | 函数解释   |  功用  |
| --------   | -----:  | :----:  |
| onLayoutBefore()     | 在setContentView()之前回调 |用来获取上一个界面跳转跳过来的参数|
| onLayoutView()|实例化布局id成View后返回到setContentView|   就是用来返回布局View的   |
|onFindView()|在setContentView()之后回调，用于查找控件|  查找控件时，复写该方法来写查找View，请用函数传递过来的ViewFinder对象进行查找，例如finder.get(R.id.toolBar)  |
| onLayoutAfter()|在setContentView()之后回调| 一般是给查找出来的View进行相关设置，例如ViewPager的setAdapter()  |
|onGetWaitDialogController()|返回等待弹窗控制器实例|需要特定界面不同的等待弹窗时，复写该函数，返回不同的实例|

> 带列表的Activity，继承BaseFastListActivity（继承于BaseFastActivity，所以上面的回调函数，BaseFastListActivity都有，下表只列表新增的）

| 函数名        | 函数解释   |  功用  |
| --------   | -----:  | :----:  |
|onListDataSourceReady()|请求接口，拿列表接口数据| 返回数据集实例，一般是BaseListDataSource，复写load方法，在上面写请求，该函数会在子线程调用  |
|onListAdapterReady()|初始化rv的adapter，并返回|一般不需要重写，都统一返回支持多类型的BaseListAdapter。rv最终使用的adapter并不是该BaseAdapter，而是内部的HeadeFooterAdapter，来支持添加头部和尾部。
|onLoadViewFactoryReady()|界面加载状态切换布局工厂|如需修改对应界面的加载样式，复写该方法
| onLoadMoreViewFactoryReady() |加载更多布局切换工厂|如需要修改加载更多的样式，复写该方法
|onListTypeClassesReady|返回一个HashMap，定义列表条目类Tpl和对应的ViewType映射|这里匹配条目的映射关系|
|onGetListLayoutManager()|返回RecyclerView的LayoutManager|返回rv的LayoutManager|
|onGetStickyTplViewType()|返回需要粘性的条目的ViewType|需要时复写，默认返回一个-1，代表不需要，该方法依赖于StickyHeadersManager，必须设置为粘性的LayoutManager时，才有效|
|onGetScrollHelper()|返回滚动帮助类实例|返回对应使用的滚动类帮助类实例|
|onListScrollHelperReady|当滚动帮助类初始化完成时回调|帮助类初始化完成，可以对帮助类进行添加监听等操作|
|onListReady()|RecyclerView已经初始化完成时回调|rv初始化完成，这时候可以对rv进行操作，例如设置ItemDecoration条目分割线|
|onStartRefresh()|当列表下拉刷新时回调，会返回是否isFirst代表是否是第一次刷新|这里可以针对业务做对应的第一次刷新时做处理，以及每次刷新后做的处理|
|onEndRefresh()|当下拉刷新结束后回调，同样也有isFirst代表是否是第一次|这里可以处理下拉刷新结束|
|onStartLoadMore()|当加载更多开始时回调，会返回isFirst标志，代表是否是第一次|这里可以处理加载更多开始后的处理|
|onEndLoadMore()|当加载更多结束时回调，同样返回isFirst标志|这里可以处理加载更多结束后的处理|

- **注意** onListDataSourceReady()方法，在复写时，调用请求方法或者数据库查询方法时，要分情况返回：
    1. 如果没有数据，也要返回一个List，它的size为0，
    2. 如果是异常，返回null
    3. 有数据就是返回一个List，里面是带数据的
    4. 开发中注意上面的返回即可，状态布局会根据这个结果去切换布局，例如返回null是显示错误布局，返回的List的size为0时，显示为空布局。这个也是合情合理的。

> * BaseFastFragment，不带列表的Fragment，函数回调和BaseFastActivity是一致的，这里不再列出。
> * BaseFastListFragment，带列表的Fragment，函数回调和BaseFastListActivity一致。

# 列表数据类（BaseItemData、ItemDataWrapper）
- 列表界面需要重写的onListDataSourceReady()，需要返回一个BaseItemData的集合，其实就是根据请求回来的数据，组装BaseItemData或者ItemDataWrapper。
- ItemDataWrapper继承BaseItemData，BaseItemData不能带数据，ItemDataWrapper可以带多个数据。
- 构造这2个类时，需要传对应的条目类的ViewType，如果是ItemDataWrapper则需要在构造函数传入ViewType后面的参数。

# 条目类（BaseTpl、BaseStickyTpl）

- 列表列表类，写列表时，必须使用的。创建时填写一个泛型，指的是传递给条目类的数据，记得要对应。

- **注意！** Tpl类文件必须在项目中放置到一个名为tpl包中，不能使用内部类。因为创建tpl实例是使用反射创建的，内部类创建需要传入外部类实例，框架不知道传入的class的外部类是谁，所以强制规定单独起一个包防止tpl类文件！


| 函数名        | 函数解释   |  功用  |
| --------   | -----:  | :----:  |
|onLayoutBefore()| 在生成布局之前回调 |可以初始化一些对象预备着
|onFindView()| 填充了布局后回调，用于查找控件 |用于查找布局中的控件|
|onBindContent()| onFindView()后调用|给控件进行相关设置，例如给ViewPager设置Adapter|
|onRender()|渲染函数，每次RecyclerView的onBindView调用时会回调|在该函数应该做条目数据设置操作，函数会传入该条目的itemData。
|onViewAttachedToWindow()|当条目依附到RecyclerView时回调|可以按场景进行一些操作。
|onViewDetachedFromWindow()|当条目从RecyclerView中移除时回调|可以做一些回收和暂停操作，条目滚动出屏幕时回调|
|onRecyclerViewDetachedFromWindow()|当条目依附的RecyclerView从视图树中移除时回调|这里可以做EventBus或者广播注销操作
|getPosition()|当前条目在rv上的位置|可以获取当前tpl实例在rv上的位置
|getItemDataBean()|可以获取Tpl上存在的数据bean|当需要再onReader()以外的地方获取到itemData时回调
|getItemViewType()|获取当前条目类的类型|当需要使用到条目类的类型时调用
|onItemClick()|当条目被点击时回调|整个条目的点击事件可以复写该方法
|onItemLongClick()|当条目被长按时回调|整个条目的长按事件可以复写该方法
|getRecyclerView()|获取tpl当前依附的RecyclerView实例|获取tpl依附的rv
|getListAdapter()|同样是获取依附的rv的adapter，返回IAssistRecyclerAdapter，是BaseAdapter依赖的最终父类，支持单选、多选、管理模式、普通模式|一般条目使用到单选、多选、编辑模式、普通模式时调用该方法获取adapter来进行操作。
|getActivity()|获取tpl依附的Activity|需要获取Activity获取Context时使用
|getListDataSource()|获取当前列表的数据集对象|当需要获取当前列表的数据集对象时调用
|getListData()|获取当前列表的数据集合|当需要获取当前列表的数据列表时调用
|getRecyclerViewHelper()|获取rv帮助类|获取rv帮助类，内部有刷新函数、加载更多函数，需要调用刷新、加载更多时，调用该方法获取帮助类实例，调用对应方法即可
|getListScrollHelper()|获取列表滚动帮助类|当需要获取滚动帮助类时调用，例如点击条目item滚动到顶部，就需要调用ListScrollHelper的moveToTop()。
|getRoot()|获取列表类上的布局|需要拿取条目类上的布局时调用

> BaseStickyTpl，支持粘性的Tpl，继承至BaseTpl，增加2个粘性方法回调，同样该Tpl也是依赖粘性的

| 函数名        | 函数解释   |  功用  |
| --------   | -----:  | :----:  |
|onAttachSticky（）|当条目被吸顶时回调|可以当吸顶时做处理，例如添加阴影
|onDetachedSticky（）|当条目从吸顶改变为取消吸顶时回调|当取消吸顶时做处理，例如取消阴影

# 列表适配器

> rv的列表适配器，一般都不需要去设置其他的adapter，所以写列表界面是不需要写adapter的。
> 注意框架中设置给rv的adapter是HeaderFooterAdapter，是用装饰器模式给BaseListAdapter添加的支持头、尾布局的适配器。

| 函数名        | 函数解释   |  功用  |
| --------   | -----:  | :----:  |
|addHeaderView()|添加View到头部|当需要添加一个View在rv的头部时调用，类似ListView的addHeader()，不建议使用这种方法，封装程度太低，建议直接使用Tpl在数据加载回调时插入。
|addFooterView()|添加View到尾部|当需要一个View在rv的尾部时调用，现在加载更多的条目就是这么做的。
|removeHeaderView()|移除添加的顶部的View|需要移除添加到头部的View时调用
|removeFooterView()|移除添加的底部View|需要移除添加到尾部的View时调用
|getHeaderFooterAdapter()|获取真正设置给rv的适配器|获取真正设置给rv的适配器时调用

> 当需要用到BaseListAdapter的时候，getHeaderFooterAdapter()拿到装饰适配器，再调用getAdapter()拿到包裹的BaseListAdapter。

| 函数名        | 函数解释   |  功用  |
| --------   | -----:  | :----:  |
|getAdapter()|获取装饰包裹的适配器|需要使用到BaseListAdapter的时候，调用该方法

# 界面加载切换样式

> 列表界面加载时，默认有4种状态。

1. loading加载
2. 加载失败
3. 数据为空
4. 加载成功

> 默认使用BaseLoadViewFactory，子类需要修改时，请复写ListActivity或者ListFragment中的onLoadViewFactoryReady（），返回对应的工厂类。

# 加载更多布局切换

> 加载更多的样式，请复写ListActivity或者ListFragment中的onLoadMoreViewFactoryReady()，返回对应的工厂类。

# 一些辅助方法（ListAbleDelegateHelper）

- ListAbleDelegateHelper，继承BaseFastActivity或者BaseFastFragment时，调用getListAbleDelegateHelper()获取，对rv的一些辅助方法会放在这里。

| 函数名        | 函数解释   |  功用  |
| --------   | -----:  | :----:  |
|compatNestedScroll()|当界面是NestedScrollView嵌套rv时，调用该方法解决滚动粘结问题|如果出现这样的嵌套情况，在对应界面的onListReady()时调用该方法。
|reverseListLayout()|反转rv的布局|当需要实现需求例如QQ、微信的聊天界面时，在对应界面的onListReady()时调用该方法。
|smoothMoveToTop()|缓慢滚动rv到顶部|当需要有动画回顶时，调用该方法
|moveToTop()|瞬时滚动rv到顶部|当只需要瞬时回调顶部时，调用该方法
|findTplByPosition()|根据条目类的position找到tpl条目类|当需要遍历整个列表去找到对应的条目类对象时可调用

# Sample地址

- [地址](http://git.linghit.com:666/android/androidUI)

# 开始使用

## 一、普通界面，继承BaseFastActivity或者BaseFastFragment，这个大家懂的。

- 获取前面跳转过来的数据，统一复写**onLayoutBefore（）**方法

```java
@Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        //例如获取传递过来的用户id
        String userId = intentStr(ActivitySampleActivity.BUNDLE_KEY_USER_ID);
        ToastUtil.showToast(getActivity(), "收到前面传递过来的userId -> " + mUserId);
    }
```

- 设置布局View，统一复写**onLayoutView()**

```java
	/**
     * 该函数返回布局View
     */
    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_base_activity_sample, null);
    }
```

- 查找控件，统一复写**onFindView()**

```java
	@Override
    public void onFindView(ViewFinder finder) {
        super.onFindView(finder);
        //onFindView回调上找控件
        mContent = finder.get(R.id.content);
    }
```

- 设置控件内容等操作，统一复写**onLayoutAfter()**

```java
	@Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        //最后设置数据给控件
        mContent.setText(mUserId);
        //如果后续有继续请求，请在这里写
        requestXxx();
    }
```

## 二、列表界面，继承BaseFastListActivity或者BaseFastListFragment。

- 如果只是一个下拉刷新，一个rv，不用复写**onLayoutView()**，基类默认有一个布局，直接使用即可。如果还有其他控件在界面，例如一个浮动按钮，则复写**onLayoutView()**，将基类的布局拷贝一份，在上面修改，控件的id必须一致，在该方法返回。

- 开始一个请求，统一复写**onListDataSourceReady()**返回数据集

```java
/**
     * 这里请求网络拿列表数据
     */
    @Override
    public IDataSource<BaseItemData> onListDataSourceReady() {
        return new BaseListDataSource<BaseItemData>(getActivity()) {
            @Override
            protected ArrayList<BaseItemData> load(int page, boolean isRefresh) throws Exception {
                ArrayList<BaseItemData> models = new ArrayList<>();
                //这里模拟请求数据，真实开发时是调用一个同步接口，返回数据
                ArrayList<String> datas = getData();
                //同样json解析后的bean，都是for循环出来，根据bean的字段判断，添加对应条目，条目位置在这里决定，ItemDataWrapper是带传入参数的条目数据bean，构造时返回对应条目的ViewType，后续的参数就是不定长度的参数。
                for (int i = 0; i < datas.size(); i++) {
                    String data = datas.get(i);
                    if (i % 2 == 0) {
                        models.add(new ItemDataWrapper(TPL_IMAGE, data));
                    } else {
                        models.add(new ItemDataWrapper(TPL_TEXT, data));
                    }
                }
                 //分页，需要和后台协商，一页返回大于多少条时可以有下一页
                 //这里记得更新页码，当页面是不分页的，下面这2句代码不需要写
					//this.page = page;
					//例如规定一页14条
					//this.hasMore = datas.size() >= Const.Config.pageSize;
                return models;
            }
        };
    }
```

- 条目多type支持，统一复写**onListTypeClassesReady()**返回一个类型映射Map

```java
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
```

- 设置rv的LayoutManager，统一复写**onGetListLayoutManager()**

```java
//需要粘性条目时返回StickyHeaders系列的LayoutManager
@Override
    public RecyclerView.LayoutManager onGetListLayoutManager() {
        return new StickyHeadersLinearLayoutManager(getActivity());
    }
```

- 需要条目有粘性时，复写**onGetStickyTplViewType()**，该方法依赖StickyHeaders系列的LayoutManager，请确保复写时，**onGetListLayoutManager()**方法返回粘性类型的布局管理器。

```java
@Override
    public int onGetStickyTplViewType() {
        return TPL_EDIT;
    }
```

- 要对rv进行操作时，复写**onListReady()**，例如添加一个头部，为rv添加分割线

```java
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
```

- 需要在刷新开始、结束时做处理，复写**onStartRefresh()**、**onEndRefresh()**

```java
@Override
    public void onStartRefresh(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, boolean isFirst, boolean isReverse) {
        super.onStartRefresh(adapter, isFirst, isReverse);
        //刷新开始，isFirst代表是第一次，例如第一次时，显示一个等待弹窗
        if (isFirst) {
            showWaitDialog();
        }
    }

    @Override
    public void onEndRefresh(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, ArrayList<BaseItemData> result, boolean isFirst, boolean isReverse) {
        super.onEndRefresh(adapter, result, isFirst, isReverse);
        //刷新结束，对应的第一次时，隐藏等待弹窗
        if (isFirst) {
            hideWaitDialog();
        }
    }
```

- 需要在加载更多时，开始、结束时做处理，复写**onStartLoadMore()**、**onEndLoadMore()**

```java
@Override
    public void onStartLoadMore(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, boolean isFirst, boolean isReverse) {
        super.onStartLoadMore(adapter, isFirst, isReverse);
        ...
    }

    @Override
    public void onEndLoadMore(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, ArrayList<BaseItemData> result, boolean isFirst, boolean isReverse) {
        super.onEndLoadMore(adapter, result, isFirst, isReverse);
        ...
    }
```
# 更换等待弹窗布局样式

- 等待弹窗样式修改，复写fragment或者activity的onWaitDialogFactoryReady()，返回一个实现IWaitViewFactory接口的工厂类。默认是BaseWaitDialogFactory，是弹出ProgreesDialog作为弹窗样式，当需要选择其他样式时，新建一个弹窗控件类，一个弹窗控制类iml，新建一个工厂类即可。例如我需要显示一个IOS菊花旋转的样式。下面例子显示修改。

1. 弹窗控件类，新建控件类，IOSWaitDialog，继承Dialog，必须实现IEditableDialog接口，复写设置文字方法。

```java
public class IOSWaitDialog extends Dialog implements IEditableDialog {
    private TextView message;

    public IOSWaitDialog(Context context) {
        this(context, R.layout.view_ios_wait_dialog, R.style.ios_dialog_wait);
    }

    public IOSWaitDialog(Context context, int layout, int style) {
        super(context, style);
        View rootLayout = LayoutInflater.from(context).inflate(layout, null);
        setContentView(rootLayout);
		 ...
    }

    public void setMessage(CharSequence msg) {
        if (TextUtils.isEmpty(msg)) {
            message.setVisibility(View.GONE);
        } else {
            message.setVisibility(View.VISIBLE);
            message.setText(msg);
        }
    }

    @Override
    public void setMessage(int messageResId) {
        setMessage(getContext().getResources().getString(messageResId));
    }
}
```

2. 新建一个IOSWaitDialogIml，继承AbsWaitDialogIml，这里有个泛型，声明为该类控制弹出的Dialog，例如这里是IOSWaitDialog。

```java
public class IOSWaitDialogIml extends AbsWaitDialogIml<IOSWaitDialog> {

    @Override
    public IOSWaitDialog onCreateDialog(Activity activity, CharSequence msg) {
        IOSWaitDialog dialog = new IOSWaitDialog(activity);
        dialog.setMessage(msg);
        return dialog;
    }
}
```

3. 最后，新建一个IOSWaitDialogFactory工厂类返回

```java
public class IOSWaitDialogFactory extends BaseWaitDialogFactory {
    @Override
    public WaitDialogController getWaitDialogController(Activity activity) {
        return new WaitDialogController(activity, IOSWaitDialogIml.class);
    }
}
```

# 更换界面切换布局样式

- 重写fragment或activity的onLoadViewFactoryReady()方法，返回一个实现了ILoadViewFactory接口的工厂类，默认是BaseLoadViewFactory，需要修改时，新建一个类，继承BaseLoadViewFactory，复写madeLoadView()方法，返回一个ILoadView接口实例，推荐继承BaseLoadViewHelper，复写对应的状态方法，返回对应的View视图即可。

- **注意** 当activity的onLoadViewFactoryReady()有复写时，fragment的复写则无效。只有当宿主Activity不是BaseFastActivity或者手动复写Activity的onLoadViewFactoryReady()返回成null时，才有效。

> 相关函数

| 函数名        | 函数解释   |  功用  |
| --------   | -----:  | :----:  |
|onInflateLoadingLayout()|当填充加载状态的布局时回调|返回对应的View对象即可
|onInflateErrorLayout()|当填充错误装填的布局时回调|返回对应的View对象即可
|onInflateEmptyLayout()|当填充数据为空的布局时回调|返回对应的View对象即可

1. 新建工厂类

```java
public class SampleLoadViewFactory extends BaseLoadViewFactory {
    @Override
    public ILoadView madeLoadView() {
        return new BaseLoadViewHelper() {
            @Override
            protected View onInflateLoadingLayout(VaryViewHelper helper, View.OnClickListener onClickRefreshListener) {
                return helper.inflate(R.layout.layout_loading_view_sample_loading);
            }

            @Override
            protected View onInflateErrorLayout(VaryViewHelper helper, View.OnClickListener onClickRefreshListener) {
                View layout = helper.inflate(R.layout.layout_sample_load_view_error);
                TextView refreshTv = (TextView) layout.findViewById(R.id.base_list_error_refresh);
                refreshTv.setOnClickListener(onClickRefreshListener);
                return layout;
            }

            @Override
            protected View onInflateEmptyLayout(VaryViewHelper helper, View.OnClickListener onClickRefreshListener) {
                View layout = helper.inflate(R.layout.layout_sample_load_view_empty);
                TextView refreshTv = (TextView) layout.findViewById(R.id.base_list_empty_refresh);
                refreshTv.setOnClickListener(onClickRefreshListener);
                return layout;
            }
        };
    }
}
```

2. 最后将新的工厂类添加到方法上

```java
//重写该函数，切换页面切换时的样式（加载中、异常、空）
    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new SampleLoadViewFactory();
    }
```

# 更换尾部加载更多样式

> 具体函数

| 函数名        | 函数解释   |  功用  |
| --------   | -----:  | :----:  |
|onInflateFooterView()|返回尾部布局View|就是加载更多尾部布局的视图
|onInflateFooterViewAfter()|返回了布局后的操作|返回布局后的操作，通常来查找控件
|onShowNormal()|当显示普通布局，一开始默认显示的样式|一开始显示的样式，在显示加载中之前显示，该方法做该状态需要修改的事情，例如控件设置显示隐藏，修改提示文字
|onShowLoading()|显示加载布局时回调|切换加载时要显示的控件
|onShowError()|显示错误布局时的回调|切换错误时要显示的控件
|onShowNoMore()|显示没有更多时的回调|切换没有更多时要显示的控件

- 如果样式只是在一个布局上做样式切换，来完成相似的样式上做切换，基本上上面的方法是够用的。但是如果几个状态样式相差很大时，就不能这么使用了，所以有了一下onSwitch开头的函数，可以在对应状态时，返回对应布局来达到切换。

| 函数名        | 函数解释   |  功用  |
| --------   | -----:  | :----:  |
|onSwitchNormalLayout()|当显示普通布局前回调，返回普通状态下的布局|该方法返回一个View布局即可
|onSwitchLoadingLayout()|当显示加载布局前回调|返回加载状态下的布局
|onSwitchErrorLayout()|当显示错误布局前回调|返回错误状态下的布局
|onSwitchNoMoreLayout()|当显示没有更多布局前回调|返回没有更多状态下的布局

- on开头的方法需要返回一个AfterAction的枚举实例，代表了状态布局的附加处理，这个类型的作用是用来，隐藏布局和显示布局的，还可以做一些样式需求。这个加载更多的尾部布局，其实最后是作为rv的item来处理的，例如我想没有更多时，隐藏这个item，如果直接将viewHolder的itemLayout去gone掉，还是会占一个条目的高度的，这个标志就用来去将item的布局的高度去压缩到0，这样就隐藏掉这个布局了。当我需要加载更多不隐藏，而是显示出来，并且设置一段文字，这样就可以实现例如支付宝的列表界面，没有更多时，显示一段文字“我是有底线的”，这样的一个效果。

| 状态类型名        | 类型解释   |  功用  |
| --------   | -----:  | :----:  |
|NO_ACTION|无操作，当不需要压缩高度或者恢复高度时可用|项目中没有用到这个类型，因为上一个状态是否压缩和恢复状态无法知道，所以一般不使用
|COMPRESS_HEIGHT|压缩高度，当前状态是隐藏布局时使用|例如没有更多时，加载更多条目是要隐藏时，返回该标志。
|RESTORE_HEIGHT|恢复高度|当前状态需要显示出来时，返回该标志

- 直接复写fragment和activity的，onLoadMoreViewFactoryReady()，返回一个实现了ILoadMoreViewFactory接口的工厂类，复写创建加载视图方法，madeLoadMoreView()，传入一个实现了ILoadMoreView的实例，默认是DefaultLoadMoreHelper，推荐创建该类实例后，匿名对象复写需要复写的对应撞他的方法即可，也可以完全继承DefaultLoadMoreHelper的父类，AbsLoadMoreHelper来复写所有方法。

```java
public class SampleLoadMoreViewFactory implements ILoadMoreViewFactory {
    private TextView mTipText;
    private ProgressWheel mProgressWheel;

    @Override
    public ILoadMoreView madeLoadMoreView() {
        return new AbsLoadMoreHelper() {
            @Override
            protected View onInflateFooterView(LayoutInflater inflater, RecyclerView list, View.OnClickListener onClickLoadMoreListener) {
                return inflater.inflate(R.layout.layout_sample_load_more_footer, list, false);
            }

            @Override
            protected void onInflateFooterViewAfter(View footerView) {
                mProgressWheel = (ProgressWheel) footerView.findViewById(R.id.progressBar);
                mTipText = (TextView) footerView.findViewById(R.id.base_list_error_tip);
            }

            @Override
            protected AfterAction onShowNormal(View footerView) {
                footerView.setVisibility(View.VISIBLE);
                mProgressWheel.setVisibility(View.GONE);
                mTipText.setVisibility(View.VISIBLE);
                mTipText.setText("");
                footerView.setOnClickListener(null);
                return AfterAction.RESTORE_HEIGHT;
            }

            @Override
            protected AfterAction onShowNoMore(View footerView) {
                //当时统一的尾部视图时，直接对返回的footerView做控件操作，如果是单独一个布局，则不需要做操作了，重写onSwitchNoMoreLayout()方法返回特定的布局即可

//                footerView.setVisibility(View.VISIBLE);
//                mProgressWheel.setVisibility(View.GONE);
//                mTipText.setVisibility(View.VISIBLE);
//                mTipText.setText("没有更多了呢");
//                footerView.setOnClickListener(null);
                //这里返回不压缩高度，让尾部item显示，并且显示一条"没有更多了呢"的提示
                return AfterAction.RESTORE_HEIGHT;
            }

            @Override
            protected View onSwitchNoMoreLayout(LayoutInflater inflater) {
                return inflater.inflate(R.layout.layout_sample_load_more_footer_no_more, null);
            }

            @Override
            protected AfterAction onShowLoading(View footerView) {
                footerView.setVisibility(View.VISIBLE);
                mProgressWheel.setVisibility(View.VISIBLE);
                mTipText.setVisibility(View.VISIBLE);
                mTipText.setText("正在努力赶来喔...");
                footerView.setOnClickListener(null);
                return AfterAction.RESTORE_HEIGHT;
            }

            @Override
            protected AfterAction onShowError(View footerView) {
                footerView.setVisibility(View.VISIBLE);
                mProgressWheel.setVisibility(View.GONE);
                mTipText.setVisibility(View.VISIBLE);
                mTipText.setText("发生错误啦，刷新一下吧...");
                footerView.setOnClickListener(getOnClickRefreshListener());
                return AfterAction.RESTORE_HEIGHT;
            }
        };
    }
}
```
- 最后设置新的工厂类给对应的界面即可

```java
//重写该函数，切换加载更多更换的样式（正在加载、无数据、异常）
    @Override
    public ILoadMoreViewFactory onLoadMoreViewFactoryReady() {
        return new SampleLoadMoreViewFactory();
    }
```

# Activity单是作为容器Fragment的简单使用

- ***注意*** 该方法只在BaseFastActivity上存在！直接复写**onSetupFragment()**

```java
//简单使用：如果activity只是一个容器，直接复写该方法，传入fragment的class和需要传递数据Bundle即可，不用写重复的代码
    @Override
    protected FragmentFactory.FragmentInfoWrapper onSetupFragment() {
        //transformActivityData()这个函数，会将activity的intent的数据转移到fragment的mArguments上。
        return new FragmentFactory.FragmentInfoWrapper(BaseFragmentSampleFragment.class, transformActivityData());
    }
```

## Tpl条目类

- 列表界面使用的条目类，继承BaseTpl，**泛型传入使用的条目数据bean类型**，例如比较常用的**ItemDataWrapper**，当需要数据时，是**BaseItemData**，记得和列表的**onListDataSourceReady()**方法中对应喔

- 返回条目布局Layout，统一复写onLayoutView()，作用上上面的界面类的onLayoutView一样

```java
@Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_conversation_chat_msg, container, false);
    }
```

- 查找View，统一复写**onFindView()**

```java
@Override
    public void onFindView(ViewFinder finder) {
        super.onFindView(finder);
        avatar = finder.get(R.id.avatar);
        name = finder.get(R.id.name);
        msgContent = finder.get(R.id.msgContent);
    }
```

- 当条目类上的控件需要做设置操作时，统一复写**onBindContent()**，该方法只会调用一次

```java
@Override
    protected void onBindContent() {
        super.onBindContent();
        viewPager.setAdapter(new PagerAdapter());
    }
```

- 每次rv的onBindView回调时，调用onRender()方法，统一复写**onRender()**做操作，该方法会调用多次

```java
@Override
    protected void onRender(ItemDataWrapper itemData) {
        super.onRender(itemData);
        String avatarUrl = (String) itemData.getDatas().get(0);
        String nameText = (String) itemData.getDatas().get(1);
        String content = (java.lang.String) itemData.getDatas().get(2);
        Glide.with(getActivity()).load(avatarUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(avatar);
        name.setText(nameText);
        msgContent.setText(content);
    }
```
- 条目点击和长按，直接复写**onItemClick()**，**onItemLongClick()**

```java
@Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        super.onItemLongClick(view, position);
    }
```

- 当条目类需要监听广播时，注册、注销操作统一复写**onLayoutBefore()**进行注册，**onRecyclerViewDetachedFromWindow()**进行注销

```java
@Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        //注册，这里用EventBus做样例，广播也一样
        EventBusUtil.register(this);
    }

    @Override
    public void onRecyclerViewDetachedFromWindow(View view) {
        super.onRecyclerViewDetachedFromWindow(view);
        //注销
        EventBusUtil.unregister(this);
    }
```

- 如果条目时粘性的(记得继承BaseStickyTpl，否则无效)，统一复写**onAttachSticky()**、**onDetachedSticky()**，一个是当粘上回调，一个是当脱离粘附时回调。

```java
	@Override
    public void onAttachSticky() {
        //例如：当粘附时，设置阴影
        ViewCompat.setElevation(getRoot(), 10);
    }

    @Override
    public void onDetachedSticky() {
        //例如：当脱离粘附时，取消阴影
        ViewCompat.setElevation(getRoot(), 0);
    }
```

# RecyclerViewViewHelper，rv帮助类，定义了刷新、加载更多等操作

- 调用刷新

```java
getRecyclerViewHelper().refresh();
```

- 调用刷新，并且下拉刷新显示刷新的圆圈

```java
getRecyclerViewHelper().startRefreshWithRefreshLoading();
```

- 加载更多（一般很少手动去调用，都是由滚动监听调用）

```java
getRecyclerViewHelper().loadMore()
```

# 跳转参数传递读取

- 平时带参跳转，读取参数，在fragment、activity上直接调用intentXxx类方法拿取，已支持String、Int、Float、Boolean、Serializable、Parcelable。详情见基类。

```java
		//获取传递过来的用户id
       String userId = intentStr(ActivitySampleActivity.BUNDLE_KEY_USER_ID);
       //也可以带默认参数
       String userId = intentStr(ActivitySampleActivity.BUNDLE_KEY_USER_ID, "-1");
       //常用的int
       int userIdInt = intentInt(ActivitySampleActivity.BUNDLE_KEY_USER_ID);
       int userIdInt = intentInt(ActivitySampleActivity.BUNDLE_KEY_USER_ID, -1);
       //序列化用户信息
       Serialzable userInfo = intentSerializable(ActivitySampleActivity.BUNDLE_KEY_USER_Info);
```