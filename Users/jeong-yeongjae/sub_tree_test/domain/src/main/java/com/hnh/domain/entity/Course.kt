package com.hnh.domain.entity

import android.os.Parcelable
/**
 * Created by hyerim on 9/15/21...
 */

interface Course {
    val id: Int //uid
    val title: String //코스명
    val description: String //설명
//    val trainerId: String //트레이너 id
    val difficulty: Int //난이도
    val contentCount: Int //영상수
    val calorie: String //소모 칼로리
    val isAi: Boolean // ai여부
    val thumbnail: String //코스 썸네일
    val createdDate: String //생성날짜
}