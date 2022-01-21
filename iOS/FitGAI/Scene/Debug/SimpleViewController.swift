//
//  SimpleViewController.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/23.
//

import UIKit
import RealmSwift

class SimpleViewController: SampleBaseTableViewController {
    // MARK: - Private Variable
    private enum ListItem : String, CaseIterable {
        case fileListCsv
        case uploadCsv
        case createDB       = "DB를 생성합니다."
        case crash          = "Crash"
        case warningMessage = "firebase로 warning message 보내기"
        case fatalError     = "fatalError를 화면에 찍고, firebase로도 warning message 보냅니다."
        case loading        = "2초가 loading view 가 보입니다."
        case writeNameKihoon = "GlobalDB에 Name key에 kihoon을 write합니다."
        case writeNameHosung = "GlobalDB에 Name key에 hosung을 write합니다."
        case readGlobalDB = "GlobalDB에 Name key의 값을 읽습니다."
        case networkStatus = "네트워크 상태를 확인하는 함수"
        case actionSheet2
        case actionSheet4
        case actionSheet6
        case actionSheet10
        case actionSheet20
    }
    
    private var listArray:[ListItem] = ListItem.allCases
    private var csvRepository = CsvRepository(requestNetworkSubject:nil)


    // MARK: - Override Method or Basic Method
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return listArray.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell(style: .subtitle, reuseIdentifier: NSStringFromClass(Self.self))
                
        cell.textLabel?.text = "\(listArray[indexPath.row])"
        cell.detailTextLabel?.text = "\(listArray[indexPath.row].rawValue)"
        
        return cell
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        switch listArray[indexPath.row] {
        case .fileListCsv:
            do {
                let documentsUrl =  FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!
                let directoryContents = try FileManager.default.contentsOfDirectory(at: documentsUrl, includingPropertiesForKeys: nil)
                HHLog.d(.temporary, directoryContents)
            } catch let error {
                HHLog.e(.temporary, .no0044, "error = \(error)")
            }
        case .uploadCsv:
            csvRepository.uploadCsv(fileName: "2022-01-12 12:15:16.19__1.csv", setNum: 1)
                .subscribe { result in
                    HHLog.d(.network, "uploadCsv() result = \(result)")
                }
        case .createDB :
            let realm = try! Realm()
            try! realm.write {
                realm.add(BodyPartCourseDB(serverId: 34, serverDescription: "serverDescription", thumbnailFileName: "thumbnailFileName",
                                           thumbnailPath: "thumbnailPath", isAi: true, isVisible: true, totalCalorie: 3, difficulty: 1, bodyPartName: "bodyPartName", contentsCount: 2))
            }
        case .crash:
            let numbers = [0]
            let _ = numbers[1]
        case .warningMessage:
            HHLog.e(.core, .no0045, "warning message를 출력합니다.")
        case .fatalError:
            HHFatalError("Fatal Error 출력합니다.")
        case .loading:
            LoadingView.shared.show()
            delayExecute(2) {
                LoadingView.shared.dismiss()
            }
        case .writeNameKihoon :
            GlobalDB.set(key: GlobalDB.Key.name, value: "kihoon.kim")
//            let realm = try! Realm()
//            try! realm.write {
//                realm.add(GlobalDB(key: GlobalDB.Key.name.rawValue, value: "kihoon.kim"), update: .modified)
//            }
        case .writeNameHosung :
            GlobalDB.set(key: GlobalDB.Key.name, value: "hosung.kim")
//            let realm = try! Realm()
//            try! realm.write {
//                realm.add(GlobalDB(key: GlobalDB.Key.name.rawValue, value: "hosung.kim"), update: .modified)
//            }
        case .readGlobalDB :
            let result = GlobalDB.getStringValue(key: GlobalDB.Key.name)
//            let realm = try! Realm()
//            let result = realm.objects(GlobalDB.self)
            HHLog.d(.temporary, "result = \(result)")
            
        case .networkStatus:
            let isAvailableNetwork = Reachability.isAvailableNetwork()
            let isWifiOff = Reachability.isWifiOff()
            
            HHLog.d(.network, "isAvailableNetwork = \(isAvailableNetwork)")
            HHLog.d(.network, "isWifiOff = \(isWifiOff)")
        case .actionSheet2:
            let actionSheet = HHActionSheetViewController(title: "스타크래프트 (2)")
            addAction(actionSheet: actionSheet, count: 2)
            present(actionSheet, animated: false)
        case .actionSheet4:
            let actionSheet = HHActionSheetViewController(title: "스타크래프트 (4)")
            addAction(actionSheet: actionSheet, count: 4)
            present(actionSheet, animated: false)
        case .actionSheet6:
            let actionSheet = HHActionSheetViewController(title: "스타크래프트 (6)")
            addAction(actionSheet: actionSheet, count: 6)
            present(actionSheet, animated: false)
        case .actionSheet10:
            let actionSheet = HHActionSheetViewController(title: "스타크래프트 (10)")
            addAction(actionSheet: actionSheet, count: 10)
            present(actionSheet, animated: false)
        case .actionSheet20:
            let actionSheet = HHActionSheetViewController(title: "스타크래프트 (20)")
            addAction(actionSheet: actionSheet, count: 20)
            present(actionSheet, animated: false)
        }
    }

    func addAction(actionSheet:HHActionSheetViewController, count:Int) {
        enum Name : String, CaseIterable{
            case terran     = "테란"
            case zerg       = "저그"
            case protoss    = "프로토스"
            case random     = "랜덤"
            case lisa       = "리사"
            case lico       = "리코"
            case selly      = "셀리"
        }
       
        for i in 0..<count {
            let name = Name.allCases.randomElement()
            actionSheet.addAction(HHActionSheetAction(title: "\(i). \(name!.rawValue)", image: nil, handler: {
                HHLog.d(.core, "\(i). \(name!.rawValue)이 선택됨")
            }))
        }
    }
}
