package com.hnh.data.entity

import com.google.gson.annotations.SerializedName
import com.hnh.domain.entity.Exercise
import com.hnh.domain.entity.Trainer

data class ExerciseRes (
    @SerializedName("id")
    private val _id: Long,
    @SerializedName("title")
    private val _title: String,
    @SerializedName("description")
    private val _description: String,
    @SerializedName("exerciseName")
    private val _exerciseName: String,
    @SerializedName("difficulty")
    private val _difficulty: Int,
    @SerializedName("contentsCount")
    private val _contentsCount: Int,
    @SerializedName("calorie")
    private val _calorie: Long,
    @SerializedName("thumbnailFileName")
    private val _thumbnailFileName: String,
    @SerializedName("thumbnailPath")
    private val _thumbnailPath: String,
    @SerializedName("videoFileName")
    private val _videoFileName: String,
    @SerializedName("videoPath")
    private val _videoPath: String,
    @SerializedName("isAi")
    private val _isAi: Boolean,
    @SerializedName("createdDate")
    private val _createdDate: String,
    @SerializedName("modifiedDate")
    private val _modifiedDate: String
    //TODO: 트레이너 필요시 구현 필요
//    @SerializedName("trainer")
//    private val _trainer: Trainer
): Exercise {
    override val id: Long
        get() = _id
    override val title: String
        get() = _title
    override val description: String
        get() = _description
    override val exerciseName: String
        get() = _exerciseName
    override val difficulty: Int
        get() = _difficulty
    override val contentsCount: Int
        get() = _contentsCount
    override val calorie: Long
        get() = _calorie
    override val thumbnailFileName: String
        get() = _thumbnailFileName
    override val thumbnailPath: String
        get() = _thumbnailPath
    override val videoFileName: String
        get() = _videoFileName
    override val videoPath: String
        get() = _videoPath
    override val isAi: Boolean
        get() = _isAi
    override val createdDate: String
        get() = _createdDate
    override val modifiedDate: String
        get() = _modifiedDate
//    override val trainer: Trainer
//        get() = _trainer
}