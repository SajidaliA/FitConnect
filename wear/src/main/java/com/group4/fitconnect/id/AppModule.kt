package com.group4.fitconnect.id

import android.content.Context
import com.group4.fitconnect.health.HealthServiceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHealthManager(@ApplicationContext context: Context): HealthServiceManager =
        HealthServiceManager(context)
}