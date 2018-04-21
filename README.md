# FastMvc开发框架说明文档

------

| 更新内容        | 更新时间   |  作者  |
| --------   | -----:  | :----:  |
|文档初始化编写|2018.03.05|子和
|代码审核，1.0.3版本|2018.03.12|子和
|1.0.4版本，重构，支持替换下拉刷新、滚动控件，更好进行拓展|2018.03.29|子和

---------

### 以前的框架，我们是怎样进行开发的？

 - 以前的都是在MMCSDK上的基类Activity、Fragment上进行子类开发，但是例如 **查找控件** ，**给控件进行相关设置**，没有一个明确的地方进行设置，大家都是自己起函数或者直接在查找控件后，直接就写上了。

### 这样的方式有什么问题呢？

- 没有一个约定俗成的函数名，大量相同的操作，每个人都是自己写一套，下一个组员进行迭代开发的时候，想找相关查找控件时或者进行相关操作时，首先只能搜索onCreate方法，然后从中再来看，可能是写一起的，也有可能是分拆成其他函数了，每个界面都要重新找一遍，造成了时间浪费，如果找不到，那只能找上次开发的组员了。
- 如果加上函数安放顺序也可能不一样，函数调用顺序和代码上的不一致，看逻辑时只能跳来跳去，一个Activity上千行的话（老项目还真有），可想而知，那感觉真难受。

### 所以？

- 要改变这样的现状。让项目迭代组员沟通量小，代码风格统一，BUG量减少。所以，我定义了这样的一套开发框架，规定好共有的逻辑和回调方法，统一开发书写样式。

- 由于面对的都是一套Api，Activity切换到Fragment只需要改继承的基类，从BaseFastActivity改为BaseFastFragment即可，无报错，切换基本无成本。

- 使用时，如果是已经更新了mmcsdk到2.0.0，则依赖此库，Activity使用BaseFastActivity进行开发。如果还是旧版本，由于以前都需要继承MMC开头的Activity不能继承本库的BaseFastActivity，所以要兼容旧版本，使用继承BaseFastFragment进行嵌入到之前的Activity进行开发。

---------

# 关于是否加入MMCSDK

- 本库是不依赖MMCSDK的，因为没有功能依赖MMCSDK，并且本库可用于旧项目和新项目，所以依赖的话，是不确定具体版本的，具体项目，看情况依赖即可。至于友盟的页面统计，由于本库不依赖MMCSDK，页面的统计MMCSDK都是封装到了BaseUIHelper，具体可以参考MMCSDK中的BaseFragment，将BaseUIHelper创建实例，在相应生命周期调用即可。BaseFastActivity也是一样，推荐使用Fragment来做界面。

- 至于TopBar，MMCSDK2.0.0已经拆分成View了，所以BaseFastActivity和BaseFastFragment也没有默认添加到布局，建议界面根据自身建议基类，使用BaseFastFragment、BaseFastListFragment作为基类，然后将TopBar放置到布局相应位置。这样对于模块划分，运营需求UI界面变更时更加灵活。

--------------------

# 1.0.4版本

```java
compile 'oms.mmc:fast-mvc:1.0.4-SNAPSHOT@aar'
//  -------------------------- fast mvc 依赖的库 start ----------------------------
//生命周期监听库
compile 'oms.mmc:lifecycle-dispatch:1.0.3-SNAPSHOT@aar'
//通用滚动监听库
compile 'oms.mmc:list-scroll-helper:1.0.4-SNAPSHOT@aar'
//等待弹窗库
compile 'oms.mmc:wait-view-factory:1.0.4-SNAPSHOT@aar'
//界面切换状态库
compile 'oms.mmc:load-view-factory:1.0.5-SNAPSHOT@aar'
//图片加载
compile 'oms.mmc:imageLoader:1.0.0-SNAPSHOT@aar'
//Glide图片加载框架，MMCImageLoader需要依赖的加载库
compile 'com.github.bumptech.glide:glide:3.8.0'
//    -------------------------- fast mvc 依赖的库 end ----------------------------
```

