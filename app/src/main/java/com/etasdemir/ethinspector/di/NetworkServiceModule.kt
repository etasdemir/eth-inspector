package com.etasdemir.ethinspector.di

import com.etasdemir.ethinspector.data.remote.dao.EthStatsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkServiceModule {

    @Singleton
    @Provides
    fun provideEthStatsDao(@BlockchairRetrofit retrofit: Retrofit): EthStatsDao =
        retrofit.create(EthStatsDao::class.java)
}