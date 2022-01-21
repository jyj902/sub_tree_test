//
//  UserViewController.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/27.
//

import UIKit
// MARK: - Public Outter Class, Struct, Enum, Protocol

class UserViewController: BaseViewController {

    // MARK: - Public Inner Class, Struct, Enum, Protocol
    enum MenuIndex: Int {
        case notice
        case settings
        case help
        case samples
    }
    
    private struct MenuItem {
        var index:MenuIndex
        var icon:String
        var title:String
    }
    
    // MARK: - Public Variable
    // MARK: - IBOutlet
    @IBOutlet weak var lbGreetingMessage: UILabel!
    @IBOutlet weak var lbEmail: UILabel!
    @IBOutlet weak var btnLogout: RoundButton!
    @IBOutlet weak var tbvMenu: UITableView!

    // MARK: - Private Variable
    private var menuLists:[MenuItem] = [
        MenuItem(index:.notice ,icon: "notice", title: TODO("공지사항")),
        MenuItem(index:.settings ,icon: "settings", title: TODO("설정")),
        MenuItem(index:.help ,icon: "help", title: TODO("문의하기")),
    ]
    
    // MARK: - Override Method or Basic Method
    override func viewDidLoad() {
        super.viewDidLoad()
        initializeViews()
        initializeVariables()
    }
    // MARK: - Public Method
    // MARK: - Private Method
    private func initializeViews() {
        lbGreetingMessage.text = TODO("\(AccountManager.shared.userName ?? TODO("이름 없음"))님 안녕하세요!")
        lbGreetingMessage.font = UIFont.typeA28.weight(.bold)
        lbGreetingMessage.textColor = UIColor.fontDarkPrimary
        lbEmail.text = AccountManager.shared.userEmail
        lbEmail.font = UIFont.typeA16
        lbEmail.textColor = UIColor.fontDarkSecondary
        btnLogout.setTitle(TODO("로그 아웃"), for: .normal)
        tbvMenu.delegate = self
        tbvMenu.dataSource = self
        tbvMenu.register(UINib(nibName: String(describing: IconTitleDescriptionTableViewCell.self), bundle: nil), forCellReuseIdentifier: String(describing: IconTitleDescriptionTableViewCell.self))
        tbvMenu.tableFooterView = UIView.init(frame: .zero)
    }
    
    private func initializeVariables() {
#if DEBUG
        menuLists.append(MenuItem(index:.samples ,icon: "sample", title: TODO("Samples")))
#endif
    }
    
    // MARK: - IBAction
    @IBAction func onClickLogout(_ sender: Any) {
        HHDialog(style:HHStyle(HHDialogStyle.dismissOnTouchBackground), titleText: TODO("로그 아웃"), contentsText: TODO("정말로 로그아웃하시겠습니까?"), okText: TODO("확인"), okHandler: { [weak self] in
            AccountManager.shared.processLogout()
        }, cancelText: TODO("취소"), cancelHandler: nil)
    }
}

// MARK: - Delegate
extension UserViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        let menuItem = menuLists[indexPath.row]
        switch menuItem.index {
        case .samples:
            let vc = UIViewController.getViewController(viewControllerEnum: .sampleMain)
            self.navigationController?.pushViewController(vc, animated: true)
        default:
            break
        }
        UIViewController.top().view.makeToast(menuItem.title)
    }
}

extension UserViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return menuLists.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: IconTitleDescriptionTableViewCell
        if let reusableCell = tableView.dequeueReusableCell(withIdentifier: String(describing: IconTitleDescriptionTableViewCell.self), for: indexPath) as? IconTitleDescriptionTableViewCell {
            cell = reusableCell
        } else {
            let objectArray = Bundle.main.loadNibNamed(String(describing: IconTitleDescriptionTableViewCell.self), owner: nil, options: nil)
            cell = objectArray![0] as! IconTitleDescriptionTableViewCell
        }
        let menuItem = menuLists[indexPath.row]
        cell.ivIcon.image = UIImage(named: menuItem.icon)
        cell.lbTitle.text = menuItem.title
        cell.lbDescription.text = ""
        cell.accessoryType = UITableViewCell.AccessoryType.disclosureIndicator
        return cell
    }
    
    
}
