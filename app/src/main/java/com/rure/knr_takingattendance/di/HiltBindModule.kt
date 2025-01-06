package com.rure.knr_takingattendance.di

import com.rure.knr_takingattendance.data.repository.MemberParticipationRepositoryImpl
import com.rure.knr_takingattendance.data.repository.MemberRepositoryImpl
import com.rure.knr_takingattendance.domain.repository.MemberParticipationRepository
import com.rure.knr_takingattendance.domain.repository.MemberRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class HiltBindModule {
    @Binds
    @ViewModelScoped
    abstract fun provideMemberRepository(impl: MemberRepositoryImpl): MemberRepository

    @Binds
    @ViewModelScoped
    abstract fun provideMemberParticipationRepository(impl: MemberParticipationRepositoryImpl): MemberParticipationRepository
}