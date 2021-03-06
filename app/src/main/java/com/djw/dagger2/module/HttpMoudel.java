package com.djw.dagger2.module;

import com.djw.dagger2.base.BaseApplication;
import com.djw.dagger2.http.DoubanUrl;
import com.djw.dagger2.http.GankUrl;
import com.djw.dagger2.http.WxUrl;
import com.djw.dagger2.http.ZhihuUrl;
import com.djw.dagger2.http.apis.GankApi;
import com.djw.dagger2.http.apis.WxApi;
import com.djw.dagger2.http.apis.ZhihuApi;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JasonDong on 2017/3/23.
 */
@Module
public class HttpMoudel {

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }


    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    @ZhihuUrl
    Retrofit provideZhihuRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ZhihuApi.SERVICE);
    }

    @Singleton
    @Provides
    @GankUrl
    Retrofit provideGankRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, GankApi.SERVICE);
    }

    @Singleton
    @Provides
    @WxUrl
    Retrofit provideWxRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, WxApi.SERVICE);
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder, Cache cache, Interceptor interceptor) {

        return builder.cache(cache).addNetworkInterceptor(interceptor).build();
    }

    @Singleton
    @Provides
    Cache provideCache() {
        return new Cache(new File(BaseApplication.getInstance().getApplicationContext().getCacheDir(), "dagger2"), 1024 * 1024 * 20);
    }

    @Singleton
    @Provides
    Interceptor provideInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "max-age=" + 60 * 60)
                        .build();
            }
        };
    }

    @Singleton
    @Provides
    ZhihuApi provideZhihuService(@ZhihuUrl Retrofit retrofit) {
        return retrofit.create(ZhihuApi.class);
    }


    @Singleton
    @Provides
    GankApi provideGankService(@GankUrl Retrofit retrofit) {
        return retrofit.create(GankApi.class);
    }

    @Singleton
    @Provides
    WxApi provideWxService(@WxUrl Retrofit retrofit) {
        return retrofit.create(WxApi.class);
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {


        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
