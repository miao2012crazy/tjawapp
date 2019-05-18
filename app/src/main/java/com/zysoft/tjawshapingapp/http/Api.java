package com.zysoft.tjawshapingapp.http;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by mr.miao on 2019/3/27.
 */

public interface Api {

    @POST()
    Call<ResponseBody> uploadPic(
            @Url() String url,
            @Body RequestBody Body);
}
