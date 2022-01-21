package com.hnh.data.entity;

import com.google.gson.annotations.SerializedName;
import com.hnh.domain.entity.DefaultResponse

/**
 * Created by hyerim on 2021/11/23...
 */
data class DefaultResponseRes(
    @SerializedName("status")
    val _statusCode: String
) : DefaultResponse {
    override val statusCode: String
        get() = _statusCode
}
