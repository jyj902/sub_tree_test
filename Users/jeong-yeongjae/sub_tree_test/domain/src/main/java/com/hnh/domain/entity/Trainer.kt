package com.hnh.domain.entity

/**
 * Created by hyerim on 2021/09/26...
 */
interface Trainer {
    val id: Int
    val name: String
    val description: String
    val userId: String
    val contactNumber: String
    val exerciseContentList: ArrayList<Int>
    val isActive: Boolean
    val createdDate: String
    val modifiedDate: String
}