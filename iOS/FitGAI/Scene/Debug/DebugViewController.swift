//
//  DebugViewController.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/07.
//

import UIKit

fileprivate enum ButtonId:Int {
    case cancel = 0
    case ok     = 1
}

class DebugViewController: BaseViewController {
    // MARK: - IBOutlet
    @IBOutlet weak var navigationView: NavigationView!
    @IBOutlet weak var swLog: UISwitch!
    @IBOutlet weak var swAllocWatch: UISwitch!
    @IBOutlet weak var barButtonView: BarButtonView!
    
    // MARK: - Override Method or Basic Method
    override func viewDidLoad() {
        super.viewDidLoad()
        initializeViews()
    }
    
    // MARK: - Private Method
    func initializeViews() {
        navigationView.setTitle(title: TODO("Debug"))
        navigationView.backgroundColor = UIColor.clear
        navigationView.style = HHStyle(NavigationView.Style.hiddenBackButton)
        swLog.isOn = DebugVariable.shared.logEnable
        swAllocWatch.isOn = DebugVariable.shared.allocWatch
        barButtonView.addButtons(barButtonItems: [
            BarButtonItem(tag: ButtonId.cancel.rawValue, title: TODO("취소")),
            BarButtonItem(tag: ButtonId.ok.rawValue, title: TODO("저장")),
        ])
        barButtonView.delegate = self
        barButtonView.backgroundColor = UIColor.clear
    }
}

// MARK: - Delegate
extension DebugViewController: BarButtonViewDelegate {
    func onClickBarButton(_ sender: UIButton) {
        switch sender.tag {
        case ButtonId.ok.rawValue:
            HHLog.d(.core, "ok")
            // 값 저장
            DebugVariable.shared.logEnable = swLog.isOn
            UserDefaults.standard.setValue(DebugVariable.shared.logEnable, forKey: UserDefaultConst.logEnable.rawValue)
            DebugVariable.shared.allocWatch = swAllocWatch.isOn
            UserDefaults.standard.setValue(DebugVariable.shared.allocWatch, forKey: UserDefaultConst.allocWatch.rawValue)
            dismiss(animated: true)
        case ButtonId.cancel.rawValue:
            HHLog.d(.core, "cancel")
            dismiss(animated: true)
        default:
            HHLog.e(.core, .no0046, "ERROR")
        }
    }
}
