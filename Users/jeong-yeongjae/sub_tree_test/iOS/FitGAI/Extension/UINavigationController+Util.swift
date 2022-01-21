//
//  UINavigationController+Util.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/11/24.
//

import UIKit

extension UINavigationController {
    // MARK: - Public Method
    // ViewController를 초기화하면서 RootViewController로 설정
    class func setRootViewController(viewController: UIViewController, withNavigationController:Bool) {
        if withNavigationController == true {
            let navigationController =  HHNavigationController.init(rootViewController:viewController)
            navigationController.setupNavigationController()
            (UIApplication.shared.delegate as? AppDelegate)?.window?.rootViewController = navigationController
        } else {
            (UIApplication.shared.delegate as? AppDelegate)?.window?.rootViewController = viewController
        }
    }

    // 제일 상위 View Controller만 대체
    func replaceViewController(viewController: UIViewController?) {
        var viewControllers: [UIViewController]? = nil
        if let view = navigationController?.viewControllers {
            viewControllers = view
        }
        viewControllers?.removeLast()
        if let viewController = viewController {
            viewControllers?.append(viewController)
        }
        if let viewControllers = viewControllers {
            self.setViewControllers(viewControllers, animated: false)
        }
    }

    // view controller 찾아서 삭제
    func removeViewController(viewControllerClass: AnyClass) {
        var viewControllers: [UIViewController]? = nil
        if let view = navigationController?.viewControllers {
            viewControllers = view
        }
        for i in 0..<(viewControllers?.count ?? 0) {
            if (viewControllers?[i] is UIViewController) {
                viewControllers?.remove(at: i)
                break
            }
        }
        if let viewControllers = viewControllers {
            self.setViewControllers(viewControllers, animated: false)
        }
    }

    // 제일 상위의 View들을 삭제
    func removeTopViewController(count: Int) {
        var viewControllers: [UIViewController]? = nil
        if let view = navigationController?.viewControllers {
            viewControllers = view
        }
        for _ in 0..<count {
            viewControllers?.removeLast()
        }
        if let viewControllers = viewControllers {
            self.setViewControllers(viewControllers, animated: false)
        }
    }
    
    func pushViewController(viewControllerEnum:ViewControllerEnum, animated:Bool) {
        pushViewController(UIViewController.getViewController(viewControllerEnum:viewControllerEnum), animated: animated)
    }
}
