package com.codewithmohsen.remote.di


import com.codewithmohsen.remote.BuildConfig
import com.codewithmohsen.remote.api.ApiService
import com.codewithmohsen.remote.api.ClientInterceptor
import com.codewithmohsen.remote.api.Constants
import com.codewithmohsen.remote.api.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun providesOKHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(ClientInterceptor())
            .retryOnConnectionFailure(true)
            .readTimeout(Constants.READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(Constants.CONNECT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun providesAppStartApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun providesApiRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
    }

    @Provides
    fun providesMoshiBuilder(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
}