- 该版本，重构了非常多，原本下拉刷新绑定SwipeRefreshLayout，滚动控件绑定RecyclerView，现在也得到了抽取和拓展，轻松几句即可实现不同滚动控件的切换，日后谷歌再出比RecyclerView更牛逼的滚动控件也不会打破整个框架的结构。下拉刷新也得到了动态替换，想换什么就换什么~

- 原本需要继承的BaseFastActivity也不用强制继承啦，添加IFastUIDelegate的实现类FastUIDelegate，按BaseFastActivity中对应生命周期方法，转调delegate对应的方法即可~老项目也能轻松接入~

# 一些改动

1. 由于将滚动控件支持拓展到更多的控件，需要继承的BaseFastListActivity、BaseFastListFragment，需要继承BaseFastListViewActivity、BaseFastRecyclerViewListActivity或者BaseFastListViewFragment、BaseFastRecyclerViewListFragment，见名知意，对应的就是使用不同的控件选择不同的基类，推荐rv的，更强大~原来的不能继承喔，旧版本升级需要修改一下继承的基类。

2. 原来的界面类，指的是继承的Activity或者Fragment，需要加上2个泛型，第一个是我们处理过刷新布局，第二个是滚动控件。如果不需要特殊处理，默认使用SwipePullRefreshLayout，继承自SwipeRefreshLayout，实现了IPullRefreshLayout接口，其他下拉刷新是要适配，则是新建一个类继承第三方库的刷新布局类，实现我们的IPullRefreshLayout接口，实现接口方法，转调第三方库刷新布局的方法即可，文档后面会讲解到接入第三方刷新框架的步骤。

3. 重写方法更名：

| 原方法名        | 变更名   |  作用  |
| --------   | -----:  | :----:  |
|onGetStickyTplViewType()|onStickyTplViewTypeReady()|返回需要粘性头部的Tpl的ViewType即可实现粘性
|onGetScrollHelper()|onInitScrollHelper()|用于返回滚动帮助类的回调
|onGetWaitDialogFactory()|onWaitDialogFactoryReady()|用于返回等待弹窗工厂类
|getRecyclerView()|getScrollableView()|获取滚动控件，由于拓展了滚动控件，获取滚动控件的方法改为getScrollableView()
|getSwipeRefresh()|getRefreshLayout()|获取下拉刷新布局
|getRecyclerViewHelper()|getListHelper()|获取列表帮助类

4. 新增的方法

| 函数名        | 返回值   | 函数解释和功用  |
| --------   | -----:  | :----:  |
|getRefreshLayoutWrapper()|IPullRefreshWrapper<?>|获取下拉刷新布局包裹类
|getRefreshLayout()|IPullRefreshLayout|获取下拉刷新布局控件
|getScrollableView()|IScrollableAdapterView|获取滚动控件，rv或者lv
|getListDataSource()|IDataSource<BaseItemData>|获取列表数据集
|getListData()|ArrayList<BaseItemData>|获取列表数据
|getAssistHelper()|IAssistHelper|原本adapter中的多选、单选、多模式放到这边，而不是继承adapter上
|getRecyclerViewAdapter()|HeaderFooterAdapter|支持rv添加头部、尾部的适配器
|getListAbleDelegateHelper()|IListAbleDelegateHelper|列表代理对象，用于统一list系列的activity、fragment回调。

5. 列表adapter的变更，原本rv的adapter分了开继承了好几个，最主要就是BaseListAdapter继承MultiTypeAdater，最后添加头部、尾部又用装饰者模式加上了HeaderFooterAdapter来实现。现统一为HeaderFooterDataAdapter装饰CommonRecyclerViewAdapter即可。至于ListView的实现，就是直接只用CommonListViewAdapter。

6. 数据集的拓展，原本列表数据集都是封装在一个叫IDataSource的类，默认page从1开始，现抽出一个initFirstPage()来提供复写。也添加一个getCurrentPage()来获取当前页码。

7. 设置列表适配器Adapter的改变！原本的setAdapter()改为setListAdapter，这里在ListHelper会回调adapter初始化和设置，通常不需要使用者调用，这里注意了！获取则封装了getListAdapter()，可以直接调用。

