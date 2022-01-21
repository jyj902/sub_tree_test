//
//  RealmManager.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/23.
//

import Foundation
import RealmSwift

class RealmManager : NSObject {
    // MARK: - Public Variable
    public static let shared = RealmManager()
    
    // MARK: - Private Variable
    private let DefaultSchemeVersion:UInt64 = 1
    
    // MARK: - Override Method or Basic Method
    private override init() {
        super.init()
    }
    
    // MARK: - Public Method
    func configurationDefaultRealm(loginType:LoginTypeEnum, userNo:String?) {
        HHLog.d(.database, "configurationDefaultRealm() loginType = \(loginType), userNo = \(userNo)")
        if let userNo = userNo {
            if let url = try? realmFileURL(loginType:loginType, userNo:userNo),
                let config = try? defaultConfiguration(fileURL:url) {
                Realm.Configuration.defaultConfiguration = config
            }
        } else {
            var config = Realm.Configuration()
            config.objectTypes = []
            Realm.Configuration.defaultConfiguration = config
        }
    }
    
    func deleteRealm(loginType:LoginTypeEnum, userNo: String?) throws {
        var fileURL = try? realmFileURL(loginType:loginType, userNo:userNo)
        if let fileURL = fileURL {
            if FileManager.default.fileExists(atPath: fileURL.path ?? "") {
                try FileManager.default.removeItem(at: fileURL)
            }
        }
        
        if let lockURL = fileURL?.appendingPathExtension("lock") {
            if FileManager.default.fileExists(atPath: lockURL.path ?? "") {
                try FileManager.default.removeItem(at: lockURL)
            }
        }
        if let managementURL = fileURL?.appendingPathExtension("management") {
            if FileManager.default.fileExists(atPath: managementURL.path ?? "") {
                try FileManager.default.removeItem(at: managementURL)
            }
        }
    }
    
    // MARK: - Private Method
    private func defaultConfiguration(fileURL:URL) -> Realm.Configuration {
        var config = Realm.Configuration(fileURL:fileURL)
        config.schemaVersion = DefaultSchemeVersion
        config.objectTypes = [
            BodyPartCourseDB.self,
            GlobalDB.self,
        ]
        config.migrationBlock = { migration, oldSchemaVersion in

        }
        return config
    }
    
    private func realmFileURL(loginType:LoginTypeEnum, userNo: String?) throws -> URL? {
        do {
            let documentDirectoryURL = try FileManager.default.url(for: .documentDirectory, in: .userDomainMask, appropriateFor: nil, create: false)
            var fileNameComponents: [String] = []
            fileNameComponents.append(userNo ?? "")
            fileNameComponents.append(loginType.rawValue)
            let realmFileName = "\(fileNameComponents.joined(separator: "-")).realm"
            return documentDirectoryURL.appendingPathComponent(realmFileName)
        } catch let error {
            HHLog.e(.database, .no0009, "error = \(error.localizedDescription).")
            return nil
        }
    }
}
