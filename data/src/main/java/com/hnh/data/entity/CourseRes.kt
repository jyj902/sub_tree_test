package com.hnh.data.entity

import com.google.gson.annotations.SerializedName
import com.hnh.domain.entity.Course

/**
 * Created by hyerim on 9/15/21...
 */
data class CourseRes(
    @SerializedName("id")
    private val _id: Int,
    @SerializedName("title")
    private val _title: String,
    @SerializedName("description")
    private val _description: String,
    @SerializedName("difficulty")
    private val _difficulty: Int,
    @SerializedName("contentCount")
    private val _contentCount: Int,
    @SerializedName("calorie")
    private val _calorie: String,
    @SerializedName("ai")
    private val _isAi: Boolean,
    @SerializedName("thumbnail")
    private val _thumbnail: String,
    @SerializedName("createDate")
    private val _createdDate: String
) : Course {
    override val id: Int
        get() = _id
    override val title: String
        get() = _title
    override val description: String
        get() = _description
    override val difficulty: Int
        get() = _difficulty
    override val contentCount: Int
        get() = _contentCount
    override val calorie: String
        get() = _calorie
    override val isAi: Boolean
        get() = _isAi
    override val thumbnail: String
        get() = _thumbnail
    override val createdDate: String
        get() = _createdDate
}