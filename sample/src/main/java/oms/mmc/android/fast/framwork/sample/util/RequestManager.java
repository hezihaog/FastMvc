package oms.mmc.android.fast.framwork.sample.util;

import com.hzh.nice.http.NiceApiClient;
import com.hzh.nice.http.base.ApiParams;

import oms.mmc.android.fast.framwork.sample.bean.ContactList;
import oms.mmc.android.fast.framwork.sample.bean.Result;
import oms.mmc.android.fast.framwork.sample.constant.Const;

/**
 * Package: oms.mmc.android.fast.framwork.sample.util
 * FileName: RequestManager
 * Date: on 2018/5/24  上午7:48
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class RequestManager {
    /**
     * 获取联系人列表
     *
     * @param page 页码
     * @param size 每页的条数
     */
    public static Result getAllContact(String page, String size) throws Exception {
        ApiParams params = new ApiParams();
        params.add(Const.Key.page, page);
        params.add(Const.Key.size, size);
        return (Result) NiceApiClient.getInstance().getApi()
                .getSync(Const.Api.DOMIN + Const.Api.BASE_URL + Const.Api.ALL_CONTACT_LIST,
                        params, ContactList.class);
    }
}