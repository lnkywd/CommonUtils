package common.utils.base.http;

import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import common.utils.utils.LogUtils;
import common.utils.utils.NetworkUtils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static okhttp3.internal.platform.Platform.INFO;

/**
 * Created by fzz on 2017/11/2 0002.
 */

public class HttpManage {
    //设缓存有效期为1天
    private static long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    private static HttpManage mServiceManage;
    private static boolean mIsDebug;
    /**
     * 这个拦截类是看别人写的，大家可以做个参考
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor sRewriteCacheControlInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);

            if (NetworkUtils.isConnected()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        //pragma也是控制缓存的一个消息头属性,要移除掉然后重新设置缓存策略
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };
    private Retrofit retrofit;
    private HashMap<String, String> mHeaders = new HashMap<>();

    private HttpManage(String baseUrl) {
        this(baseUrl, null);
    }

    private HttpManage(String baseUrl, Param param) {
        //缓存目录
        File files = new File(Environment.getDownloadCacheDirectory().toString(), "cache");
        //缓存大小为10M
        int cacheSize = 10 * 1024 * 1024;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                String msg = message;
                try {
                    if (message.startsWith("{") || message.startsWith("[")) {
                        JSONObject jsonObject = new JSONObject(message);
                        msg = jsonObject.toString(4);
                        LogUtils.json(msg);
                        msg = jsonObject.toString();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    msg = message;
                }
                Platform.get().log(INFO, msg, null);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Cache mCache = new Cache(files, cacheSize);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.cache(mCache);
        builder.addNetworkInterceptor(sRewriteCacheControlInterceptor);
        if (mIsDebug) {
            builder.addInterceptor(httpLoggingInterceptor);
        }
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder requestBuilder = request.newBuilder();
                if (mHeaders != null && mHeaders.size() > 0) {
                    for (Map.Entry<String, String> entry : mHeaders.entrySet()) {
                        requestBuilder.addHeader(entry.getKey(), entry.getValue());
                    }
                }
                return chain.proceed(requestBuilder.build());
            }
        });

        Converter.Factory factory = null;
        if (param != null) {
            factory = param.getConverterFactory();
        }

        if (param != null) {
            for (Interceptor interceptor : param.getInterceptors()) {
                builder.addInterceptor(interceptor);
            }
        }

        OkHttpClient client = builder.build();
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(factory == null ? GsonConverterFactory.create() : factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static HttpManage getInstance(String baseUrl, boolean isDebug) {
        mIsDebug = isDebug;
        if (mServiceManage == null) {
            mServiceManage = new HttpManage(baseUrl);
        }
        return mServiceManage;
    }

    public static HttpManage get() {
        return mServiceManage;
    }

    public HashMap<String, String> getHeaders() {
        return mHeaders;
    }

    public <T> T creat(Class<T> service) {
        return retrofit.create(service);
    }

    public static class Builder {

        private Param mParam = new Param();

        public Builder setConverterFactory(Converter.Factory factory) {
            mParam.setConverterFactory(factory);
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            mParam.getInterceptors().add(interceptor);
            return this;
        }

        public void create(String baseUrl, boolean isDebug) {
            mIsDebug = isDebug;
            mServiceManage = new HttpManage(baseUrl, mParam);
        }
    }

    private static class Param {

        private Converter.Factory mConverterFactory;
        private List<Interceptor> mInterceptors = new ArrayList<>();

        Converter.Factory getConverterFactory() {
            return mConverterFactory;
        }

        void setConverterFactory(Converter.Factory converterFactory) {
            mConverterFactory = converterFactory;
        }

        public List<Interceptor> getInterceptors() {
            if (mInterceptors == null) {
                return new ArrayList<>();
            }
            return mInterceptors;
        }
    }
}