8. 原本的rv的适配是调用BaseListAdapter里面包含的HeaderFooterAdapter，现在不需要啦，现在在回调适配器初始化时直接创建HeaderFooterAdapter包裹并返回，更加直观，getListAdapter()也不需要再getHeaderFooterAdapter来做addHeader()等操作，现在直接getListAdapter()来直接强转HeaderFooterAdapter来进行相关调用。

9. 关于列表添加头部和尾部。由于拓展了支持lv，所以给lv或者rv添加、移除头部、尾部则提供了4个方法。

在界面类上调用getListAbleDelegateHelper()()，则能获取到界面设定的滚动控件对应的列表代理对象，调用以下表格中的方法即可。

| 函数名        | 返回值   | 函数解释和功用  |
| --------   | -----:  | :----:  |
|addHeaderView(View view)|void|使用一个View作为头部
|removeHeader(View view)|boolean|移除一个指定的头部
|addFooterView(View view)|void|使用一个View作为尾部
|removeFooter(View view)|boolean|移除一个指定的尾部

# 接入第三方下拉刷新库

- 1.0.3版本强制使用了SwipeRefreshLayout作为下拉刷新，1.0.4版本对该方面做了重构，做了一个适配器层，接入第三方刷新库十分简单，而且不会改动到底层框架，项目Sample例子中有4种下拉刷新库的实现，推荐SmartRefreshLayout，拓展性更高。现在就教大家以SmartRefreshLayotu为例子。

1. 第一步：新建一个类，继承刷新库中的刷新布局，并实现IPullRefreshLayout接口，重写接口方法，转调第三方刷新布局上的方法，方法名都很清楚了，都是刷新库中必备的功能。


```java
//例如SmartRefreshLayout库中刷新库就是SmartRefreshLayout，新建一个类叫SmartPullRefreshLayout，实现IPullRefreshLayout接口。

public class SmartPullRefreshLayout extends SmartRefreshLayout implements IPullRefreshLayout {
    public SmartPullRefreshLayout(Context context) {
        super(context);
    }

    public SmartPullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartPullRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void startRefresh() {
        autoRefresh();
    }

    @Override
    public void startRefreshWithAnimation() {
        autoRefresh();
    }

    @Override
    public void completeRefresh() {
        finishRefresh();
    }

    @Override
    public void setRefreshEnable() {
        setEnableRefresh(true);
    }

    @Override
    public void setRefreshDisable() {
        setEnableRefresh(false);
    }

    @Override
    public boolean isRefreshEnable() {
        return isEnableRefresh();
    }

    @Override
    public boolean isRefreshDisable() {
        return !isEnableRefresh();
    }

    @Override
    public boolean isRefurbishing() {
        return isRefreshing();
    }
}
```

2. 第二步：继承要使用的滚动控件的界面基类，指的是继承BaseFastRecyclerViewListFragment等，重写onLayoutView()返回布局中，给rv包裹的下拉刷新控件要改为第一步定义的SmartPullRefreshLayout。

```java
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <oms.mmc.android.fast.framwork.sample.widget.SmartPullRefreshLayout
        android:id="@id/fast_refresh_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <com.scwang.smartrefresh.header.DeliveryHeader
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <oms.mmc.helper.widget.ScrollableRecyclerView
            android:id="@id/fast_list"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </oms.mmc.android.fast.framwork.sample.widget.SmartPullRefreshLayout>
</FrameLayout>
```

3. 第三步：新建一个Wrapper类，继承AbsPullRefreshWrapper，类后有一个泛型，传入刚才第一步新建的SmartPullRefreshLayout。复写接口方法。其实继承AbsPullRefreshWrapper，除了添加下拉监听的方法需要重写，其他都不需要了，简洁很多~

```java
public class SmartPullRefreshWrapper extends AbsPullRefreshWrapper<SmartPullRefreshLayout> {
    public SmartPullRefreshWrapper(SmartPullRefreshLayout refreshLayout) {
        super(refreshLayout);
    }

    @Override
    public void setOnRefreshListener(final OnRefreshListener listener) {
        getPullRefreshAbleView().setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                listener.onRefresh();
            }
        });
    }
}
```

