package com.rure.knr_takingattendance.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rure.knr_takingattendance.data.data_source.AppRoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)   // DB를 만드는 작업은 많은 자원을 필요로 하기 때문.
class HiltProvideModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppRoomDataBase =
        Room.databaseBuilder(
            context,
            AppRoomDataBase::class.java,
            "app_db"
        ).build()


    @Provides
    fun provideMemberDao(appRoomDatabase: AppRoomDataBase) = appRoomDatabase.memberDao
    @Provides
    fun provideMemberParticipationDao(appRoomDatabase: AppRoomDataBase) = appRoomDatabase.memberParticipationDao
}