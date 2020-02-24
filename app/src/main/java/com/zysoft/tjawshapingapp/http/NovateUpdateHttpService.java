/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zysoft.tjawshapingapp.http;

import android.support.annotation.NonNull;


import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Throwable;
import com.tamic.novate.download.DownLoadCallBack;
import com.xuexiang.xupdate.logs.UpdateLog;
import com.xuexiang.xupdate.proxy.IUpdateHttpService;
import com.zysoft.tjawshapingapp.common.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 使用okhttp
 *
 * @author xuexiang
 * @since 2018/7/10 下午4:04
 */
public class NovateUpdateHttpService implements IUpdateHttpService {

    public NovateUpdateHttpService() {

    }

    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, Object> params, final @NonNull Callback callBack) {

    }

    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, Object> params, final @NonNull Callback callBack) {

        NovateUtil.getInstance().post(url, transform(params), new BaseSubscriber<ResponseBody>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    callBack.onSuccess(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, final @NonNull DownloadCallback callback) {
        NovateUtil.getInstance().download(url, fileName,  new DownLoadCallBack() {

            @Override
            public void onStart(String s) {
                super.onStart(s);

                callback.onStart();

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onProgress(String key, int progress, long fileSizeDownloaded, long totalSize) {
                super.onProgress(key, progress, fileSizeDownloaded, totalSize);
                UpdateLog.e("下载进度"+progress+"：：："+totalSize);
                callback.onProgress(progress, totalSize);


            }

            @Override
            public void onSucess(String key, String path, String name, long fileSize) {
                LogUtils.e("下载完成"+path+"  name:  "+name);
                callback.onSuccess(new File(path+name));
            }

            @Override
            public void onCancel() {
                super.onCancel();
                UpdateLog.e("取消下载");

            }

        });
    }

    @Override
    public void cancelDownload(@NonNull String url) {
        UpdateLog.e("取消下载"+url);



    }

    private Map<String, Object> transform(Map<String, Object> params) {
        Map<String, Object> map = new TreeMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
        }
        return map;
    }


}