package com.hnh.domain.entity

/**
 * Created by hyerim on 2021/09/26...
 */
interface VideoContents {
    val id: Int //uid
    val title: String //코스명
    val description: String //설명
    //    val trainerId: Trainer //트레이너 id
    val difficulty: Int //난이도
    val path: String //경로
    val calorie: String //소모 칼로리
    val isAi: Boolean // ai 여부
    val thumbnail: String //코스 썸네일
}