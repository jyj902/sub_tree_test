package com.hnh.poseengine.di

import com.hnh.data.source.*
import com.hnh.data.source.local.UserLocalSource
import com.hnh.data.source.local.UserLocalSourceImpl
import com.hnh.data.source.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by hyerim on 9/16/21...
 */
@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    //local
    @Singleton
    @Provides
    fun providesUserLocalSource(source: UserLocalSourceImpl): UserLocalSource {
        return source
    }

    //remote
    @Singleton
    @Provides
    fun providesCourseRemoteSource(source: CourseRemoteSourceImpl): CourseRemoteSource {
        return source
    }

    @Singleton
    @Provides
    fun providesVideoContentRemoteSource(source: VideoContentRemoteSourceImpl): VideoContentRemoteSource {
        return source
    }

    @Singleton
    @Provides
    fun providesVersionRemoteSource(source: VersionRemoteSourceImpl): VersionRemoteSource {
        return source
    }

    @Singleton
    @Provides
    fun providesUserRemoteSource(source: UserRemoteSourceImpl): UserRemoteSource {
        return source
    }

    @Singleton
    @Provides
    fun providesFileRemoteSource(source: FileRemoteSourceImpl): FileRemoteSource {
        return source
    }

    @Singleton
    @Provides
    fun providesExerciseBodyRemoteSource(source: ExerciseBodyRemoteSourceImpl): ExerciseBodyRemoteSource {
        return source
    }

    @Singleton
    @Provides
    fun providesPersonalRecordRemoteSource(source: PersonalRecordRemoteSourceImpl) : PersonalRecordRemoteSource {
        return source
    }
}