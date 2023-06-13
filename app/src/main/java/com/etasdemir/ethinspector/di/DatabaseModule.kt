package com.etasdemir.ethinspector.di

import android.content.Context
import androidx.room.Room
import com.etasdemir.ethinspector.data.local.LocalDatabase
import com.etasdemir.ethinspector.data.local.dao.*
import com.etasdemir.ethinspector.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun getLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room
            .databaseBuilder(context, LocalDatabase::class.java, Constants.LOCAL_DB_NAME)
            .build()
    }

    @Provides
    fun provideAddressDao(db: LocalDatabase) = db.addressDao()

    @Provides
    fun provideBlockDao(db: LocalDatabase): BlockDao = db.blockDao()

    @Provides
    fun provideEthStatsDao(db: LocalDatabase): EthStatsDao = db.ethStatsDao()

    @Provides
    fun provideTransactionDao(db: LocalDatabase): TransactionDao = db.transactionDao()

    @Provides
    fun provideUserDao(db: LocalDatabase): UserDao = db.userDao()

    @Provides
    fun provideTokenTransferDao(db: LocalDatabase): TokenDao = db.tokenTransferDao()

}