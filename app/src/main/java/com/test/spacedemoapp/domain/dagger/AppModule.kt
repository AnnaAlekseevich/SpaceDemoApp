package com.test.spacedemoapp.domain.dagger

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import javax.inject.Singleton

@Module
class AppModule(
    private val application: Application,
    private val internetStateObservable: Observable<Boolean>
) {
    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    fun providesApplicationContext(): Context = application

    @Provides
    @Singleton
    fun providesInternetStateObservable(): Observable<Boolean> = internetStateObservable


}