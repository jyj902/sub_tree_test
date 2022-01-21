//
//  SampleBaseTableViewController.swift
//  SwiftSampleCollection
//
//  Created by 김기훈 on 2021/04/02.
//  Copyright © 2021 com.lge. All rights reserved.
//

import UIKit

class SampleBaseTableViewController: UITableViewController {
    // MARK: - Override Method or Basic Method
    class func getTitle() -> String {
        return "\(Self.self)"
    }
    
    class func getDetailTitle() -> String {
        return "\(Self.self) 예제"
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let className:String! = NSStringFromClass(type(of: self))
        let aClass = NSClassFromString(className) as! SampleBaseTableViewController.Type
        self.navigationItem.title = aClass.getTitle()
        self.tableView.tableFooterView = UIView(frame:CGRect.zero)
    }
}
