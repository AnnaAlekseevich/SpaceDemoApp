package com.test.spacedemoapp.domain.dagger

import com.test.spacedemoapp.SpaceDemoApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Observable
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
open class AppModule {

    @Provides
    @Singleton
    fun providesInternetStateObservable(): Observable<Boolean> {
        return SpaceDemoApp.internetStateObservable
    }

}