4. 第四步：上面两步其实就已经将自定义部分完成了，接下来第三步就是使用了。在界面类上，继承BaseFastRecyclerViewListFragment或者BaseFastListViewListFragment等界面基类，后面的泛型，第一个泛型传入刚才第一步定义的SmartPullRefreshLayout。复写2个方法，onInitPullRefreshWrapper()，onPullRefreshWrapperReady()。方法会根据泛型自动确定对应的传入参数，所以不用担心。

| 函数名        | 返回值   | 函数解释和功用  |
| --------   | -----:  | :----:  |
|onInitPullRefreshWrapper()|IPullRefreshWrapper<SmartPullRefreshLayout>|初始化刷新布局包裹类，就是第二步我们新建的Wrapper类。
|onPullRefreshWrapperReady()|void|用于回调设置第三方下拉刷新库相关设置，例如设置不使用加载更多功能（这里我们使用我们自己的加载更多模块），有的库可以设置支持IOS惯性滚动，等等。

# 拓展滚动控件（rv或者lv，如果日后推出更强大的滚动控件，则按这种方法拓展！）

1. 第一步：新建一个类，继承滚动控件	，实现IScrollableAdapterView接口。例如RecyclerView，我们使用的是ScrollableRecyclerView，就是继承RecyclerView。

```java
public class ScrollableRecyclerView extends RecyclerView implements IScrollableAdapterView {
    public ScrollableRecyclerView(Context context) {
        super(context);
    }

    public ScrollableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setListAdapter(IListScrollViewAdapter adapter) {
        ListScrollViewAdapterUtil.isValidListAdapter(adapter);
        super.setAdapter((Adapter) adapter);
    }

    @Override
    public IListScrollViewAdapter getListAdapter() {
        return (IListScrollViewAdapter) super.getAdapter();
    }

    @Override
    public View getViewByPosition(int position) {
        RecyclerView.ViewHolder holder = findViewHolderForAdapterPosition(position);
        if (holder != null) {
            return holder.itemView;
        }
        return null;
    }
}
```

2. 新建一个类继承AbsScrollableViewWrapper，类后泛型改为刚才第一步定义的类，例如这里就是ScrollableRecyclerView。实现未实现的方法。就是一下3个方法。

| 函数名        | 返回值   | 函数解释和功用  |
| --------   | -----:  | :----:  |
|setup(ScrollDelegate delegate, ScrollableRecyclerView scrollableView)|void|设置代理滚动监听操作，在这里给滚动控件设置滚动监听，回调传过来的delegate对应的滚动方法，例如滚动到顶部，滚动到底部，上滑、下滑。
|moveToTop()|void|瞬时滚动到顶部
|smoothMoveToTop()|void|缓慢滚动到顶部

```java
public class ScrollableRecyclerViewWrapper extends AbsScrollableViewWrapper<ScrollableRecyclerView> {
    //第一次进入界面时也会回调滚动，所以当手动滚动再监听
    private boolean isNotFirst = false;

    public ScrollableRecyclerViewWrapper(ScrollableRecyclerView scrollingView) {
        super(scrollingView);
    }

    @Override
    public void setup(final ScrollDelegate delegate, ScrollableRecyclerView scrollableView) {
        scrollableView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isNotFirst = true;
                if (delegate != null) {
                    //如果滚动到最后一行，RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
                    if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                            !recyclerView.canScrollVertically(1)) {
                        delegate.onScrolledToBottom();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (delegate != null && isNotFirst) {
                    //RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
                    if (!recyclerView.canScrollVertically(-1)) {
                        delegate.onScrolledToTop();
                    }
                    //下滑
                    if (dy < 0) {
                        delegate.onScrolledToDown();
                    }
                    //上滑
                    if (dy > 0) {
                        delegate.onScrolledToUp();
                    }
                }
            }
        });
    }

    @Override
    public void moveToTop() {
        RecyclerView recyclerView = getScrollableView();
        if (recyclerView != null) {
            recyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void smoothMoveToTop() {
        RecyclerView recyclerView = getScrollableView();
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
        }
    }
}
```

3. 如果滚动控件有继承关系，可以新建一个wrapper的基类，例如ListView，框架中添加了PinnedSectionListView来支持粘性头部。和ListView是继承关系，这时候就可以新建一个Wrapper的基类，AbsScrollableListViewWrapper。

