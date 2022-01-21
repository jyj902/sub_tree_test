package com.hnh.data

/**
 * Created by hyerim on 2021/10/25...
 */

/** hnh.ai */
//    const val BASE_URL = "http://192.168.0.9:80"
const val BASE_URL = "http://hnhinc.co.kr:8080"
const val API_V1 = "/api/v1"

const val VERSION = "/version/1"

const val USER_LOGIN = "/user/{type}/{token}"
const val USER_INFO = "/user/info/token"

const val COURSE = "/course"
const val COURSE_BODY = "/course/bodypart"
const val COURSE_BODY_LIST = "/course/bodypart/all"
const val COURSE_BODY_CONTENTS = "/course/bodypart/contents/{id}"
const val COURSE_BODY_DETAIL = "/course/bodypart/one/{id}"

const val SKELETON_UPLOAD = "/record/personal/new"

const val PERSONAL_RECORD = "/record/personal"
const val PERSONAL_RECORD_NEW = "/record/personal/new"
const val PERSONAL_RECORD_TOTAL = "/record/personal/total/{userId}"
const val PERSONAL_RECORD_LIST = "/record/personal/all/{userId}"

const val VIDEO = "/contents/{id}"
const val VIDEO_LIST = "/contents"
const val VIDEO_LIST_COURSE = "/course/{id}"

/** kakao */
const val KAKAO_BASE_URL = "https://kauth.kakao.com"
const val KAKAO_REDIRECT_URL = "http://hnh.ai:8080/login/oauth2/code/kakao"
const val KAKAO_LOGIN = "/oauth/authorize"
const val KAKAO_TOKEN = "/oauth/token"