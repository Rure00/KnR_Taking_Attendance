package com.rure.knr_takingattendance.di

import com.rure.knr_takingattendance.data.data_source.AppRoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelScopeProvideModule {
//    @Provides
//    @ViewModelScoped
//    fun provideMemberDao(appRoomDatabase: AppRoomDataBase) = appRoomDatabase.memberDao
//    @Provides
//    @ViewModelScoped
//    fun provideMemberParticipationDao(appRoomDatabase: AppRoomDataBase) = appRoomDatabase.memberParticipationDao
}