//
//  BodyPartCourseDB.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/22.
//
import Foundation
import RealmSwift

class BodyPartCourseDB: Object {
    
    // MARK: - Public Variable
    @objc dynamic var uuid: String = UUID().uuidString

    @objc dynamic var serverId: Int
    @objc dynamic var serverDescription: String?
    @objc dynamic var thumbnailFileName: String?
    @objc dynamic var thumbnailPath: String?
    @objc dynamic var isAi: Bool
    @objc dynamic var isVisible : Bool
    @objc dynamic var totalCalorie: Int
    @objc dynamic var difficulty: Int
    @objc dynamic var bodyPartName: String?
    @objc dynamic var contentsCount: Int
    
    // MARK: - Override Method or Basic Method
    override init() {
        serverId = 0
        serverDescription = ""
        thumbnailFileName = ""
        thumbnailPath = ""
        isAi = false
        isVisible = false
        totalCalorie = 0
        difficulty = 0
        bodyPartName = ""
        contentsCount = 0
        
        super.init()
    }
    
    init(serverId:Int?, serverDescription:String?, thumbnailFileName: String?, thumbnailPath: String?, isAi: Bool?, isVisible : Bool?, totalCalorie: Int?, difficulty: Int?, bodyPartName: String?, contentsCount: Int?) {
        self.serverId = serverId ?? 0
        self.serverDescription = serverDescription
        self.thumbnailFileName = thumbnailFileName
        self.thumbnailPath = thumbnailPath
        self.isAi = isAi ?? false
        self.isVisible = isVisible ?? false
        self.totalCalorie = totalCalorie ?? 0
        self.difficulty = difficulty ?? 0
        self.bodyPartName = bodyPartName
        self.contentsCount = contentsCount ?? 0
        super.init()
    }
    
    init(bodyPartCourse:BodyPartCourseModel) {
        self.serverId = bodyPartCourse.id
        self.serverDescription = bodyPartCourse.description
        self.thumbnailFileName = bodyPartCourse.thumbnailFileName
        self.thumbnailPath = bodyPartCourse.thumbnailPath
        self.isAi = bodyPartCourse.isAi
        self.isVisible = bodyPartCourse.isVisible
        self.totalCalorie = bodyPartCourse.totalCalorie
        self.difficulty = bodyPartCourse.difficulty
        self.bodyPartName = bodyPartCourse.bodyPartName
        self.contentsCount = bodyPartCourse.contentsCount
        super.init()
    }
    
    override class func primaryKey() -> String? {
        return "uuid"
    }
    
    override var description: String {
        return "BodyPartCourseDB"
        [
            "serverId": nonNilString(self.serverId),
            "serverDescription": nonNilString(self.serverDescription),
            "thumbnailFileName": nonNilString(self.thumbnailFileName),
            "thumbnailPath": nonNilString(self.thumbnailPath),
            "isAi": self.isAi,
            "isVisible": self.isVisible,
            "totalCalorie": self.totalCalorie,
            "difficulty": self.difficulty,
            "bodyPartName": nonNilString(self.bodyPartName),
            "contentsCount": nonNilString(self.contentsCount)
        ].description
    }
}
