package com.etasdemir.ethinspector.di

import com.etasdemir.ethinspector.BuildConfig
import com.etasdemir.ethinspector.data.remote.apiKeyInterceptor
import com.etasdemir.ethinspector.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BlockchairRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EtherscanRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @EtherscanRetrofit
    @Singleton
    @Provides
    fun provideEtherscan(okHttpBuilder: OkHttpClient.Builder): Retrofit {
        okHttpBuilder
            .addInterceptor(
                apiKeyInterceptor(
                    Constants.ETHER_SCAN_API_KEY_QUERY_NAME,
                    BuildConfig.ETHER_SCAN_API_KEY
                )
            )
        return Retrofit
            .Builder()
            .baseUrl(Constants.ETHER_SCAN_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @BlockchairRetrofit
    @Singleton
    @Provides
    fun provideBlockchair(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(Constants.BLOCK_CHAIR_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    fun provideOkHttpClientBuilder(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder =
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .callTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        logging.redactHeader("Authorization");
        logging.redactHeader("Cookie");
        return logging
    }
}