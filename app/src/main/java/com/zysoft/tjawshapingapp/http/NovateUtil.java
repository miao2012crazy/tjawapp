package com.zysoft.tjawshapingapp.http;

import com.tamic.novate.Novate;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.tjawshapingapp.common.DeviceUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mr.miao on 2018/7/30.
 */

public class NovateUtil {

    private static Novate novate;
    private static Novate.Builder builder;

    public static Novate getInstance() {
        if (novate != null) {
            return novate;
        }
        //连接时间 可以忽略
        Map<String, String> headers = new HashMap<>();
//        headers.put("Cache-Control", "max-age=1000*60");
        try {
            String adresseMAC = DeviceUtils.getUniqueID();
            headers.put("uuid", adresseMAC);
            if (AppConstant.USER_INFO_BEAN == null) {
                headers.put("userTel", "");

            } else {
                headers.put("userTel", AppConstant.USER_INFO_BEAN.getUserTel());
            }

        } catch (Exception ex) {

        }
        builder = new Novate.Builder(UIUtils.getContext());
        return initBuilder();
    }

    public static Novate initBuilder() {
        Map<String, String> headers = new HashMap<>();
//        headers.put("Cache-Control", "max-age=1000*60");
        try {
            String adresseMAC = DeviceUtils.getUniqueID();
            headers.put("uuid", adresseMAC);
            if (AppConstant.USER_INFO_BEAN == null) {
                headers.put("userTel", "");
            } else {
                headers.put("userTel", AppConstant.USER_INFO_BEAN.getUserTel());
            }

        } catch (Exception ex) {
        }
        novate = builder
                .baseUrl(HttpUrls.getBaseUrl())
                .addHeader(headers)
                .addCache(true)
                .addCookie(true)
                .connectTimeout(8)  //连接时间 可以忽略
                .addLog(true)
                .build();
        return novate;
    }


    public static Api initApi() {
        return getInstance().create(Api.class);
    }

}
