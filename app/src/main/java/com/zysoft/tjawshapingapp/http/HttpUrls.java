package com.zysoft.tjawshapingapp.http;

import com.zysoft.tjawshapingapp.constants.AppConstant;

/**
 * Created by mr.miao on 2019/5/8.
 */

public class HttpUrls {

    public static String LOGIN="login";
    public static String CHECK="checkMobile";
    public static String CHECK_CODE="checkCode";
    public static String REGEDIT="regedit";

    public static String GET_CODE="getVerifyCode";
    public static String UPDATEPSD="updatePsd";
    public static String GET_HOME_DATA="getHomeData";

    public static String getBaseUrl() {
        if (AppConstant.isDebug) {
//            ic_return "http://192.168.1.101:8080/";
            return "http://101.37.64.119/tjawserverapp/";

        } else {
            return "http://101.37.64.119/tjawserverapp/";
        }
    }


}
