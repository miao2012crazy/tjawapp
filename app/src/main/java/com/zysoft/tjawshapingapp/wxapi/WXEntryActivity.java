package com.zysoft.tjawshapingapp.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zysoft.tjawshapingapp.base.CustomBaseActivity;
import com.zysoft.tjawshapingapp.bean.WXBean;
import com.zysoft.tjawshapingapp.common.GsonUtil;
import com.zysoft.tjawshapingapp.common.UIUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;

import org.greenrobot.eventbus.EventBus;

import me.jessyan.autosize.utils.LogUtils;

/**
 * description ：
 * project name：CCloud
 * author : Vincent
 * creation date: 2017/6/9 18:13
 *
 * @version 1.0
 */

public class WXEntryActivity extends CustomBaseActivity implements IWXAPIEventHandler {

    /**
     * 微信登录相关
     */
    private IWXAPI api;
    private Gson gson;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(this, WXIDConstants.APP_ID, true);
        //将应用的appid注册到微信
        api.registerApp(WXIDConstants.APP_ID);
        gson = new Gson();

        LogUtils.d("------------------------------------");
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            boolean result = api.handleIntent(getIntent(), this);
            if (!result) {
//                LogUtils.d("参数不合法，未被SDK处理，退出");
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        api.handleIntent(data, this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {
        LogUtils.d("baseReq:" + gson.toJson(baseReq));
    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtils.d("baseResp:" + gson.toJson(baseResp));
        LogUtils.d("baseResp:" + baseResp.errStr + "," + baseResp.openId + "," + baseResp.transaction + "," + baseResp.errCode);
        String result = "";

        if(baseResp.getType()== ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){
//            Toast.makeText(WXEntryActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "正在登录";
                LogUtils.e("1");
                String gsonString = gson.toJson(baseResp);
                WXBean wxBean = GsonUtil.GsonToBean(gsonString, WXBean.class);
                EventBus.getDefault().post(new NetResponse("WXLOGINCODE",wxBean));
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:

                result = "取消登录";
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:

                result = "访问被拒绝";
                finish();
                break;
            default:

                result = "";
                finish();
                break;
        }
        UIUtils.showToast(result);
    }
}