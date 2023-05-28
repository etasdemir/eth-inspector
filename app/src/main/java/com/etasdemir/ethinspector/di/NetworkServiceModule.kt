package com.etasdemir.ethinspector.di

import com.etasdemir.ethinspector.data.remote.dao.*
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

    @Singleton
    @Provides
    fun provideAddressDao(@EtherscanRetrofit retrofit: Retrofit): AddressDao =
        retrofit.create(AddressDao::class.java)

    @Singleton
    @Provides
    fun provideBlockDao(@EtherscanRetrofit retrofit: Retrofit): BlockDao =
        retrofit.create(BlockDao::class.java)

    @Singleton
    @Provides
    fun provideTransactionDao(@EtherscanRetrofit retrofit: Retrofit): TransactionDao =
        retrofit.create(TransactionDao::class.java)
}