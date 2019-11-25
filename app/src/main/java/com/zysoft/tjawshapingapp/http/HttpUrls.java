package com.zysoft.tjawshapingapp.http;

import com.zysoft.tjawshapingapp.constants.AppConstant;

/**
 * Created by mr.miao on 2019/5/8.
 */

public class HttpUrls {

    public static final String GET_PROJECTOR_DERDETAIL = "getProjectOrderDetail";
    public static String LOGIN = "login";
    public static String CHECK = "checkMobile";
    public static String WX_BIND_TEL = "wxbindTel";

    public static String CHECK_CODE = "checkCode";
    public static String REGEDIT = "regedit";

    public static String GET_CODE = "getVerifyCode";
    public static String UPDATEPSD = "updatePsd";
    public static String GET_HOME_DATA = "getHomeData";
    public static String GET_PRODUCT_HOME_DATA = "getProductHomeData";
    public static String GET_PROJECT_DETAIL = "getProjectDetail";
    public static String GET_PRODUCT_DETAIL = "getProductDetail";
    public static String USER = "getUser";
    public static String GETUSERCOUPONS = "getUserCouponsForProject";
    public static String GETUSERCOUPONSFORPRODUCT = "getUserCouponsForProduct";
    public static String CREATEORDER = "createProjectOrder";
    public static String CREATEPRODUCTORDER = "createProductOrder";
    public static String GET_ORDER_DATA = "getProjectOrder";
    public static String GET_VIDEO = "getVideo";
    public static String GET_OPTION_DATA = "getProject";
    public static String bindJPushClientID = "bindJPushClientID";
    public static String GET_COUPONS_LIST = "getUserCouponsList";
    public static String WXLOGIN = "getWXLoginInfo";
    public static String GET_ADDRESS = "getAddress";
    public static String ADD_ADDR = "addressManage";
    public static String CANCEL_ORDER = "cancelOrder";
    public static final String GET_USER_WALLET = "getUserWallet";
    public static final String GET_REAL_STATE = "getUserRealInfo";
    public static final String CREATE_PRODUCT_ORDER="createProductOrder";
    public static String DEL_CART="delCartProduct";
    public static String GET_CART = "getCart";
    public static String ADD_CART = "addUserCart";
    public static String UPDATE_CART = "updateUserCart";
    public static String GET_USER_DEFAULT="getUserDefaultAddr";
    public static String CONFIRM_ORDER_USER="confirmOrderUser";
    public static String GET_USER="getUser";
    public static String UPDATE_PSD_CENTER="updateUserPsd";
    public static String COMMIT_FEED_BACK="commitFeedBack";
    public static String RECHARGE="createRechargeOrder";
    public static String APPLY_MEMBER="createApplyOrder";
    public static String GET_QR_CODE="getQrCode";


    public static String getBaseUrl() {
        if (AppConstant.isDebug) {

            return "http://192.168.1.100:8098/";

        } else {

            return "http://awapp.beauty521.com/tjawserverapp/";

        }
    }


}
