package es.niux.efc.demoapp.common.injection.data;

import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.niux.efc.core.common.util.Check;
import es.niux.efc.demoapp.data.source.network.themoviedb.TheMovieDbNetworkDataSource;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataSourceModule {
    @Provides @Singleton @Named("theMovieDb") HttpUrl provideTheMovieDBBaseUrl() {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("api.themoviedb.org")
                .addPathSegment("3")
                .addPathSegment("")
                .build();
    }

    @Provides @Singleton @Named("theMovieDb") OkHttpClient provideTheMovieDBOkHttpClient(){
        return new OkHttpClient.Builder()
                .build();
    }

    @Provides @Singleton @Named("theMovieDb") Converter.Factory provideTheMovieDBJsonConverterFactory() {
        return GsonConverterFactory
                .create(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd")
                        .create()
                );
    }

    @Provides @Singleton TheMovieDbNetworkDataSource provideTheMovieDBApiDataSource(
            @NonNull @Named("theMovieDb") HttpUrl baseUrl,
            @NonNull @Named("theMovieDb") OkHttpClient okHttpClient,
            @NonNull @Named("theMovieDb") Converter.Factory jsonConverterFactory
    ) {
        return new Retrofit.Builder()
                .baseUrl(Check.nonNull(baseUrl))
                .client(Check.nonNull(okHttpClient))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(Check.nonNull(jsonConverterFactory))
                .build()
                .create(TheMovieDbNetworkDataSource.class);
    }
}
