//
//  SampleMainViewController.swift
//
//
//  Created by kihoon.kim on 2018. 4. 9..
//  Copyright © 2018년 com.hnh. All rights reserved.
//
import UIKit

class SampleMainViewController: UITableViewController {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    enum VCMode {
        case xib
        case noxib
        case storyboard(String)
    }

    // MARK: - Private Variable
    private var listItemArray:[(name:String, mode:VCMode)] = [
        ("SimpleViewController", .noxib),
        ("Mp3ViewController", .noxib),
        ("GUISampleViewController", .noxib),
    ]
    private var appName:String!

    // MARK: - Override Method or Basic Method
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        if let dictionary = Bundle.main.infoDictionary {
            // 앱 이름
            appName = dictionary["CFBundleName"] as? String
        }
        self.navigationItem.title = appName;
        self.tableView.tableFooterView = UIView(frame:CGRect.zero)
        
        //listItemArray.reverse()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return listItemArray.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell(style: .subtitle, reuseIdentifier: "MyCell")
        var className:String
        className = listItemArray[indexPath.row].name
        let aClass = NSClassFromString(appName + "." + className)
        if((aClass!.isSubclass(of: SampleBaseTableViewController.self))) {
            let typeCastClass = aClass as! SampleBaseTableViewController.Type
            cell.textLabel?.text = typeCastClass.getTitle()
            cell.detailTextLabel?.text = "(" + className + ") " +  typeCastClass.getDetailTitle()
        } else if((aClass!.isSubclass(of: SampleBaseViewController.self))) {
            let typeCastClass = aClass as! SampleBaseViewController.Type
            cell.textLabel?.text = typeCastClass.getTitle()
            cell.detailTextLabel?.text = "(" + className + ") " +  typeCastClass.getDetailTitle()
        } else {
            let typeCastClass = aClass as! Title.Type
            cell.textLabel?.text = typeCastClass.getTitle()
            cell.detailTextLabel?.text = "(" + className + ") " +  typeCastClass.getDetailTitle()
        }

        cell.accessoryType = UITableViewCell.AccessoryType.none
        return cell
    }

    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        var listArray:[(name:String, mode:VCMode)]
        listArray = listItemArray
        let className = listArray[indexPath.row].name
        let aClass = NSClassFromString(appName + "." + className) as! UIViewController.Type
        
        switch listArray[indexPath.row].mode {
        case .noxib:
        let viewController = aClass.init(nibName: nil, bundle: nil)
        self.navigationController?.pushViewController(viewController, animated: true)
        case .xib:
            let viewController = aClass.init(nibName: className, bundle: nil)
            self.navigationController?.pushViewController(viewController, animated: true)
        case .storyboard(let storyboardName):
            let sb = UIStoryboard(name: storyboardName, bundle: nil)
            let controller:UIViewController = sb.instantiateViewController(withIdentifier: className)
            self.navigationController?.pushViewController(controller, animated: true)
        }
    }
}
