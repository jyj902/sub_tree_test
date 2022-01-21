//
//  HHLog.swift
//
//  Created by kihoon.kim on 03/09/2019.
//

import UIKit
import FirebaseCrashlytics

// MARK: - Public Outter Class, Struct, Enum, Protocol
public enum LogCategory {
    case appDelegate
    case audio
    case core
    case database
    case login
    case main
    case moviePlay
    case network
    case poseDetect
    case temporary
    case time
    case view
}

class HHLog {
    static let outputNSLog = true

    // MARK: - Public Method
    public class func e<T>(_ category:LogCategory, _ errorNo:HHErrorNo, _ object: T?, filename: String = #file, line:Int = #line, funcName:String = #function) {
        if DebugVariable.shared.logEnable, let object = object {
            let th = Thread.current.isMainThread ? "M" : Thread.current.name ?? "-"
            if outputNSLog {
                NSLog("!!!![E][\(category)]{\(th)}\(filename.components(separatedBy: "/").last ?? "")-(\(line))-\(funcName)(\(errorNo.rawValue)): \(object)")
            } else {
                print("!!!![E][\(category)](\(errorNo.rawValue)) \(object)")
            }
        }
        if let object = object {
            // Firebase 로 로그 전송
            var userInfo: [String : Any] = [:]
            userInfo["Error"] = "[E][\(category)](\(errorNo.rawValue)) \(object)"
            let error = NSError(domain: "HHLog", code: errorNo.rawValue, userInfo: userInfo)
            Crashlytics.crashlytics().record(error: error)
        }
    }
    
    public class func w<T>(_ category:LogCategory, _ object: T?, filename: String = #file, line:Int = #line, funcName:String = #function) {
        if DebugVariable.shared.logEnable, let object = object {
            let th = Thread.current.isMainThread ? "M" : Thread.current.name ?? "-"
            if outputNSLog {
                NSLog("!!!![W][\(category)]{\(th)}\(filename.components(separatedBy: "/").last ?? "")-(\(line))-\(funcName) :\(object)")
            } else {
                print("[W][\(category)] \(object)")
            }
        }
    }
    
    public class func d<T>(_ category:LogCategory, _ object: T?, filename: String = #file, line:Int = #line, funcName:String = #function) {
        if DebugVariable.shared.logEnable, let object = object {
            let th = Thread.current.isMainThread ? "M" : Thread.current.name ?? "-"
            if outputNSLog {
                NSLog("[D][\(category)]{\(th)}\(filename.components(separatedBy: "/").last ?? "")-(\(line))-\(funcName) :\(object)")
            } else {
                print("[D][\(category)] \(object)")
            }
        }
    }
    
    public class func v<T>(_ category:LogCategory, _ object: T?, filename: String = #file, line:Int = #line, funcName:String = #function) {
        if DebugVariable.shared.logEnable, let object = object {
            let th = Thread.current.isMainThread ? "M" : Thread.current.name ?? "-"
            if outputNSLog {
                NSLog("[V][\(category)]{\(th)}\(filename.components(separatedBy: "/").last ?? "")-(\(line))-\(funcName) :\(object)")
            } else {
                print("[V][\(category)]\(object)")
            }
        }
    }
    
    // trace 를 목적으로 하는 함수로 호출되는 위치만 로그로 출력합니다.
    public class func t(_ category:LogCategory, filename: String = #file, line:Int = #line, funcName:String = #function) {
        if DebugVariable.shared.logEnable {
            let th = Thread.current.isMainThread ? "M" : Thread.current.name ?? "-"
            if outputNSLog {
                NSLog("[D][\(category)]{\(th)}\(filename.components(separatedBy: "/").last ?? "")-(\(line))-\(funcName)")
            } else {
                print("[D][\(category)]{\(th)}\(filename.components(separatedBy: "/").last ?? "")-(\(line))-\(funcName)")
            }
        }
    }
    
    // call stack 보여주기 위함.
    public class func cs(_ category:LogCategory, filename: String = #file, line:Int = #line, funcName:String = #function) {
        if DebugVariable.shared.logEnable {
            let th = Thread.current.isMainThread ? "M" : Thread.current.name ?? "-"
            if outputNSLog {
                NSLog("[D][\(category)]{\(th)}\(Thread.callStackSymbols)")
            } else {
                print("[D][\(category)]{\(th)}\(Thread.callStackSymbols)")
            }
        }
    }
}
