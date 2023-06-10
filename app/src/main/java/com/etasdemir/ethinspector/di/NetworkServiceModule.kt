package com.etasdemir.ethinspector.di

import com.etasdemir.ethinspector.data.remote.service.*
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
    fun provideEthStatsService(@BlockchairRetrofit retrofit: Retrofit): EthStatsService =
        retrofit.create(EthStatsService::class.java)

    @Singleton
    @Provides
    fun provideEtherscanAddressService(@EtherscanRetrofit retrofit: Retrofit): EtherscanAddressService =
        retrofit.create(EtherscanAddressService::class.java)

    @Singleton
    @Provides
    fun provideBlockchairAddressService(@BlockchairRetrofit retrofit: Retrofit): BlockchairAddressService =
        retrofit.create(BlockchairAddressService::class.java)

    @Singleton
    @Provides
    fun provideBlockService(@EtherscanRetrofit retrofit: Retrofit): BlockService =
        retrofit.create(BlockService::class.java)

    @Singleton
    @Provides
    fun provideTransactionService(@EtherscanRetrofit retrofit: Retrofit): TransactionService =
        retrofit.create(TransactionService::class.java)
}