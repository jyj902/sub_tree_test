//
//  BodyPartCourse.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/21.
//

import Foundation

//    "id" : 2,
//    "description" : "test",
//    "thumbnailFileName" : "dc8b3d02-a15a-4afa-a88b-989cf2a50476.png",
//    "thumbnailPath" : "http:\/\/hnh.ai:8080\/api\/v1\/course\/bodypart\/thumbnail\/2",
//    "isAi" : true,
//    "isVisible" : true,
//    "createdDate" : "2021-12-16T14:47:06.563",
//    "totalCalorie" : 0,
//    "modifiedDate" : "2021-12-16T14:47:06.563",
//    "difficulty" : 2,
//    "bodyPartName" : "상체운동",
//    "contentsCount" : 0
struct BodyPartCourseVO: Codable {
    var id: Int?
    var description: String?
    var thumbnailFileName: String?
    var thumbnailPath: String?
    var isAi: Bool?
    var isVisible: Bool?
    var createdDate: String?
    var totalCalorie: Int?
    var modifiedDate: String?
    var difficulty: Int?
    var bodyPartName: String?
    var contentsCount: Int?
}
