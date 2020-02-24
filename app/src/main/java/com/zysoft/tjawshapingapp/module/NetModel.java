package com.zysoft.tjawshapingapp.module;

import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Throwable;
import com.tamic.novate.download.DownLoadCallBack;
import com.zysoft.tjawshapingapp.common.LogUtils;
import com.zysoft.tjawshapingapp.constants.AppConstant;
import com.zysoft.tjawshapingapp.constants.NetResponse;
import com.zysoft.tjawshapingapp.http.HttpConstant;
import com.zysoft.tjawshapingapp.http.NovateUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;


/**
 * Created by mr.miao on 2019/3/26.
 */

public class NetModel {

    private static NetModel netModel;

    private NetModel() {
    }

    public static NetModel getInstance() {
        if (netModel != null) {
            return netModel;
        } else {
            NetModel netModel = new NetModel();
            return netModel;
        }
    }


    public void getAllData(final String tag, String url, HashMap<String, Object> map) {
        LogUtils.e("************** 网络请求"+tag+" ************");
        LogUtils.e("请求参数："+"tag="+tag+"\n"+"url="+url+"\n"+"参数="+map.toString());
        NovateUtil.getInstance().post(url, map, new BaseSubscriber<ResponseBody>() {
            @Override
            public void onStart() {
                super.onStart();
                EventBus.getDefault().post(new NetResponse(HttpConstant.PROGRESS_DIALOG, ""));
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                EventBus.getDefault().post(new NetResponse(HttpConstant.PROGRESS_DIALOG_DISMISS, ""));
                LogUtils.e("************** 网络请求结束"+tag+" ************");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                EventBus.getDefault().post(new NetResponse(HttpConstant.STATE_ERROR, "网络连接失败！"));

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    LogUtils.e("请求返回数据：："+string);
                    JSONObject jsonObject = new JSONObject(string);
                    String result = jsonObject.getString("result");
                    switch (result) {
                        case "S":
                            String data = jsonObject.getString("data");
                            EventBus.getDefault().post(new NetResponse(tag, data));
                            break;
                        case "F":
                            EventBus.getDefault().post(new NetResponse(HttpConstant.STATE_ERROR, jsonObject.getString("msg")));
                            break;
                        case "T":
                            EventBus.getDefault().post(new NetResponse(AppConstant.STATE_TIMEOUT, jsonObject.getString("msg")));
                            break;
                        case "N":
                            EventBus.getDefault().post(new NetResponse(AppConstant.STATE_USER_NOEXIT, jsonObject.getString("data")));
                            break;
                        case "B":
                            EventBus.getDefault().post(new NetResponse(AppConstant.STATE_BIND_TEL,jsonObject.getString("data")));
                            break;
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getDataFromNet(final String tag, String url, HashMap<String, Object> map) {
        LogUtils.e("************** 网络请求"+tag+" ************");
        LogUtils.e("请求参数："+"tag="+tag+"\n"+"url="+url+"\n"+"参数="+map.toString());
        NovateUtil.getInstance().post(url, map, new BaseSubscriber<ResponseBody>() {
            @Override
            public void onStart() {
                super.onStart();
                EventBus.getDefault().post(new NetResponse(HttpConstant.PROGRESS_DIALOG, ""));
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                EventBus.getDefault().post(new NetResponse(HttpConstant.PROGRESS_DIALOG_DISMISS, ""));
                LogUtils.e("************** 网络请求结束"+tag+" ************");

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                EventBus.getDefault().post(new NetResponse(HttpConstant.STATE_ERROR, "网络连接失败！"));

            }

            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String string = responseBody.string();
                    LogUtils.e("请求返回数据：："+string);
                    JSONObject jsonObject = new JSONObject(string);
                    String result = jsonObject.getString("result");

                    switch (result) {
                        case "S":
                            String data = jsonObject.getString("data");
                            EventBus.getDefault().post(new NetResponse(tag, data));
                            break;
                        case "F":
                            EventBus.getDefault().post(new NetResponse(HttpConstant.STATE_ERROR, jsonObject.getString("msg")));
                            break;
                        case "T":
                            EventBus.getDefault().post(new NetResponse(AppConstant.STATE_TIMEOUT, jsonObject.getString("msg")));
                            break;
                        case "N":
                            EventBus.getDefault().post(new NetResponse(AppConstant.STATE_USER_NOEXIT, jsonObject.getString("data")));
                            break;
                        case "B":
                            EventBus.getDefault().post(new NetResponse(AppConstant.STATE_BIND_TEL,jsonObject.getString("data")));
                            break;
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void getVersion(final String tag, String url, HashMap<String, Object> map) {
        LogUtils.e("************** 网络请求"+tag+" ************");
        LogUtils.e("请求参数："+"tag="+tag+"\n"+"url="+url+"\n"+"参数="+map.toString());
        NovateUtil.getInstance().post(url, map, new BaseSubscriber<ResponseBody>() {
            @Override
            public void onStart() {
                super.onStart();
                EventBus.getDefault().post(new NetResponse(HttpConstant.PROGRESS_DIALOG, ""));
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                EventBus.getDefault().post(new NetResponse(HttpConstant.PROGRESS_DIALOG_DISMISS, ""));
                LogUtils.e("************** 网络请求结束"+tag+" ************");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                EventBus.getDefault().post(new NetResponse(HttpConstant.STATE_ERROR, "网络连接失败！"));

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    EventBus.getDefault().post(new NetResponse(tag, string));

                    LogUtils.e("请求返回数据：："+string);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
