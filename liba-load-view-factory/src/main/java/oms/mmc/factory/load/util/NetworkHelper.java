package oms.mmc.factory.load.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Package: oms.mmc.factory.load
 * FileName: NetworkHelper
 * Date: on 2018/2/24  上午11:56
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class NetworkHelper {
    /**
     * 是否有网络连接
     *
     * @param paramContext
     * @return
     */
    @SuppressLint("MissingPermission")
    public static boolean hasNetwork(Context paramContext) {
        try {
            ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
            if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable())) {
                return true;
            }
        } catch (Throwable localThrowable) {
            localThrowable.printStackTrace();
        }
        return false;
    }
}