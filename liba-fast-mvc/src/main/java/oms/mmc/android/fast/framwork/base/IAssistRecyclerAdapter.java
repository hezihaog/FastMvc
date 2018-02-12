package oms.mmc.android.fast.framwork.base;

import java.util.List;
import java.util.Map;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IAssistRecyclerAdapter
 * Date: on 2018/2/12  下午3:20
 * Auther: zihe
 * Descirbe:辅助RecyclerViewAdapter
 * Email: hezihao@linghit.com
 */

public interface IAssistRecyclerAdapter {
    /**
     * 没有选中
     */
    int NOT_CHECK = -1;
    /**
     * 普通模式
     */
    int MODE_NORMAL = 0;
    /**
     * 管理模式
     */
    int MODE_EDIT = 1;

    /**
     * 设置一个Tag
     *
     * @param key   键
     * @param value 值
     */
    void putTag(String key, Object value);

    /**
     * 用Key获取一个Tag
     *
     * @param key key
     */
    Object getTag(String key);

    /**
     * 移除一个Tag，并返回移除的Tag
     *
     * @param key 键
     */
    Object removeTag(String key);

    /**
     * 获取所有Tag的集合
     */
    Map<String, Object> getTagList();

    /**
     * 获取单选选的条目位置
     */
    int getCheckedItemPosition();

    /**
     * 设置一个单选条目位置
     */
    void setCheckedItemPosition(int checkedItemPosition);

    /**
     * 设置一个多选集合
     */
    void setCheckedItemPositions(List<Integer> checkedItemPositions);

    /**
     * 获取多选集合
     */
    List<Integer> getCheckedItemPositions();

    /**
     * 设置一个模式
     */
    void setMode(int mode);

    int getMode();

    /**
     * 是否是编辑模式
     */
    boolean isEditMode();

    /**
     * 是否是普通模式
     */
    boolean isNormalMode();
}