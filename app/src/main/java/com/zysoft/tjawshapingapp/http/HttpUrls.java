package com.zysoft.tjawshapingapp.http;

import com.zysoft.tjawshapingapp.constants.AppConstant;

/**
 * Created by mr.miao on 2019/5/8.
 */

public class HttpUrls {

    public static final String GET_PROJECTOR_DERDETAIL = "getProjectOrderDetail";
    public static String LOGIN="login";
    public static String CHECK="checkMobile";
    public static String CHECK_CODE="checkCode";
    public static String REGEDIT="regedit";

    public static String GET_CODE="getVerifyCode";
    public static String UPDATEPSD="updatePsd";
    public static String GET_HOME_DATA="getHomeData";
    public static String GET_PROJECT_DETAIL="getProjectDetail";
    public static String USER="getUser";
    public static String GETUSERCOUPONS="getUserCouponsForProject";
    public static String CREATEORDER="createOrder";
    public static String GET_ORDER_DATA="getProjectOrder";
    public static String GET_VIDEO="getVideo";
    public static String GET_OPTION_DATA="getProject";
    public static String bindJPushClientID="bindJPushClientID";
    public static String GET_COUPONS_LIST="getUserCouponsList";
    public static String WXLOGIN="getWXLoginInfo";
    public static String GET_ADDRESS="getAddress";
    public static String ADD_ADDR="addressManage";
    public static String CANCEL_ORDER="cancelOrder";
    public static final String GET_USER_WALLET="getUserWallet";
    public static final String GET_REAL_STATE="getUserRealInfo";

    public static String getBaseUrl() {
        if (AppConstant.isDebug) {

            return "http://192.168.18.7:8098/";

        } else {

            return "http://101.37.64.119/tjawserverapp/";

        }
    }


}
