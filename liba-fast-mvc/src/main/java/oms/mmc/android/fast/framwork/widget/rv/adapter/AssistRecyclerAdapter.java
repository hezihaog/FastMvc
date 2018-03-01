package oms.mmc.android.fast.framwork.widget.rv.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import oms.mmc.android.fast.framwork.util.EasySparseArrayCompat;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: AssistRecyclerAdapter
 * Date: on 2018/2/12  下午3:13
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class AssistRecyclerAdapter<T extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<T> implements IAssistRecyclerAdapter {
    /**
     * 多选时使用
     */
    private EasySparseArrayCompat<Object> checkedItemPositions = new EasySparseArrayCompat<Object>();
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

    private ArrayList<OnAttachedToRecyclerViewListener> mAttachedRecyclerListener = new ArrayList<OnAttachedToRecyclerViewListener>();
    private ArrayList<onViewAttachedToWindowListener> mAttachedWindowListener = new ArrayList<onViewAttachedToWindowListener>();

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        for (OnAttachedToRecyclerViewListener listener : mAttachedRecyclerListener) {
            listener.onAttachedToRecyclerView();
        }
    }

    @Override
    public void addOnAttachedToRecyclerViewListener(OnAttachedToRecyclerViewListener listener) {
        if (!mAttachedRecyclerListener.contains(listener)) {
            mAttachedRecyclerListener.add(listener);
        }
    }

    @Override
    public void removeOnAttachedToRecyclerViewListener(OnAttachedToRecyclerViewListener listener) {
        mAttachedRecyclerListener.remove(listener);
    }

    @Override
    public void removeAllOnAttachedToRecyclerViewListener() {
        mAttachedRecyclerListener.clear();
    }

    @Override
    public void onViewAttachedToWindow(T holder) {
        super.onViewAttachedToWindow(holder);
        for (onViewAttachedToWindowListener listener : mAttachedWindowListener) {
            listener.onViewAttachedToWindow(holder);
        }
    }

    @Override
    public void addOnViewAttachedToWindowListener(onViewAttachedToWindowListener listener) {
        mAttachedWindowListener.add(listener);
    }

    @Override
    public void removeOnViewAttachedToWindowListener(onViewAttachedToWindowListener listener) {
        mAttachedWindowListener.remove(listener);
    }

    @Override
    public void removeAllOnViewAttachedToWindowListener() {
        mAttachedWindowListener.clear();
    }

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
    public void setCheckedItemPositions(EasySparseArrayCompat<Object> checkedItemPositions) {
        this.checkedItemPositions = checkedItemPositions;
    }

    @Override
    public void clearCheckedItemPositions() {
        this.checkedItemPositions.clear();
    }

    @Override
    public EasySparseArrayCompat<Object> getCheckedItemPositions() {
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
