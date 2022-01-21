//
//  DialogViewControllerTransitioningDelegate.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/14.
//

import UIKit

class DialogViewControllerTransitioningDelegate: NSObject, UIViewControllerTransitioningDelegate {
    public static let shared = DialogViewControllerTransitioningDelegate()
    private override init() {
    }
    
    func animationController(forPresented presented: UIViewController, presenting: UIViewController, source: UIViewController) -> UIViewControllerAnimatedTransitioning? {
        let animationController = DialogTransitionAnimationController()
        animationController.presenting = true
        return animationController
    }

    func animationController(forDismissed dismissed: UIViewController) -> UIViewControllerAnimatedTransitioning? {
        let animationController = DialogTransitionAnimationController()
        animationController.presenting = false
        return animationController
    }
}

