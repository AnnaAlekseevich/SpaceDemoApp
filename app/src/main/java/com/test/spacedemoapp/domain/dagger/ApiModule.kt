package com.test.spacedemoapp.domain.dagger

import com.google.gson.GsonBuilder
import com.test.spacedemoapp.domain.net.ApiRoverPhotos
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object ApiModule {

    @JvmStatic
    @Singleton
    @Provides
    fun getApiService(): Retrofit {

        var builder = GsonBuilder()
        var httpClient = OkHttpClient.Builder()
        var gson = builder.create()
        val logging = HttpLoggingInterceptor();

        return Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/mars-photos/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(
                httpClient.addInterceptor(
                    logging.setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
                )
                    .build()
            )
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideApiRoverPhotos(retrofit: Retrofit): ApiRoverPhotos =
        retrofit.create(ApiRoverPhotos::class.java)

}