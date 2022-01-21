package com.hnh.data.entity

import com.google.gson.annotations.SerializedName
import com.hnh.domain.entity.RecordTotal

class RecordTotalRes(
    @SerializedName("useId")
    private val _id: Long,
    @SerializedName("totalSuccessCount")
    private val _totalSuccessCount: Int,
    @SerializedName("totalFailCount")
    private val _totalFailCount : Int,
    @SerializedName("totalRunningTime")
    private val _totalRunningTime : Long,
    @SerializedName("totalRestTime")
    private val _totalRestTime : Long
) : RecordTotal {
    override val id: Long
        get() = _id
    override val totalSuccessCount: Int
        get() = _totalSuccessCount
    override val totalFailCount: Int
        get() = _totalFailCount
    override val totalRunningTime: Long
        get() = _totalRunningTime
    override val totalRestTime: Long
        get() = _totalRestTime
}