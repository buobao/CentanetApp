package com.cetnaline.findproperty.network;

import com.cetnaline.findproperty.network.services.ApiRequestService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkManager {
    private static NetWorkManager mInstance;
    private static Retrofit retrofit;
    private static volatile ApiRequestService centanetRequest = null;

    public static NetWorkManager getInstance() {
        if (mInstance == null) {
            synchronized (NetWorkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkManager();
                }
            }
        }
        return mInstance;
    }

    public void init() {
        OkHttpClient client = new OkHttpClient.Builder().build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(NetWorkContents.CENTANET_BASE_HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiRequestService getCentalineRequest() {
        if (centanetRequest == null) {
            synchronized (Request.class) {
                centanetRequest = retrofit.create(ApiRequestService.class);
            }
        }
        return centanetRequest;
    }
}
