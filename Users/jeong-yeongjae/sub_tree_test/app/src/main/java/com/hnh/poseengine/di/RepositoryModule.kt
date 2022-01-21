package com.hnh.poseengine.di

import com.hnh.data.repositoryimpl.*
import com.hnh.domain.repository.*
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
object RepositoryModule {

    @Singleton
    @Provides
    fun providesCourseRepository(repository: CourseRepositoryImpl): CourseRepository {
        return repository
    }

    @Singleton
    @Provides
    fun providesVideoContentsRepository(repository: VideoContentRepositoryImpl): VideoContentsRepository {
        return repository
    }

    @Singleton
    @Provides
    fun providesVersionRepository(repository: VersionRepositoryImpl): VersionRepository {
        return repository
    }

    @Singleton
    @Provides
    fun providesLoginRepository(repository: UserRepositoryImpl): UserRepository {
        return repository
    }

    @Singleton
    @Provides
    fun providesFileRepository(repository: FileRepositoryImpl): FileRepository {
        return repository
    }

    @Singleton
    @Provides
    fun providesExerciseBodyRepository(repository: ExerciseBodyRepositoryImpl): ExerciseBodyRepository {
        return repository
    }

    @Singleton
    @Provides
    fun providesPersonalRecordRepository(repository: PersonalRecordRepositoryImpl) : PersonalRecordRepository {
        return repository
    }
}