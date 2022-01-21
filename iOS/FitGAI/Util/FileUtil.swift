//
//  FileUtil.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/17.
//

import Foundation

class FileUtil {
    // MARK: - Public Method
    class func writeString(toFile str: String, fileName: String, path: String) throws {
        do {
            if !FileManager.default.fileExists(atPath: path) {
                try FileManager.default.createDirectory(atPath: path, withIntermediateDirectories: true, attributes: nil)
            }
            let filePath = URL(fileURLWithPath: path).appendingPathComponent(fileName).path
            if FileManager.default.fileExists(atPath: filePath) {
                try FileManager.default.removeItem(atPath: filePath)
            }
            try str.write(toFile: filePath, atomically: true, encoding: .utf8)
        } catch let error {
            HHLog.e(.core, .no0061, "FILE WRITE ERROR : \(error) fileName = \(fileName)")
        }
    }
}