```java
public class AbsScrollableListViewWrapper<V extends ScrollableListView> extends AbsScrollableViewWrapper<V> {
    private int oldVisibleItem = 0;
    //第一次进入界面时也会回调滚动，所以当手动滚动再监听
    private boolean isNotFirst = false;

    public AbsScrollableListViewWrapper(V scrollingView) {
        super(scrollingView);
    }

    @Override
    public void setup(final ScrollDelegate delegate, V scrollableView) {
        scrollableView.addOnListViewScrollListener(new ScrollableListView.OnListViewScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView listView, int scrollState) {
                isNotFirst = true;
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (delegate != null) {
                        if (listView.getLastVisiblePosition() + 1 == listView.getCount()) {
                            delegate.onScrolledToBottom();
                        } else if (listView.getFirstVisiblePosition() == 0) {
                            delegate.onScrolledToTop();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView listView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (delegate != null) {
                    if (firstVisibleItem > oldVisibleItem && isNotFirst) {
                        //上滑
                        delegate.onScrolledToUp();
                    }
                    if (oldVisibleItem > firstVisibleItem && isNotFirst) {
                        //下滑
                        delegate.onScrolledToDown();
                    }
                    oldVisibleItem = firstVisibleItem;
                }
            }
        });
    }

    @Override
    public void moveToTop() {
        getScrollableView().setSelection(0);
    }

    @Override
    public void smoothMoveToTop() {
        getScrollableView().smoothScrollToPosition(0);
    }
}

//普通ListView
public class ScrollableListViewWrapper extends AbsScrollableListViewWrapper<ScrollableListView> {

    public ScrollableListViewWrapper(ScrollableListView scrollingView) {
        super(scrollingView);
    }
}

//支持粘性头部的ListView
public class ScrollablePinnedSectionListViewWrapper extends AbsScrollableListViewWrapper<ScrollablePinnedSectionListView> {

    public ScrollablePinnedSectionListViewWrapper(ScrollablePinnedSectionListView scrollingView) {
        super(scrollingView);
    }
}

```

4. 最后一步：使用~在你界面类中，刚才继承的ListActivity基类的第二个泛型参数，传入我们处理的ScrollablePinnedSectionListView。重写onInitScrollHelper()方法。

| 函数名        | 返回值   | 函数解释和功用  |
| --------   | -----:  | :----:  |
|onInitScrollHelper()|ListScrollHelper|返回滚动帮助类，这里要根据使用的列表使用不同的包裹类。需要对应喔~

```java
@Override
    public ListScrollHelper<ScrollablePinnedSectionListView> onInitScrollHelper() {
        return new ListScrollHelper<ScrollablePinnedSectionListView>(new ScrollablePinnedSectionListViewWrapper(getScrollableView()));
    }
```

布局中使用的控件也需要对应！就是我们定义的Scrollable系列控件！

```java
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshLayout
        android:id="@id/fast_refresh_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <oms.mmc.android.fast.framwork.widget.lv.ScrollablePinnedSectionListView
            android:id="@id/fast_list"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:scrollbars="vertical" />
    </oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshLayout>
</FrameLayout>
```

# 一些需要注意的点

- 要使用ListView的粘性头部控件：ScrollablePinnedSectionListView，必须ViewType多余2个，这个是开源控件规定的，所以如果只有一个Type的话，请使用ScrollableListView。

- 这个开源控件有个bug，强制要求需要粘性的条目的ViewType必须是0！这里就有点坑了，所以定义ViewType的时候，将需要粘性的条目类型定义为0！并且onStickyTplViewTypeReady()返回粘性头部时，也返回这个对应的类型，否则也是会抛异常喔。rv的则不会，所以尽量不要使用lv啦。

--------------------

# 1.0.3版本

```java
compile 'oms.mmc:fast-mvc:1.0.3-SNAPSHOT@aar'
//  -------------------------- fast mvc 依赖的库 start ----------------------------
    //生命周期监听库
    compile 'oms.mmc:lifecycle-dispatch:1.0.1-SNAPSHOT@aar'
    //通用滚动监听库
    compile 'oms.mmc:list-scroll-helper:1.0.3-SNAPSHOT@aar'
    //等待弹窗库
    compile 'oms.mmc:wait-view-factory:1.0.3-SNAPSHOT@aar'
    //界面切换状态库
    compile 'oms.mmc:load-view-factory:1.0.4-SNAPSHOT@aar'
    //图片加载
    compile 'oms.mmc:imageLoader:1.0.0-SNAPSHOT@aar'
//    -------------------------- fast mvc 依赖的库 end ----------------------------
```

