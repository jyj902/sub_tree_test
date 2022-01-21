//
//  ExerciseContentModel.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/24.
//

import Foundation

struct ExerciseContentModel {
    // MARK: - Public Variable
    var id: Int
    var title: String?
    var exerciseName: String?
    var description: String?
    var thumbnailFileName: String?
    var thumbnailPath: String?
    var videoFileName: String?
    var videoPath: String?
    var difficulty: Int
    var calorie: Int
    var isAi: Bool
    
    // MARK: - Override Method or Basic Method
    init() {
        id                  = 0
        title               = ""
        exerciseName        = ""
        description         = ""
        thumbnailFileName   = ""
        thumbnailPath       = ""
        videoFileName       = ""
        videoPath           = ""
        difficulty          = 0
        calorie             = 0
        isAi                = false
    }
    
    init(exerciseContentVO:ExerciseContentVO) {
        id                  = exerciseContentVO.id ?? 0
        title               = exerciseContentVO.title
        exerciseName        = exerciseContentVO.exerciseName
        description         = exerciseContentVO.description
        thumbnailFileName   = exerciseContentVO.thumbnailFileName
        thumbnailPath       = exerciseContentVO.thumbnailPath
        videoFileName       = exerciseContentVO.videoFileName
        videoPath           = exerciseContentVO.videoPath
        difficulty          = exerciseContentVO.difficulty ?? 0
        calorie             = exerciseContentVO.calorie ?? 0
        isAi                = exerciseContentVO.isAi ?? false
    }
    
    // MARK: - Public Method
    func getDifficultyString() -> String {
        if difficulty == 1 {
            return TODO("초급")
        } else if difficulty == 2 {
            return TODO("중급")
        } else if difficulty == 3 {
            return TODO("고급")
        }
        return TODO("없음")
    }
}
