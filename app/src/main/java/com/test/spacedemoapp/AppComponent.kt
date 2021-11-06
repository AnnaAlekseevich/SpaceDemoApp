package com.test.spacedemoapp

import com.test.spacedemoapp.data.common.repositories.RemoteRoverPhotosDataStore
import com.test.spacedemoapp.domain.dagger.ApiModule
import com.test.spacedemoapp.domain.dagger.PhotosRepoModule
import com.test.spacedemoapp.ui.main.MainActivity
import com.test.spacedemoapp.ui.screen.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApiModule::class, PhotosRepoModule::class]
)
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: SplashActivity)
    fun inject(remoteRoverPhotosDataStore: RemoteRoverPhotosDataStore)

}