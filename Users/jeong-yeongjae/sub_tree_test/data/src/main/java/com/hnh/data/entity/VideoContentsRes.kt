package com.hnh.data.entity

import com.google.gson.annotations.SerializedName
import com.hnh.domain.entity.VideoContents

/**
 * Created by hyerim on 2021/09/26...
 */
class VideoContentsRes(
    @SerializedName("id")
    private val _id: Int,
    @SerializedName("title")
    private val _title: String,
    @SerializedName("description")
    private val _description: String,
//    @SerializedName("trainer")
//    private val _trainer: Trainer,
    @SerializedName("difficulty")
    private val _difficulty: Int,
    @SerializedName("path")
    private val _path: String,
    @SerializedName("contentCount")
    private val _contentCount: Int,
    @SerializedName("calorie")
    private val _calorie: String,
    @SerializedName("ai")
    private val _isAi: Boolean,
    @SerializedName("thumbnail")
    private val _thumbnail: String
) : VideoContents {
    override val id: Int
        get() = _id
    override val title: String
        get() = _title
    override val description: String
        get() = _description

    //    override val trainerId: Trainer
//        get() = _trainer
    override val difficulty: Int
        get() = _difficulty
    override val path: String
        get() = _path
    override val calorie: String
        get() = _calorie
    override val isAi: Boolean
        get() = _isAi
    override val thumbnail: String
        get() = _thumbnail

}