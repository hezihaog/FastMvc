package oms.mmc.android.fast.framwork.widget.list.sticky;

/**
 * Package: oms.mmc.android.fast.framwork.widget.list.sticky
 * FileName: ItemStickyDelegate
 * Date: on 2018/3/30  下午7:06
 * Auther: zihe
 * Descirbe:粘性条目代理
 * Email: hezihao@linghit.com
 */
public class ItemStickyDelegate {
    /**
     * 不使用粘性头部
     */
    public static final int NOT_STICKY_SECTION = -1;
    /**
     * 粘性条目的类型，默认没有粘性头部
     */
    private int stickySectionViewType = NOT_STICKY_SECTION;

    public ItemStickyDelegate() {
    }

    /**
     * 设置粘性条目类型
     */
    public void setStickySectionViewType(int stickySectionViewType) {
        this.stickySectionViewType = stickySectionViewType;
    }

    /**
     * 是否没有粘性条目
     */
    public boolean isNotStickySection() {
        return this.stickySectionViewType == NOT_STICKY_SECTION;
    }

    /**
     * 是否是粘性条目
     *
     * @param viewType 条目类型
     */
    public boolean isStickyItem(int viewType) {
        return this.stickySectionViewType == viewType;
    }
}