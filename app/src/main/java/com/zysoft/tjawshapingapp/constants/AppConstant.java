package com.zysoft.tjawshapingapp.constants;

import com.zysoft.tjawshapingapp.bean.AddressBean;
import com.zysoft.tjawshapingapp.bean.CouponsBean;
import com.zysoft.tjawshapingapp.bean.OrderResultBean;
import com.zysoft.tjawshapingapp.bean.ProjectDetailBean;
import com.zysoft.tjawshapingapp.bean.UserCartBean;
import com.zysoft.tjawshapingapp.bean.UserInfoBean;

import java.util.List;

/**
 * Created by mr.miao on 2019/5/6.
 */

public class AppConstant {

    /**
     * 0 订单支付
     * 1 充值钱包
     * 2 购买礼包 会员 一级代理 二级代理
     */
    public static  int PAY_TYPE = 0;
    public static boolean IS_REGEDIT = true;
    public static boolean isDebug = false;
    public static boolean isAllowLog = true;
    public static final String STATE_TIMEOUT = "STATE_TIMEOUT";
    public static final String STATE_USER_NOEXIT = "STATE_USER_NOEXIT";
    public static String MsgId = null;
    public static String USER_PHONE = "";
    public static UserInfoBean USER_INFO_BEAN = null;
    public static ProjectDetailBean.ProjectInfoBean PROJECT_INFO = null;
    public static CouponsBean Coupons = null;
    public static final String STATE_BIND_TEL = "STATE_BIND_TEL";
    public static boolean isBindTel=false;
    public static AddressBean SELECT_ADDR=null;
    public static boolean isShowDialog = true;
    public static List<UserCartBean> SELECT_CART_LIST=null;
    public static OrderResultBean ORDER_RESULT_BEAN=null;
}
