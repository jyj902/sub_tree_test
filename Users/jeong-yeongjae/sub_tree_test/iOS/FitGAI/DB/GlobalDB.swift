//
//  GlobalDB.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/11.
//

import UIKit
import RealmSwift

class GlobalDB: Object {
    enum Key: String {
        case name
        
    }
    // MARK: - Public Variable
    @objc dynamic var key: String
    @objc dynamic var value: String

    // MARK: - Override Method or Basic Method
    override init() {
        key = ""
        value = ""
        super.init()
    }
    
    init(key:String, value:String) {
        self.key = key
        self.value = value
    }
    
    override class func primaryKey() -> String? {
        return "key"
    }
    
    override var description: String {
        return
        [
            "key": nonNilString(self.key),
            "value": nonNilString(self.value),
        ].description
    }
    
    class func set(key:Key, value:Any) {
        let realm = try! Realm()
        try! realm.write {
            realm.add(GlobalDB(key: key.rawValue, value: "\(value)"), update: .modified)
        }
    }
    
    class func getStringValue(key:Key) -> String {
        let realm = try! Realm()
        return realm.object(ofType:GlobalDB.self, forPrimaryKey:key.rawValue)?.value ?? ""
    }

    class func getIntValue(key:Key) -> Int {
        return Int(GlobalDB.getStringValue(key:key)) ?? 0
    }
    
    class func getBoolValue(key:Key) -> Bool {
        return Bool(GlobalDB.getStringValue(key:key)) ?? false
    }
}
