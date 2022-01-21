package com.hnh.data.entity

import com.google.gson.annotations.SerializedName
import com.hnh.domain.entity.User

/**
 * Created by hyerim on 2021/10/25...
 */
class UserRes (
    @SerializedName("id")
    private val _id: Long,
    @SerializedName("name")
    private val _name: String,
    @SerializedName("email")
    private val _email: String,
    @SerializedName("token")
    private val _token: String,
    @SerializedName("role")
    private val _role: String,
    @SerializedName("registrationId")
    private val _socialType: String,
    @SerializedName("activate")
    private val _isActivate: Boolean,
    @SerializedName("createDate")
    private val _createDate: String,
): User {
    override val id: Long
        get() = _id
    override val name: String
        get() = _name
    override val email: String
        get() = _email
    override val token: String
        get() = _token
    override val role: String
        get() = _role
    override val socialType: String
        get() = _socialType
    override val isActivated: Boolean
        get() = _isActivate
    override val createDate: String
        get() = _createDate
}