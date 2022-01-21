//
//  HomeViewController.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/20.
//

import UIKit
import RxSwift
import Moya
import RealmSwift

struct ListItem {
    var title:String
    var block:UITableViewCell.Type
}

class HomeViewController: BaseViewController {
    @IBOutlet weak var tbvList: UITableView!
    var menuItems:[ListItem] = [
        ListItem(title: TODO("운동하기"), block:BodyPartCourseBlock.self),
        ListItem(title: TODO("분석"), block:AnalyzeBlock.self)
    ]
    var bodyPartCourses:[BodyPartCourseModel] = []

    var requestNetworkSubject = PublishSubject<Void>()
    lazy var healthRepository = HealthRepository(requestNetworkSubject:self.requestNetworkSubject)
    var disposeBag = DisposeBag()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tbvList.delegate = self
        tbvList.dataSource = self
        tbvList.tableFooterView = UIView.init(frame: .zero)
        for menuItem in menuItems {
            let className = String(describing: menuItem.block)
            tbvList.register(UINib(nibName: className, bundle: nil), forCellReuseIdentifier: className)
        }
        
        let realm = try! Realm()
        let bodyPartCourseDBs = realm.objects(BodyPartCourseDB.self)
        
        for bodyPartCourseDB in bodyPartCourseDBs {
            bodyPartCourses.append(BodyPartCourseModel(bodyPartCourseDB:bodyPartCourseDB))
        }
        
        bindUI()
        requestNetworkSubject.onNext(Void())
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        HHLog.d(.time, "HomeViewController - viewDidAppear : \(CACurrentMediaTime() - GlobalVariable.shared.startTime)")
    }
    
    func bindUI() {
        requestNetworkSubject.subscribe(onNext: { [weak self] in
            HHLog.t(.network)
            self?.healthRepository.requestBodyPartCourseList()
                .subscribe{ [weak self] result in
                    switch result {
                    case .success(let bodyPartCourses):
                        HHLog.d(.temporary, "bodyPartCourses = \(bodyPartCourses)")
                        let realm = try! Realm()
                        try! realm.write {
                            realm.deleteAll()
                        }
                        for bodyPartCourse in bodyPartCourses {
                            try! realm.write {
                                realm.add(BodyPartCourseDB(bodyPartCourse:bodyPartCourse))
                            }
                        }
                        self?.bodyPartCourses = bodyPartCourses
                        self?.tbvList.reloadData()
                    case .failure(let error) :
                        HHLog.d(.temporary, "error = \(error)")
                    }
                }
        }).disposed(by: disposeBag)
    }
}

extension HomeViewController: UITableViewDelegate {
    
}

extension HomeViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        menuItems.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let menuItem = menuItems[indexPath.row]
        let block = menuItem.block
        let className = String(describing: block)
        
        let cell: UITableViewCell
        if let reusableCell = tableView.dequeueReusableCell(withIdentifier: className, for: indexPath) as? UITableViewCell {
            cell = reusableCell
        } else {
            let objectArray = Bundle.main.loadNibNamed(className, owner: nil, options: nil)
            cell = objectArray![0] as! UITableViewCell
        }
        
        if type(of:cell) == BodyPartCourseBlock.self {
            HHLog.d(.temporary, "BodyPartCourseBlock")
            let bodyPartCourseBlock:BodyPartCourseBlock = cell as! BodyPartCourseBlock
            bodyPartCourseBlock.lbTitle.text = menuItem.title
            bodyPartCourseBlock.bodyPartCourses = bodyPartCourses
            bodyPartCourseBlock.cvBodyPartCourse.reloadData()
        } else if type(of:cell) == AnalyzeBlock.self {
            HHLog.d(.temporary, "AnalyzeBlock")
            let analyzeBlock:AnalyzeBlock = cell as! AnalyzeBlock
        }

        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return UITableView.automaticDimension
    }
}
