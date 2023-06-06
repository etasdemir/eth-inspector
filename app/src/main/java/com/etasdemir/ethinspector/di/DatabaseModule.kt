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
    fun provideAddressDaoLocal(db: LocalDatabase): AddressDaoLocal = db.addressDaoLocal()

    @Provides
    fun provideBlockDaoLocal(db: LocalDatabase): BlockDaoLocal = db.blockDaoLocal()

    @Provides
    fun provideEthStatsDaoLocal(db: LocalDatabase): EthStatsDaoLocal = db.ethStatsDaoLocal()

    @Provides
    fun provideTransactionDaoLocal(db: LocalDatabase): TransactionDaoLocal = db.transactionDaoLocal()

    @Provides
    fun provideUserDaoLocal(db: LocalDatabase): UserDaoLocal = db.userDaoLocal()
}