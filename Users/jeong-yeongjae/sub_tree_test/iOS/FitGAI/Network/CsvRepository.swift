//
//  CsvRepository.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/07.
//

import Foundation
import RxSwift

class CsvRepository {
    // MARK: - Private Variable
    private var disposeBag =  DisposeBag()
    private var networking:NetworkingProtocol
    
    // MARK: - Override Method or Basic Method
    init(requestNetworkSubject:PublishSubject<Void>?) {
        networking = Networking(style: HHStyle(NetworkingStyle.verboseLogger | NetworkingStyle.activityIndicator), requestNetworkSubject:requestNetworkSubject)
    }
    
    // MARK: - Public Method
    func uploadCsv(fileName:String, setNum:Int) -> Single<Void> {
        do {
            let fileManager = FileManager.default
            let path = try fileManager.url(for: .documentDirectory, in: .allDomainsMask, appropriateFor: nil, create: true)
            let fileURL = path.appendingPathComponent(fileName)
            HHLog.d(.network, "fileName = \(fileName)")
            let csvString:String = try String(contentsOf: fileURL, encoding: .utf8)
            HHLog.d(.network, "csvString = \(csvString)")
            let fileSize = fileSize(forURL: fileURL)
            return networking.request(CsvAPI.uploadCsv(data: csvString.data(using: .utf8)!, fileName: fileName, userId:AccountManager.shared.id!, setNum:setNum, exerciseName:"Squat", fileSize:fileSize, runningTime: 10, isFreeMode: true))
                .map { _ in
                    return Void()
                }
        } catch let error {
            HHLog.e(.network, .no0042, "error = \(error)")
        }
        return Observable<Void>.empty().asSingle()
    }
    
    // MARK: - Private Method
    private func fileSize(forURL url: Any) -> Int {
        var fileURL: URL?
        var fileSizeValue = 0.0
        if (url is URL) || (url is String)
        {
            if (url is URL) {
                fileURL = url as? URL
            }
            else {
                fileURL = URL(fileURLWithPath: url as! String)
            }
            try? fileSizeValue = (fileURL?.resourceValues(forKeys: [URLResourceKey.fileSizeKey]).allValues.first?.value as! Double?)!
        }
        return Int(fileSizeValue)
    }
}
