package com.hnh.domain.entity

interface Exercise {
    val id: Long //uid
    val title: String //운동컨텐츠명
    val description: String //운동컨텐츠 설명
    val exerciseName: String //운동명
    val difficulty: Int //난이도
    val contentsCount: Int //운동 영상 수
    val calorie: Long //소모 칼로리
    val thumbnailFileName: String //썸네일 파일 이름
    val thumbnailPath: String //썸네일 주소
    val videoFileName: String // 영상명
    val videoPath: String // 영상주소
    val isAi: Boolean //AI포함여부
//    val isVisible: Boolean //사용자 이용 가능여부
    val createdDate: String //생성날짜
    val modifiedDate: String //수정날짜
//    val trainer: Trainer //트레이너정보
}