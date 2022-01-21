//
//  BaseViewController.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/11/24.
//

import UIKit
import FirebaseAnalytics
import RxSwift

class BaseViewController: UIViewController {
    // MARK: - Public Variable
    var supportBackSwipe:Bool = true

    // MARK: - Private Variable
    private var debugStatusView:DebugStatusView = DebugStatusView(frame: CGRect.zero)

    // MARK: - Override Method or Basic Method
    override var supportedInterfaceOrientations: UIInterfaceOrientationMask {
        return [.portrait]
    }

    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
        DebugVariable.shared.addAllocCount(self)
    }

    deinit {
        DebugVariable.shared.subtractAllocCount(self)
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        DebugVariable.shared.addAllocCount(self)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.addSubview(debugStatusView)
        debugStatusView.snp.makeConstraints { m in
            m.top.equalTo(view.snp.top).offset(10 + Coordinate.safeInsetTop)
            m.leading.equalToSuperview().offset(10)
            m.trailing.equalToSuperview().offset(-10)
        }
        debugStatusView.isHidden = true
        self.navigationController?.navigationBar.setBackgroundImage(UIImage(), for: .default)
        self.navigationController?.interactivePopGestureRecognizer?.delegate = self
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)

        if supportBackSwipe {
            self.navigationController?.interactivePopGestureRecognizer?.isEnabled = true
            self.navigationController?.interactivePopGestureRecognizer?.delegate = self
        } else {
            self.navigationController?.interactivePopGestureRecognizer?.isEnabled = false
        }
        if DebugVariable.shared.allocWatch {
            debugStatusView.isHidden = false
        } else {
            debugStatusView.isHidden = true
        }
        
        HHLog.d(.core, "enter Screen : \(NSStringFromClass(type(of: self)))")

        Analytics.logEvent(AnalyticsEventScreenView,
            parameters: [
                AnalyticsParameterScreenClass: NSStringFromClass(type(of: self)),
                AnalyticsParameterScreenName: NSStringFromClass(type(of: self)) + " Name"
            ])
    }
}

// MARK: - Delegate
extension BaseViewController: UIGestureRecognizerDelegate {
    
}
