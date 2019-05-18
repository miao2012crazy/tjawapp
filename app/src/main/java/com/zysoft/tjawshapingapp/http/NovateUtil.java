package com.zysoft.tjawshapingapp.http;

import android.location.Address;
import android.text.TextUtils;

import com.tamic.novate.Novate;
import com.zysoft.baseapp.commonUtil.SPUtils;
import com.zysoft.baseapp.commonUtil.UIUtils;
import com.zysoft.tjawshapingapp.MainActivity;
import com.zysoft.tjawshapingapp.common.CommonUtil;
import com.zysoft.tjawshapingapp.common.DeviceUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mr.miao on 2018/7/30.
 */

public class NovateUtil {

    private static Novate novate;

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
            headers.put("userTel", (String) SPUtils.getParam(UIUtils.getContext(),"USER_INFO",""));
        }catch (Exception ex){

        }
        novate = new Novate.Builder(UIUtils.getContext())
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
