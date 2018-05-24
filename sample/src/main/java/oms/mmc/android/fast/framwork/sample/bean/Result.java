package oms.mmc.android.fast.framwork.sample.bean;

/**
 * Package: entity
 * FileName: Result
 * Date: on 2018/5/21  下午9:57
 * Auther: Wally
 * Descirbe:
 */
public class Result extends Base implements com.hzh.nice.http.inter.Result {
    public static final String SUCCESS = "0";
    public static final String ERROR = "1";

    public String status = "";
    public String msg = "";

    public void setSuccess() {
        this.status = SUCCESS;
    }

    public void setError() {
        this.status = ERROR;
    }

    public boolean isOk() {
        return SUCCESS.equals(status);
    }

    public boolean isNoOk() {
        return !isOk();
    }

    public String getMsg() {
        return msg;
    }
}