//
//  UIViewController+Util.swift
//  NetworkSample
//
//  Created by 김기훈 on 2021/11/22.
//

import UIKit

// MARK: - Public Outter Class, Struct, Enum, Protocol
enum ViewControllerEnum {
    case login
    case main
    case poseDetector
    case webBrowser
    case moviePlayer
    case exerciseList
    case debug
    case exerciseSetting
    case sampleMain

    case drawer
    case dialog
    case fatalError
}

extension UIViewController {
    // MARK: - Public Method
    class func getViewController(viewControllerEnum:ViewControllerEnum) -> UIViewController {
        switch viewControllerEnum {
        case .login:
            let sb = UIStoryboard(name: "LogIn", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: String(describing:LogInViewController.self))
            vc.modalPresentationStyle = UIModalPresentationStyle.fullScreen
            return vc
        case .main:
            let sb = UIStoryboard(name: "Main", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: String(describing:MainViewController.self))
            vc.modalPresentationStyle = UIModalPresentationStyle.fullScreen
            return vc
        case .poseDetector:
            let sb = UIStoryboard(name: "PoseDetector", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: String(describing:PoseDetectorViewController.self))
            vc.modalPresentationStyle = UIModalPresentationStyle.fullScreen
            return vc
        case .webBrowser:
            let sb = UIStoryboard(name: "WebBrowser", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: String(describing:WebBrowserViewController.self))
            vc.modalPresentationStyle = UIModalPresentationStyle.fullScreen
            return vc
        case .moviePlayer:
            let sb = UIStoryboard(name: "MoviePlayer", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: String(describing:MoviePlayerViewController.self))
            vc.modalPresentationStyle = UIModalPresentationStyle.fullScreen
            return vc
        case .exerciseList:
            let sb = UIStoryboard(name: "ExerciseList", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: String(describing:ExerciseListViewController.self))
            vc.modalPresentationStyle = UIModalPresentationStyle.fullScreen
            return vc

        case .debug:
            let sb = UIStoryboard(name: "Debug", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: String(describing:DebugViewController.self))
            vc.modalPresentationStyle = UIModalPresentationStyle.fullScreen
            return vc

        case .exerciseSetting:
            let sb = UIStoryboard(name: "ExerciseSetting", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: String(describing:ExerciseSettingViewController.self))
            vc.modalPresentationStyle = UIModalPresentationStyle.custom
            return vc
            
        case .sampleMain:
            let vc = SampleMainViewController(nibName: nil, bundle: nil)
            vc.modalPresentationStyle = UIModalPresentationStyle.fullScreen
            return vc

        case .drawer:
            let sb = UIStoryboard(name: "Drawer", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: String(describing:DrawerViewController.self))
            vc.modalPresentationStyle = UIModalPresentationStyle.fullScreen
            return vc
        case .dialog:
            let sb = UIStoryboard(name: "HHDialog", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: String(describing:HHDialogViewController.self))
            vc.modalPresentationStyle = UIModalPresentationStyle.custom
            return vc
        case .fatalError:
            let sb = UIStoryboard(name: "HHFatalError", bundle: nil)
            let vc = sb.instantiateViewController(withIdentifier: String(describing:HHFatalErrorViewController.self))
            vc.modalPresentationStyle = UIModalPresentationStyle.fullScreen
            return vc
        }
    }
    
    class func top() -> UIViewController {
        assert(Thread.isMainThread, "UI API called on a background thread")
        return UIViewController.top(UIApplication.shared.keyWindow?.rootViewController!)
    }
    
    class func top(_ rootViewController: UIViewController?) -> UIViewController {
        assert(Thread.isMainThread, "UI API called on a background thread")
        if rootViewController is UINavigationController {
            let navigationController = rootViewController as? UINavigationController
            return UIViewController.top((navigationController?.viewControllers)!.last)
        }
        if rootViewController is UITabBarController {
            let tabController = rootViewController as? UITabBarController
            return UIViewController.top(tabController?.selectedViewController)
        }
        if rootViewController?.presentedViewController != nil {
            return UIViewController.top(rootViewController?.presentedViewController)
        }
        return rootViewController ?? UIViewController()
    }
}
