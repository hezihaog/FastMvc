package oms.mmc.android.fast.framwork.widget.list.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: AssistRecyclerAdapter
 * Date: on 2018/2/12  下午3:13
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class AssistHelper implements IAssistHelper {
    /**
     * 多选时使用
     */
    private HashMap<Integer, Object> checkedItemPositions = new HashMap<Integer, Object>();
    /**
     * 单选时使用
     */
    private int checkedItemPosition = NOT_CHECK;
    /**
     * 当前的选择模式
     */
    private int mode = MODE_NORMAL;
    /**
     * 可用于保存临时数据的容器
     */
    private Map<String, Object> tagList = new HashMap<String, Object>();

    @Override
    public void putTag(String key, Object value) {
        if (tagList == null) {
            tagList = new HashMap<String, Object>();
        }
        tagList.put(key, value);
    }

    @Override
    public Object getTag(String key) {
        if (tagList == null) {
            return null;
        }
        return tagList.get(key);
    }

    @Override
    public Object removeTag(String key) {
        if (tagList == null) {
            return null;
        }
        return tagList.remove(key);
    }

    @Override
    public Map<String, Object> getTagList() {
        return tagList;
    }

    @Override
    public int getCheckedItemPosition() {
        return checkedItemPosition;
    }

    @Override
    public void setCheckedItemPosition(int checkedItemPosition) {
        this.checkedItemPosition = checkedItemPosition;
    }

    @Override
    public void clearCheckedItemPosition() {
        this.checkedItemPosition = NOT_CHECK;
    }

    @Override
    public void setCheckedItemPositions(HashMap<Integer, Object> checkedItemPositions) {
        this.checkedItemPositions = checkedItemPositions;
    }

    @Override
    public void clearCheckedItemPositions() {
        this.checkedItemPositions.clear();
    }

    @Override
    public HashMap<Integer, Object> getCheckedItemPositions() {
        return checkedItemPositions;
    }

    @Override
    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public void setEditMode() {
        setMode(MODE_EDIT);
    }

    @Override
    public void setNormalMode() {
        setMode(MODE_NORMAL);
    }

    @Override
    public int getMode() {
        return mode;
    }

    @Override
    public boolean isEditMode() {
        return getMode() == MODE_EDIT;
    }

    @Override
    public boolean isNormalMode() {
        return getMode() == MODE_NORMAL;
    }
}
