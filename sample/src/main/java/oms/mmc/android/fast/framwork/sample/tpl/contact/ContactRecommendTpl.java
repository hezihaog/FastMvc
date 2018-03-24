package oms.mmc.android.fast.framwork.sample.tpl.contact;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tmall.ultraviewpager.UltraViewPager;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.util.TDevice;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.contact
 * FileName: ContactRecommendTpl
 * Date: on 2018/2/14  下午6:30
 * Auther: zihe
 * Descirbe:推荐好友ViewPager条目
 * Email: hezihao@linghit.com
 */

public class ContactRecommendTpl extends BaseTpl<ItemDataWrapper> {
    private UltraViewPager mUltraViewPager;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.item_contact_recomment, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
        super.onFindView(finder);
        mUltraViewPager = finder.get(R.id.viewPager);
    }

    @Override
    protected void onBindContent() {
        mUltraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        //UltraPagerAdapter 绑定子view到UltraViewPager
        Adapter adapter = new Adapter();
        mUltraViewPager.setAdapter(adapter);
        //内置indicator初始化
        mUltraViewPager.initIndicator();
        //设置indicator样式
        mUltraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.GREEN)
                .setNormalColor(Color.WHITE)
                .setIndicatorPadding((int) TDevice.dpToPixel(getActivity(), 4f))
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.8f, getActivity().getResources().getDisplayMetrics()));
        //设置indicator对齐方式
        mUltraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM).setMargin(0, 0, 0, (int) TDevice.dpToPixel(getActivity(), 5f));
        //构造indicator,绑定到UltraViewPager
        mUltraViewPager.getIndicator().build();
        //设定页面循环播放
        mUltraViewPager.setInfiniteLoop(true);
        //设定页面自动切换  间隔2秒
        mUltraViewPager.setAutoScroll(1500);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
    }

    private class Adapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            LinearLayout layout = (LinearLayout) LayoutInflater.from(container.getContext())
                    .inflate(R.layout.view_contact_recomment_view_pager_child, null);
            TextView textView = (TextView) layout.findViewById(R.id.pager_text_view);
            textView.setText(String.valueOf(position));
            switch (position) {
                case 0:
                    layout.setBackgroundColor(Color.parseColor("#2196F3"));
                    break;
                case 1:
                    layout.setBackgroundColor(Color.parseColor("#673AB7"));
                    break;
                case 2:
                    layout.setBackgroundColor(Color.parseColor("#009688"));
                    break;
                case 3:
                    layout.setBackgroundColor(Color.parseColor("#607D8B"));
                    break;
                case 4:
                    layout.setBackgroundColor(Color.parseColor("#F44336"));
                    break;
                default:
                    break;
            }
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toast("好友推荐 banner onClick... position ::: " + position);
                }
            });
            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            LinearLayout view = (LinearLayout) object;
            container.removeView(view);
        }
    }
}