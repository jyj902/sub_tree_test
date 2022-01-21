package com.hnh.data.entity

import com.google.gson.annotations.SerializedName
import com.hnh.domain.entity.Trainer

/**
 * Created by hyerim on 2021/09/26...
 */
class TrainerRes(
    @SerializedName("id")
    val _id: Int,
    @SerializedName("name")
    val _name: String,
    @SerializedName("description")
    val _description: String,
    @SerializedName("userId")
    val _userId: String,
    @SerializedName("contactNumber")
    val _contactNumber: String,
    @SerializedName("exerciseContentList")
    val _exerciseContentList: ArrayList<Int>,
    @SerializedName("isActive")
    val _isActive: Boolean,
    @SerializedName("createdDate")
    val _createdDate: String,
    @SerializedName("modifiedDate")
    val _modifiedDate: String,
) : Trainer {
    override val id: Int
        get() = _id
    override val name: String
        get() = _name
    override val description: String
        get() = _description
    override val userId: String
        get() = _userId
    override val contactNumber: String
        get() = _contactNumber
    override val exerciseContentList: ArrayList<Int>
        get() = _exerciseContentList
    override val isActive: Boolean
        get() = _isActive
    override val createdDate: String
        get() = _createdDate
    override val modifiedDate: String
        get() = _modifiedDate
}