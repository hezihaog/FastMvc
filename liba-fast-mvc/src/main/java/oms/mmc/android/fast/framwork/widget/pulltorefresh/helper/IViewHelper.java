package oms.mmc.android.fast.framwork.widget.pulltorefresh.helper;

public interface IViewHelper {
	 void refresh();
	 ILoadViewFactory.ILoadMoreView getLoadMoreView();
	 ILoadViewFactory.ILoadView getLoadView();
}