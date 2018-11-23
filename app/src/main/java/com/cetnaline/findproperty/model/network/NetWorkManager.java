package com.cetnaline.findproperty.model.network;

import com.cetnaline.findproperty.BuildConfig;
import com.cetnaline.findproperty.FindPropertyApplication;
import com.cetnaline.findproperty.model.network.services.ApiRequestService;
import com.cetnaline.findproperty.utils.ApplicationUtil;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cetnaline.findproperty.model.network.NetWorkContents.CONNECT_TIMEOUT;
import static com.cetnaline.findproperty.model.network.NetWorkContents.READ_TIMEOUT;
import static com.cetnaline.findproperty.model.network.NetWorkContents.WRITE_TIMEOUT;

public class NetWorkManager {

    private static NetWorkManager mInstance;
//    private OkHttpClient client;
    private static Retrofit retrofitNoCache;
    private static Retrofit retrofitCache;
    private static volatile ApiRequestService centanetCacheRequest = null;
    private static volatile ApiRequestService centanetNoCacheRequest = null;

    private static File httpCacheDirectory = new File(FindPropertyApplication.getContext().getCacheDir(), "FindPropertyCache");
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);

    private NetWorkManager() {
        retrofitNoCache = new Retrofit.Builder()
                .client(getClient(null, false))
                .baseUrl(NetWorkContents.CENTANET_BASE_HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitCache = new Retrofit.Builder()
                .client(getClient(null, true))
                .baseUrl(NetWorkContents.CENTANET_BASE_HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient getClient(Map<String, String> header, boolean isCache){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(true)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) //连接超时
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS) //读取超时
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS) //写超时
//                .addInterceptor(getHeaderInterceptor())
//                .cache(cache)
                .addInterceptor(httpLoggingInterceptor);

        //添加缓存
        if (isCache) {
            builder.addNetworkInterceptor(chain ->{
                Request request = chain.request();
                if (!ApplicationUtil.checkNetwork(FindPropertyApplication.getContext())) {
                            request = request.newBuilder()
                                    .cacheControl(CacheControl.FORCE_CACHE)
                                    .build();
                }

                Response response = chain.proceed(request);
                if (ApplicationUtil.checkNetwork(FindPropertyApplication.getContext())) {
                    int maxAge = 0 * 60; // 有网络时 设置缓存超时时间为0;

                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24; // 无网络时，设置超时为1天
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }).cache(cache);
        }

        //添加统一请求头 也可以在请求接口中添加
        if (header != null && header.size() > 0) {
            builder.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder requestBuild = original.newBuilder();
                for (String s : header.keySet()) {
                    requestBuild.addHeader(s, header.get(s));
                }
                return chain.proceed(requestBuild.build());
            });
        }

         return builder.build();
    }

    /**
     * 重置host
     * @param host
     */
    public void resetHost(String host) {
        retrofitNoCache = new Retrofit.Builder()
                .client(getClient(null, false))
                .baseUrl(host)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitCache = new Retrofit.Builder()
                .client(getClient(null, true))
                .baseUrl(host)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        centanetCacheRequest = null;
        centanetNoCacheRequest = null;
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

    public ApiRequestService getCacheCentalineRequest() {
        if (centanetCacheRequest == null) {
            centanetCacheRequest = retrofitCache.create(ApiRequestService.class);
        }
        return centanetCacheRequest;
    }

    public ApiRequestService getNoCacheCentalineRequest() {
        if (centanetNoCacheRequest == null) {
            centanetNoCacheRequest = retrofitNoCache.create(ApiRequestService.class);
        }
        return centanetNoCacheRequest;
    }
}