# 代码审核后，添加的方法（1.0.3版本）

 - 在代码审核期间，收到了小伙伴们的建议了和bug提出，在此深深感谢各位！

1. ViewFinder添加对View的操作封装（在BaseFastActivity、BaseFastFragment、BaseTpl中都有实现，所以直接类上直接调用即可）感谢景天~

> 文字，设置、获取操作。

| 函数名      |    返回值   |  函数解释和功用  |
| --------   | --------:  | :--------:  |
| isEmpty(CharSequence str) | boolean |判断字符集是否为空，直接调用传入字符串即可判断是否为空|
|isNotEmpty(CharSequence str)|boolean|判断字符集是否不为空，直接传入字符串即可判断是否不为空|
|viewTextIsEmpty(int viewId，boolean isFilterSpace)|boolean|判断指定id的View上的文字是否为空（isFilterSpace表示是否忽略空格，如果内容是空格，也算是空），需要id对应的View是TextView的子类时才有效，例如EditText、Button等，常用于EditText
|viewTextIsEmpty(TextView view，boolean isFilterSpace)|boolean|作用同上，直接传入一个View对象进行判断，必须是TextView的子类！
|viewTextIsEmptyWithTrim(int viewId)|boolean|viewTextIsEmpty()中的isFilterSpace为true的简单重载。
|viewTextIsEmptyWithTrim(TextView view)|boolean|viewTextIsEmpty()中的isFilterSpace为true的简单重载。
|viewTextIsNotEmpty(int viewId)|boolean|判断指定id的View上的文字是否不为空
|viewTextIsNotEmpty(TextView view)|boolean|判断指定View上的文字是否不为空
|setViewText(CharSequence text, int viewId)|void|使用控件id，设置文字，必须是TextView的子类，例如EditText。如果传入的text为null，则会设置一个""空字符串，常用于直接从bean上getXXX()，如果bean上的String类型的属性为null则方便直接设置为""，不再重复的if(TextUtil.isEmpty(bean.getXXX()))。
|setTextWithDefault(CharSequence text, CharSequence defaultText, int viewId)|void|使用控件id，进行设置文字，可以设置当传入的文字为null，这使用传入的默认值
|setViewText(CharSequence text, TextView view)|void|作用同上，只是直接传入一个View
|setTextWithDefault(CharSequence text, CharSequence defaultText, TextView view)|void|作用同上，只是直接传入一个View
|getViewText(TextView view)| CharSequence |获取TextView及其子类上的文字，如果为null，则默认返回一个""空字符串
|getTextWithDefault(TextView textView, CharSequence defaultText)| CharSequence |获取TextView及其子类上的文字，可以设置一个默认值，当获取为""时，返回默认值
|CharSequence getTextWithDefault(@IdRes int viewId, CharSequence defaultText)| CharSequence |作用同上，传入View的id，注意必须是TextView及其子类
|getViewTextWithTrim(int viewId)|CharSequence|获取TextView及其子类的内容并且调用Trim()，常用于EditText。
|getViewTextWithTrim(TextView view)|CharSequence|作用同上，只是直接传入一个TextView及其子类。

> 设置监听器

| 函数名        |  返回值  |  函数解释和功用  |
| --------   | -----:  | :----:  |
|findAndSetOnClick(int id, View.OnClickListener listener)|View|查找View，并且设置OnClick点击监听。
|findAndSetOnLongClick(int id, View.OnLongClickListener listener)|View|查找View，并且设置OnLongClick长按监听。
|setOnClickListener(View.OnClickListener listener, int... ids)|void|传入多View的id，统一设置一个点击监听器。
|setOnClickListener(View.OnClickListener listener, View... views)|void|传入多个View对象，统一设置一个点击监听器。
|setOnLongClickListener(View.OnLongClickListener listener, int... ids)|void|传入多个View的id，统一设置一个长按事件监听器。
|setOnLongClickListener(View.OnLongClickListener listener, View... views)|void|传入多个View对象，统一设置一个长按监听。

