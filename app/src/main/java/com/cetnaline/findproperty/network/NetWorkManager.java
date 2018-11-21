package com.cetnaline.findproperty.network;

import com.cetnaline.findproperty.BuildConfig;
import com.cetnaline.findproperty.FindPropertyApplication;
import com.cetnaline.findproperty.network.services.ApiRequestService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cetnaline.findproperty.network.NetWorkContents.CONNECT_TIMEOUT;
import static com.cetnaline.findproperty.network.NetWorkContents.READ_TIMEOUT;
import static com.cetnaline.findproperty.network.NetWorkContents.WRITE_TIMEOUT;

public class NetWorkManager {
    private static NetWorkManager mInstance;
    private OkHttpClient client;
    private static Retrofit retrofit;
    private static volatile ApiRequestService centanetRequest = null;

    private static File httpCacheDirectory = new File(FindPropertyApplication.getContext().getCacheDir(), "FindPropertyCache");
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);

    private NetWorkManager() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) //连接超时
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS) //读取超时
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS) //写超时
//                .addInterceptor(getHeaderInterceptor())
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(NetWorkContents.CENTANET_BASE_HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 重置host
     * @param host
     */
    public void resetHost(String host) {
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(host)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        centanetRequest = retrofit.create(ApiRequestService.class);
    }

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

    public ApiRequestService getCentalineRequest() {
        if (centanetRequest == null) {
            synchronized (Request.class) {
                centanetRequest = retrofit.create(ApiRequestService.class);
            }
        }
        return centanetRequest;
    }
}
