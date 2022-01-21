package com.hnh.domain.entity

/**
 * Created by hyerim on 2021/10/25...
 */
interface User {
    val id: Long //uid
    val name: String //이름
    val email: String //이메일
    val token: String //토큰
    val role: String //권한
    val socialType: String //소셜 로그인 종류 (구글, 카카오, 네이버)
    val isActivated: Boolean //활성화 여부
    val createDate: String //생성날짜
}