> 显示、隐藏

| 函数名        |  返回值  |  函数解释和功用  |
| --------   | -----:  | :----:  |
|setVisible(int... ids)|void|传入多个View的id，并且设置显示。
|setInVisible(int... ids)|void|传入多个View的id，并且设置隐藏占位。
|setGone(int... ids)|void|传入多个View的id，并设置隐藏。
|setVisible(View... views)|void|传入多个View对象，并且设置显示
|setInVisible(View... views)|void|传入多个View对象，并且设置隐藏占位|
|setGone(View... views)|void|传入多个View对象，并且设置隐藏。

> 图片加载

| 函数名        |  返回值  |  函数解释和功用  |
| --------   | -----:  | :----:  |
|getImageLoader()|ImageLoader|获取图片加载器，需要对图片加载器做操作时调用，如需要调用加载方法，请调用下面具体的loadXxx方法|
|loadUrlImage(String url, ImageView imageView, @IdRes int defaultImage)|void|加载网络图片到ImageView|
|loadUrlImageToRound(String url, ImageView imageView, @IdRes int defaultImage)|void|加载网络圆形图片到ImageView|
|loadUrlImageToCorner(String url, ImageView imageView, @IdRes int defaultImage)|void|加载网络圆角图片到ImageView圆角|
|loadFileImage(String filePath, ImageView imageView, @IdRes int defaultImage)|void|加载内存卡图片到ImageView|
|loadImageToBitmap(String url, LoadImageCallback loadImageCallback)|void|加载图片bitmap并回调设置的监听器
|loadDrawableResId(@IdRes int imageViewId, @DrawableRes int resId)|void|加载本地Res图片到ImageView
|loadDrawableResId(ImageView imageView, @DrawableRes int resId)|void|加载本地Res图片到ImageView
|clearImageMemoryCache()|void|清除图片缓存

2. 增加对Fragment的操作方法，感谢景天~

| 函数名        |  返回值  |  函数解释和功用  |
| --------   | -----:  | :----:  |
|createFragment(Class<T> fragmentClass)|Fragment|传入Fragment的Class，创建实例
|createFragment(Class<T> fragmentClass, Bundle args)| Fragment |传入Fragment的Class，和Bundle参数来实例化Fragment|
|isExistFragment()|查找是否存在Fragment|当需要判断是否有fragment存在时调用，但是同样我们都是去查找特定的Fragment是否存在，所以建议调用findFragment（）进行查找，因为在权限申请等，会插入一个代理的fragment来转接onPermissionResult()。
|findFragment(Class<? extends Fragment> fragmentClass)|Fragment|查找Fragment，该库添加的Fragment默认用Fragment的Name作为Tag。
|addFragment(Fragment fragment, int containerId)|void|添加Fragment到指定的容器id上|
|replaceFragment(Fragment fragment, @IdRes int containerViewId)|void|替换指定容器id上的Fragment
|hideFragment(Fragment fragment)|void|隐藏指定的Fragment
|removeFragment(Fragment fragment)|void|移除指定的Fragment
|removeFragments()|void|移除同级别的Fragment
|hideAllFragments()|void|移除所有的Fragment
|hideShowFragment(@NonNull Fragment hideFragment, @NonNull Fragment showFragment)|void|先隐藏指定的Fragment，再显示指定的Fragment

> Toast封装方法，也在BaseFastActivity、BaseFastFragment、BaseTpl上有实现。使用时直接调用即可。

| 函数名        |  返回值  |  函数解释和功用  |
| --------   | -----:  | :----:  |
|toast(int message)|void|以资源id，提示一个短时间的Toast
|toast(CharSequence message)|void|以CharSequence字符集，提示一个短时间的Toast
|toastLong(int message)|void|以资源id，提示一个长时间的Toast
|toastLong(CharSequence message)|void|以String字符集，提示一个长时间的Toast
|toast(int message, int duration)|void|可外部定义Toast时长长短的Toast，一般不使用，直接使用toast缺省duration参数和toastLong即可。
|toast(CharSequence message, int duration)|void|可外部定义Toast时长长短的Toast，一般不使用，直接使用toast缺省duration参数和toastLong即可。

