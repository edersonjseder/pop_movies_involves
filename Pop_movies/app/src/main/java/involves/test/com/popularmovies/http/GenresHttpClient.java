package involves.test.com.popularmovies.http;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.io.File;

import involves.test.com.popularmovies.interceptor.CacheInterceptor;
import involves.test.com.popularmovies.services.MoviesService;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static involves.test.com.popularmovies.constants.UrlConstants.URL_BASE;

/**
 * Class that creates the retrofit connection to the API for the genre list on the main Activity
 */
public class GenresHttpClient {

    // URL where JSON file is, saved in gradle.properties
    private static final String PATH = URL_BASE;

    public static MoviesService create(Context context) {

        // Place for offline cache
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder().cache(cache)
        .addInterceptor(logging)
        .addInterceptor(CacheInterceptor.provideCacheInterceptor())
        .addInterceptor(CacheInterceptor.provideOfflineCacheInterceptor(context)).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(PATH)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(httpClient).build();

        return retrofit.create(MoviesService.class);

    }

}
