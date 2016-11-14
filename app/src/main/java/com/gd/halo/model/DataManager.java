package com.gd.halo.model;

import com.gd.halo.api.CarrierApi;
import com.gd.halo.api.JztkApi;
import com.gd.halo.bean.Carrier;
import com.gd.halo.bean.Jztk;
import com.gd.halo.bean.data.CarrierData;
import com.gd.halo.bean.data.JztkData;
import com.lzy.okhttputils.interceptor.LoggerInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zhouxin on 2016/11/7.
 * Description: Data Layer持有DataManager和一系列的Helper classe
 PreferencesHelper：从SharedPreferences读取和存储数据。
 DatabaseHelper：处理操作SQLite数据库。
 Retrofit services：执行访问REST API，我们现在使用Retrofit来代替Volley，因为它天生支持RxJava。而且也更好用。
 */
public class DataManager {
    private static final String BASE_URL_Carrier = "http://v.juhe.cn/expressonline/";
    private static final String BASE_URL_Jztk = "http://api2.juheapi.com/";
    private static volatile DataManager sInstance;
    private DatabaseHelper mDatabaseHelper;
    private CarrierApi mCarrierService;
    private final JztkApi mJztkService;

    public static DataManager getInstance() {
        synchronized (DataManager.class) {
            if(sInstance == null) {
                synchronized (DataManager.class) {
                    sInstance = new DataManager();
                }
            }
            return sInstance;
        }
    }

    private DataManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new LoggerInterceptor(null, true));
        OkHttpClient httpClient = builder.build();
        Retrofit.Builder builderRetrofit = new Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        mCarrierService = builderRetrofit.baseUrl(BASE_URL_Carrier).build().create(CarrierApi.class);
        mJztkService = builderRetrofit.baseUrl(BASE_URL_Jztk).build().create(JztkApi.class);
    }

    public Observable<List<Carrier>> getCarriers() {
        return mCarrierService.loadCarriers().map(new Func1<CarrierData, List<Carrier>>() {
            @Override
            public List<Carrier> call(CarrierData carrierData) {
                return carrierData.result;
            }
        });
    }

    public Observable<List<Jztk>> getAllJztk(String subject, String model) {
        return mJztkService.query(subject, model, JztkData.TESTTYPE_ORDER).map(new Func1<JztkData, List<Jztk>>() {
            @Override
            public List<Jztk> call(JztkData jztkData) {
                return jztkData.result;
            }
        }).concatMap(new Func1<List<Jztk>, Observable<? extends List<Jztk>>>() {
            @Override
            public Observable<? extends List<Jztk>> call(List<Jztk> jztks) {
                return mDatabaseHelper.saveJztks(jztks);
            }
        });
    }

    public Observable<List<Jztk>> getRandJztk(String subject, String model) {
        return mJztkService.query(subject, model, JztkData.TESTTYPE_RAND).map(new Func1<JztkData, List<Jztk>>() {
            @Override
            public List<Jztk> call(JztkData jztkData) {
                return jztkData.result;
            }
        });
    }

    /**
     *调用Retrofit service从REST API加载一个博客文章列表
     使用DatabaseHelper保存文章到本地数据库，达到缓存的目的
     筛选出今天发表的博客，因为那才是View Layer想要展示的。
     * @return Observable 被观察者
     */
    public Observable loadTodayPosts() {
        /*return mRetrofitService.loadPosts()
                .concatMap(new Func1<List, Observable>() {
                    @Override
                    public Observable call(List apiPosts) {
                        return mDatabaseHelper.savePosts(apiPosts);
                    }
                })
                .filter(new Func1<Post, Boolean>() {
                    @Override
                    public Boolean call(Post post) {
                        return isToday(post.date);
                    }
                });*/
        return null;
    }
}
