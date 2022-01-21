//
//  CsvHelper.swift
//  FitGAI
//
//  Created by 정영재 on 2021/12/30.
//

import Foundation

class CsvHelper{
    let filePath : String
    init(_ filePath: String){
        self.filePath = filePath
    }
    func writeDataFloat(fileName: String, dataList: [[Float]]){
        var csvString : String = ""
        for data in dataList{
            var i = 1
            for cell in data{
                csvString.append("\(cell)")
                if data.count != i {
                    csvString.append(",")
                }
                i += 1
            }
            csvString = csvString.appending("\n")
        }
        let fileManager = FileManager.default
        do {
            let path = try fileManager.url(for: .documentDirectory, in: .allDomainsMask, appropriateFor: nil, create: true)
            let fileURL = path.appendingPathComponent(fileName)
            try csvString.write(to: fileURL, atomically: true, encoding: .utf8)
        } catch {
            HHLog.e(.poseDetect, .no0040, "error creating file")
        }
    }
}

