//
//  BodyPartCourseModel.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/21.
//

import Foundation

struct BodyPartCourseModel {
    // MARK: - Public Variable
    var id: Int
    var description: String?
    var thumbnailFileName: String?
    var thumbnailPath: String?
    var isAi: Bool
    var isVisible : Bool
    var totalCalorie: Int
    var difficulty: Int
    var bodyPartName: String?
    var contentsCount: Int
    
    // MARK: - Override Method or Basic Method
    init(bodyPartCourseVO:BodyPartCourseVO) {
        id                  = bodyPartCourseVO.id ?? 0
        description         = bodyPartCourseVO.description
        thumbnailFileName   = bodyPartCourseVO.thumbnailFileName
        thumbnailPath       = bodyPartCourseVO.thumbnailPath
        isAi                = bodyPartCourseVO.isAi ?? false
        isVisible           = bodyPartCourseVO.isVisible ?? false
        totalCalorie        = bodyPartCourseVO.totalCalorie ?? 0
        difficulty          = bodyPartCourseVO.difficulty ?? 0
        bodyPartName        = bodyPartCourseVO.bodyPartName
        contentsCount       = bodyPartCourseVO.contentsCount ?? 0
    }
    
    init(bodyPartCourseDB:BodyPartCourseDB) {
        id                  = bodyPartCourseDB.serverId
        description         = bodyPartCourseDB.serverDescription
        thumbnailFileName   = bodyPartCourseDB.thumbnailFileName
        thumbnailPath       = bodyPartCourseDB.thumbnailPath
        isAi                = bodyPartCourseDB.isAi
        isVisible           = bodyPartCourseDB.isVisible
        totalCalorie        = bodyPartCourseDB.totalCalorie
        difficulty          = bodyPartCourseDB.difficulty
        bodyPartName        = bodyPartCourseDB.bodyPartName
        contentsCount       = bodyPartCourseDB.contentsCount
    }

    init() {
        id                  = 0
        description         = ""
        thumbnailFileName   = ""
        thumbnailPath       = ""
        isAi                = false
        isVisible           = false
        totalCalorie        = 0
        difficulty          = 0
        bodyPartName        = ""
        contentsCount       = 0
    }
}
