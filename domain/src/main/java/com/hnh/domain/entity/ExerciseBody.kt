package com.hnh.domain.entity

interface ExerciseBody {
    val id: Long //uid
    val bodyPartName: String //운동부위 명
    val description: String //운동부위 설명
    val difficulty: Int //난이도
    val contentsCount: Int //운동 영상 수
    val totalCalorie: Long //소모 칼로리
    val thumbnailFileName: String //썸네일 파일 이름
    val thumbnailPath: String // 썸네일 주소
    val isAi: Boolean //AI포함여부
    val isVisible: Boolean //사용자 이용 가능여부
    val createdDate: String //생성날짜
    val modifiedDate: String //수정날짜
}