package com.hnh.poseengine.di

import android.app.Application
import com.hnh.data.BASE_URL
import com.hnh.data.KAKAO_BASE_URL
import com.hnh.data.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Created by hyerim on 9/16/21...
 */
@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {
    private const val CONNECT_TIMEOUT = 10L
    private const val WRITE_TIMEOUT = 15L
    private const val READ_TIMEOUT = 30L

    @Provides
    @Singleton
    fun provideCache(application: Application): Cache {
        return Cache(application.cacheDir, 10L * 2014 * 2024)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder().apply {
            cache(cache)
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

    @Provides
    @Singleton
    @KakaoAuthInterceptorOkHttpClient
    fun provideKakaoRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(KAKAO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @DefaultInterceptorOkHttpClient
    fun provideDefaultRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideDeliveryCourseService(@DefaultInterceptorOkHttpClient retrofit: Retrofit): CourseService {
        return retrofit.create(CourseService::class.java)
    }

    @Provides
    @Singleton
    fun provideDeliveryVideoContentService(@DefaultInterceptorOkHttpClient retrofit: Retrofit): VideoContentService {
        return retrofit.create(VideoContentService::class.java)
    }

    @Provides
    @Singleton
    fun provideDeliveryVersionService(@DefaultInterceptorOkHttpClient retrofit: Retrofit): VersionService {
        return retrofit.create(VersionService::class.java)
    }

    @Provides
    @Singleton
    fun provideDeliveryLoginService(@DefaultInterceptorOkHttpClient retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideDeliveryFileService(@DefaultInterceptorOkHttpClient retrofit: Retrofit): FileService {
        return retrofit.create(FileService::class.java)
    }

    @Provides
    @Singleton
    fun provideDeliveryExerciseBodyService(@DefaultInterceptorOkHttpClient retrofit: Retrofit): ExerciseBodyService {
        return retrofit.create(ExerciseBodyService::class.java)
    }

    @Provides
    @Singleton
    fun provideDeliveryPersonalRecordService(@DefaultInterceptorOkHttpClient retrofit: Retrofit): PersonalRecordService {
        return retrofit.create(PersonalRecordService::class.java)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KakaoAuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GoogleAuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NaverAuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultInterceptorOkHttpClient