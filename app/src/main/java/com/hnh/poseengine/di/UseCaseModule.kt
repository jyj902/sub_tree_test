package com.hnh.poseengine.di

import com.hnh.domain.repository.*
import com.hnh.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by hyerim on 9/16/21...
 */
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun providesGetCourseUseCase(repository: CourseRepository): GetCourseUseCase {
        return GetCourseUseCase(repository)
    }

    @Provides
    fun providesGetVideoContentsReposUseCase(repository: VideoContentsRepository): GetVideoContentsUseCase {
        return GetVideoContentsUseCase(repository)
    }

    @Provides
    fun providesGetVersionUseCase(repository: VersionRepository): GetVersionUseCase {
        return GetVersionUseCase(repository)
    }

    @Provides
    fun providesGetLoginUseCase(repository: UserRepository): GetUserUseCase {
        return GetUserUseCase(repository)
    }

    @Provides
    fun providesUploadCsvFileUseCase(repository: FileRepository): UploadCsvFileUseCase {
        return UploadCsvFileUseCase(repository)
    }

    @Provides
    fun providesGetExerciseBodyUseCase(repository: ExerciseBodyRepository): GetExerciseBodyUseCase {
        return GetExerciseBodyUseCase(repository)
    }

    @Provides
    fun providesGetRecordTotalUseCase(repository: PersonalRecordRepository) : GetRecordTotalUseCase {
        return GetRecordTotalUseCase(repository)
    }
}