> Tpl新增方法，之前Tpl注册、注销广播，是在onLayoutBefore()和onRecyclerViewDetachedFromWindow()，不够明确，现拆分2个方法提供重写，onCreate()进行注册，onDestroy()注销。

| 函数名        |  返回值  |  函数解释和功用  |
| --------   | -----:  | :----:  |
|onCreate()|当Tpl被创建时调用，（视图还未初始化），做视图操作需要在onFindView()，onLayoutAfter()|做广播注册
|onDestroy()|当Tpl被销毁是调用，是在RecyclerView从Window中移除时调用|做广播销毁工作

> 一些BUG修复和调整

- Fragment上的onWaitDialogFactoryReady()，原本是Activity和Fragment共用控制一个WaitDialog就使用了一个静态控制器，在生命周期期间去注册和注销，Activity和Fragment在内存重启时，生命周期不定，很容易导致内存泄露，现分开控制，就是说Activity和Fragment上的onWaitDialogFactoryReady()并不会覆盖，建议Activity作为容器，Fragment做界面实现。如果是列表界面，BaseTpl上的控制WaitView则是控制列表依赖的界面宿主，既和Fragment或者Activity共用。

- 状态布局切换时导致的内存泄露，原因是自定义状态布局时，如果有一个View使用动画进行变化，当状态切换时，直接进行removeView的方法并不能停止动画，即使自定义View上有canel动画的操作，让自定义View保证在onDetachViewForWindow去做销毁动画的操作也十分容易漏，所以统一在移除之前遍历布局所有的子View，调用clearAnimation()彻底销毁动画。解决掉这次的内存泄漏。

--------

# 1.0.2版本

# 使用
1. gradle 引用

```java
        compile 'oms.mmc:fast-mvc:1.0.2-SNAPSHOT@aar'
    //-------------------------- fast mvc 依赖的库 start ----------------------------
    //生命周期监听库
    compile 'oms.mmc:lifecycle-dispatch:1.0.1-SNAPSHOT@aar'
    //通用滚动监听库
    //通用滚动监听库
    compile 'oms.mmc:list-scroll-helper:1.0.3-SNAPSHOT@aar'
    //等待弹窗库
    compile 'oms.mmc:wait-view-factory:1.0.2-SNAPSHOT@aar'
    //界面切换状态库
    compile 'oms.mmc:load-view-factory:1.0.4-SNAPSHOT@aar'
    //图片加载
    compile 'oms.mmc:imageLoader:1.0.0-SNAPSHOT@aar'
    //-------------------------- fast mvc 依赖的库 end
```

2. 由于使用ListScrollHelper，如果使用BaseFastFragment时不是使用BaseFastActivity为宿主，则需要在Activity的onCreate()方法的第一行调用以下代码：

```java
ScrollableViewFactory.create(this, new AppCompatScrollableReplaceAdapter()).install();
```

3. 如果使用的RecyclerView、ListView、GridView、ScrollView、NestedScrollView不是直接是官方的，而是修改的，则需要将他们的继承改为以下表格上的类。如果是直接使用官方的，则不需要！（上面的工厂替换方法，会自动给我们替换）

| 类名        | 需要改为继承的类
| --------   | -----:
|ListView|ScrollableListView|
|GridView|ScrollableGridView|
|ScrollView|ScrollableScrollView|
|RecyclerView|ScrollableRecyclerView|
|NestedScrollView|ScrollableNestedScrollView|

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
|onListDataSourceReady()|请求接口，拿列表接口数据| 返回数据集实例，一般是BaseListDataSource，复写load方法，在上面写请求，该函数会在子线程调用，因为该方法已经在子线程回调，所以请求方法要是同步阻塞的，而不是异步，否则会直接返回数据集，造成数据不正确或者为空  |
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
|onRecyclerViewDetachedFromWindow()|当条目依附的RecyclerView从视图树中移除时回调|（这里可以做EventBus或者广播注销操作）该方法已经分拆成onDestroy()方法，推荐复写该方法
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
        toast(getActivity(), "收到前面传递过来的userId -> " + mUserId);
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