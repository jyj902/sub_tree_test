package com.hnh.data.entity

import com.google.gson.annotations.SerializedName
import com.hnh.domain.entity.ExerciseBody

data class ExerciseBodyRes(
    @SerializedName("id")
    private val _id: Long,
    @SerializedName("bodyPartName")
    private val _bodyPartName: String,
    @SerializedName("description")
    private val _description: String,
    @SerializedName("difficulty")
    private val _difficulty: Int,
    @SerializedName("contentsCount")
    private val _contentsCount: Int,
    @SerializedName("totalCalorie")
    private val _totalCalorie: Long,
    @SerializedName("thumbnailFileName")
    private val _thumbnailFileName: String,
    @SerializedName("thumbnailPath")
    private val _thumbnailPath: String,
    @SerializedName("isAi")
    private val _isAi: Boolean,
    @SerializedName("isVisible")
    private val _isVisible: Boolean,
    @SerializedName("createdDate")
    private val _createdDate: String,
    @SerializedName("modifiedDate")
    private val _modifiedDate: String
) : ExerciseBody {
    override val id: Long
        get() = _id
    override val bodyPartName: String
        get() = _bodyPartName
    override val description: String
        get() = _description
    override val difficulty: Int
        get() = _difficulty
    override val contentsCount: Int
        get() = _contentsCount
    override val totalCalorie: Long
        get() = _totalCalorie
    override val thumbnailFileName: String
        get() = _thumbnailFileName
    override val thumbnailPath: String
        get() = _thumbnailPath
    override val isAi: Boolean
        get() = _isAi
    override val isVisible: Boolean
        get() = _isVisible
    override val createdDate: String
        get() = _createdDate
    override val modifiedDate: String
        get() = _modifiedDate
}