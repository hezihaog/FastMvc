package oms.mmc.android.fast.framwork.config.inter;

/**
 * Created by Hezihao on 2017/8/5.
 * 常用的EditText保存、恢复操作
 */

public interface InputAction {
    /**
     * 保存输入
     */
    void saveInput(String inputText);

    /**
     * 获取输入
     */
    String getInput();

    /**
     * 获取输入可设置默认值
     *
     * @param defaultValue 默认值
     */
    String getInput(String defaultValue);
}
