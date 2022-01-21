//
//  ExerciseContentEntity.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/24.
//

import Foundation

//    {
//        "createdDate": "2021-12-17T12:04:56.406",
//        "modifiedDate": "2021-12-17T12:04:56.406",
//        "id": 1,
//        "title": "숄더프레스",
//        "exerciseName": "ShoulderPress",
//        "description": "테스트 안녕하세요\n",
//        "thumbnailFileName": "dc8b3d02-a15a-4afa-a88b-989cf2a50476.png",
//        "thumbnailPath": "http://hnh.ai:8080/api/v1/course/bodypart/thumbnail/1",
//        "videoFileName": "BarbellBentOverRow_kkw.mp4",
//        "videoPath": "http://hnh.ai:8080/api/v1/course/bodypart/video/1",
//        "difficulty": 1,
//        "calorie": 100,
//        "isAi": true,
//        "trainer": {
//            "createdDate": "2021-12-17T12:33:40",
//            "modifiedDate": "2021-12-17T12:33:40",
//            "id": 1,
//            "trainerName": "김영준",
//            "description": "역대최강",
//            "userId": "kyj",
//            "contactNumber": "010-1111-2222",
//            "exerciseContentsList": [],
//            "isActive": true
//        }
//    }

struct ExerciseContentVO: Codable {
    var id:Int?
    var title: String?
    var exerciseName: String?
    var description: String?
    var thumbnailFileName: String?
    var thumbnailPath: String?
    var videoFileName: String?
    var videoPath: String?
    var difficulty: Int?
    var calorie: Int?
    var isAi: Bool?
    var trainer: TrainerVO?
}

struct TrainerVO: Codable {
    var id: Int?
    var trainerName: String?
    var description: String?
    var userId: String?
    var contactNumber: String?
    var exerciseContentsList: [String]?
    var isActive: Bool?
}
