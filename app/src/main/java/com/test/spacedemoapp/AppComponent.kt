package com.test.spacedemoapp

import com.test.spacedemoapp.domain.dagger.ApiModule
import com.test.spacedemoapp.domain.dagger.AppModule
import com.test.spacedemoapp.domain.dagger.DataBaseModule
import com.test.spacedemoapp.domain.dagger.RemoteRoverPhotoSourceModule
import com.test.spacedemoapp.ui.main.MainActivity
import com.test.spacedemoapp.ui.screen.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, ApiModule::class, RemoteRoverPhotoSourceModule::class, DataBaseModule::class]
//    PhotosRepoModule::class]
)
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: SplashActivity)
}