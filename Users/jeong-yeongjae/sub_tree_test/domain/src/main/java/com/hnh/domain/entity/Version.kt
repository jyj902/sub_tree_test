package com.hnh.domain.entity

/**
 * Created by hyerim on 2021/10/08...
 */
interface Version {
    val id: Int //uid
    val version: String //version name
    val date: String //날짜-시간
    val isRequired: Boolean //필수여부
}