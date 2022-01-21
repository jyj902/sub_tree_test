package com.hnh.data.entity

import com.google.gson.annotations.SerializedName
import com.hnh.domain.entity.Version

/**
 * Created by hyerim on 2021/10/08...
 */
data class VersionRes(
    @SerializedName("id")
    val _id: Int,
    @SerializedName("version")
    val _version: String,
    @SerializedName("date")
    val _date: String,
    @SerializedName("required")
    val _isRequired: Boolean
) : Version {
    override val id: Int
        get() = _id
    override val version: String
        get() = _version
    override val date: String
        get() = _date
    override val isRequired: Boolean
        get() = _isRequired
}