# FastMvc开发框架说明文档

------

### 以前的框架，我们是怎样进行开发的？

 - 以前的都是在MMCSDK上的基类Activity、Fragment上进行子类开发，但是例如 **查找控件** ，**给控件进行相关设置**，没有一个明确的地方进行设置，大家都是自己起函数或者直接在查找控件后，直接就写上了。

### 这样的方式有什么问题呢？

- 没有一个约定俗成的函数名，大量相同的操作，每个人都自己写一套，下一个组员进行迭代开发的时候，想找相关查找控件时或者进行相关操作时，首先只能搜索onCreate方法，然后从中再来看，可能是写一起的，也有可能是分拆成其他函数了，每个界面都要重新找一遍，造成了时间浪费，如果找不到，那只能找上次开发的组员了
- 如果加上函数安放顺序也可能不一样，函数调用顺序和代码上的不一致，看逻辑时只能跳来跳去，一个Activity上千行的话（老项目还真有），可想而知，那感觉真难受。

### 所以？

- 所以要改变这样的现状。让项目迭代组员沟通量小，代码风格统一，BUG量减少。我定义了这样的一套开发框架。

------

# 要面对的Api

- Activity基类：BaseFastActivity
- Fragment基类：BaseFastFragment
- 自带列表的Activity基类：BaseFastListActivity
- 自带列表的Fragment基类：BaseFastListFragment
- 列表条目类：BaseTpl、BaseStickyTpl

# 约定俗称的函数

- onLayoutBefore() ----> 在setContentView ：之前会调用
