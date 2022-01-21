//
//  HHNavigationController.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/31.
//

import UIKit

class HHNavigationController: UINavigationController {
    // MARK: - Override Method or Basic Method
    override var supportedInterfaceOrientations: UIInterfaceOrientationMask {
        return self.topViewController!.supportedInterfaceOrientations
    }
    
    // MARK: - Public Method
    func setupNavigationController() {
        //navigationController.navigationBar.prefersLargeTitles = true
        self.interactivePopGestureRecognizer?.isEnabled = true
        self.setNavigationBarHidden(true, animated: false)
    }
}
