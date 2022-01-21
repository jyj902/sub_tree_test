//
//  ActionSheetController.swift
//  SwiftSampleCollection
//
//  Created by 김기훈 on 2021/03/26.
//  Copyright © 2021 com.hnh. All rights reserved.
//
import Foundation
import UIKit

// MARK: - Public Outter Class, Struct, Enum, Protocol
class HHActionSheetAction: NSObject {
    // MARK: - Public Variable
    public var handler: (() -> ())?
    public var title: String
    // MARK: - Private Variable
    private var image: UIImage?
    private var isNewBadge:Bool
    
    convenience init(title: String, image: UIImage?, handler: (() -> ())?) {
        self.init(title: title, image: image, isNewBadge: false, handler: handler)
    }

    required init(title: String, image: UIImage?, isNewBadge:Bool, handler: (() -> ())?) {
        self.title = title
        self.image = image
        self.isNewBadge = isNewBadge
        self.handler = handler
        
        super.init()
    }
}

class HHActionSheetViewController: UIViewController {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    // MARK: - IBOutlet
    // MARK: - Private Variable
    private let dismissDuration = 0.3
    private let backgroundView:UIView
    private let btnClose:UIButton
    private let childVC:HHActionSheetChildViewController
    // MARK: - Override Method or Basic Method
    init(title:String?){
        childVC = HHActionSheetChildViewController(title: title)!
        backgroundView = UIView(frame: .zero)
        btnClose = UIButton(frame: .zero)

        super.init(nibName: nil, bundle: nil)
        self.modalPresentationStyle = .overCurrentContext
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.backgroundColor = .clear
        
        backgroundView.backgroundColor = UIColor.bgInactive
        backgroundView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(backgroundView)

        btnClose.backgroundColor = .clear
        btnClose.addTarget(self, action: #selector(onClickClose(_:)), for: .touchUpInside)
        btnClose.isAccessibilityElement = true
        btnClose.translatesAutoresizingMaskIntoConstraints = false
        backgroundView.addSubview(btnClose)

        childVC.view.backgroundColor = .clear
        childVC.view.autoresizingMask = [.flexibleLeftMargin, .flexibleRightMargin, .flexibleTopMargin, .flexibleBottomMargin]
        childVC.view.frame = CGRect(x: Coordinate.safeInsetLeft, y: view.frame.maxY, width: view.frame.width - Coordinate.safeInsetRight - Coordinate.safeInsetLeft, height: view.frame.height)
        addChildView()

        backgroundView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        backgroundView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        backgroundView.topAnchor.constraint(equalTo: view.topAnchor).isActive = true
        backgroundView.bottomAnchor.constraint(equalTo: view.bottomAnchor).isActive = true

        btnClose.leadingAnchor.constraint(equalTo: backgroundView.leadingAnchor).isActive = true
        btnClose.trailingAnchor.constraint(equalTo: backgroundView.trailingAnchor).isActive = true
        btnClose.topAnchor.constraint(equalTo: backgroundView.topAnchor).isActive = true
        btnClose.bottomAnchor.constraint(equalTo: backgroundView.bottomAnchor).isActive = true
    }
    
    override func viewWillTransition(to size: CGSize, with coordinator: UIViewControllerTransitionCoordinator) {
        // did code
//        DispatchQueue.main.asyncAfter(deadline: .now() + 0.1) { [weak self] in
//            self?.removeChildView()
//
//            self?.childVC.view.frame = CGRect(x: Coordinate.safeInsetLeft,
//                                        y: self!.childVC.preferredTopY,
//                                        width: size.width - 2 * Coordinate.safeInsetLeft,
//                                        height: size.height - self!.childVC.preferredTopY)
//            self?.addChildView()
//            self?.btnClose.bottomAnchor.constraint(equalTo: self!.childVC.view.topAnchor).isActive = true
//        }

        removeChildView()
        if (UIDevice.current.orientation.isLandscape) {
            childVC.view.frame = CGRect(x: Coordinate.safeInsetTop,
                                        y: childVC.preferredTopY,
                                        width: size.width - 2 * Coordinate.safeInsetTop,
                                        height: size.height - childVC.preferredTopY)
        } else {
            childVC.view.frame = CGRect(x: Coordinate.safeInsetLeft,
                                        y: childVC.preferredTopY,
                                        width: size.width - 2 * Coordinate.safeInsetLeft,
                                        height: size.height - childVC.preferredTopY)
        }
        addChildView()
        btnClose.bottomAnchor.constraint(equalTo: childVC.view.topAnchor).isActive = true
    }
    // MARK: - Public Method
    func addAction(_ action:HHActionSheetAction) {
        childVC.addAction(action)
    }
    
    func dismissActionSheet(animation:Bool, handler: (() -> ())?) {
        UIView.animate(withDuration: animation ? dismissDuration : 0.0, delay: 0.0, options: [.allowUserInteraction], animations: {[weak self] in
            guard let self = self else {
                return
            }
            self.childVC.view.frame = CGRect(x: Coordinate.safeInsetLeft, y: self.view.frame.maxY, width: self.view.frame.width - Coordinate.safeInsetLeft - Coordinate.safeInsetRight, height: self.view.frame.height)
            self.view.backgroundColor = .clear
        }, completion: { [weak self] _ in
            guard let self = self else {
                return
            }
            self.removeChildView()
            self.view.removeFromSuperview()
            self.dismiss(animated: false, completion: {
                if let handler = handler {
                    handler()
                }
            })
        })
    }
    
    // MARK: - Private Method
    private func addChildView() {
        addChild(childVC)
        view.addSubview(childVC.view)
        childVC.didMove(toParent: self)
    }
    
    private func removeChildView() {
        self.childVC.willMove(toParent: nil)
        self.childVC.view.removeFromSuperview()
        self.childVC.removeFromParent()
    }
    
    // MARK: - IBAction
    @objc func onClickClose(_ sender: UIButton) {
        HHLog.d(.core, "onClickClose")
        dismissActionSheet(animation: true, handler: nil)
    }
}
