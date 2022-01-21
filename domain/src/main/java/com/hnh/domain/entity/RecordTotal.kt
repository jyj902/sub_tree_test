package com.hnh.domain.entity

interface RecordTotal {
    val id: Long //uid
    val totalSuccessCount : Int
    val totalFailCount : Int
    val totalRunningTime : Long // 1초 단위
    val totalRestTime : Long //1초 단위
}