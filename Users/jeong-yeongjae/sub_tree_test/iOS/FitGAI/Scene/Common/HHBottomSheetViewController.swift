//
//  HHBottomSheetViewController.swift
//  SwiftSampleCollection
//
//  Created by 김기훈 on 2021/11/10.
//

import UIKit

// MARK: - Public Outter Class, Struct, Enum, Protocol

class HHBottomSheetViewController: UIViewController {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    // MARK: - IBOutlet
    // MARK: - Private Variable
    private let dismissDuration = 0.3
    private var backgroundView:UIView
    private var btnClose:UIButton
    private var contentViewController:UIViewController
    private var contentHeight:CGFloat = 0
        
    // MARK: - Override Method or Basic Method
    init(contentViewController:UIViewController, contentHeight:CGFloat){
        backgroundView = UIView(frame: .zero)
        btnClose = UIButton(frame: .zero)
        self.contentViewController = contentViewController
        self.contentHeight = contentHeight
        
        super.init(nibName: nil, bundle: nil)
        
        modalPresentationStyle = .overCurrentContext
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
        btnClose.accessibilityLabel = "CP_UX30_ACCESS_DRAW_CLOSER"
        backgroundView.addSubview(btnClose)

        contentViewController.view.autoresizingMask = [.flexibleLeftMargin, .flexibleRightMargin, .flexibleTopMargin, .flexibleBottomMargin]
        contentViewController.view.frame = CGRect(x: Coordinate.safeInsetLeft, y: view.frame.maxY, width: view.frame.width - Coordinate.safeInsetRight - Coordinate.safeInsetLeft, height: contentHeight)

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
        delayExecute(0.1) { [weak self] in
            self?.removeChildView()
            self?.contentViewController.view.frame = CGRect(x: Coordinate.safeInsetLeft,
                                                      y: Coordinate.height - self!.contentHeight,
                                        width: Coordinate.width - Coordinate.safeInsetLeft - Coordinate.safeInsetRight,
                                        height: self!.contentHeight)
            self?.addChildView()
        }
    }
    
    // MARK: - Public Method
    class func bottomSheetViewController(contentViewController:UIViewController, contentHeight:CGFloat) -> Self {
        let bottomSheet = HHBottomSheetViewController(contentViewController:contentViewController, contentHeight:contentHeight)
        return bottomSheet as! Self
    }
    
    // MARK: - Private Method
    private func dismissActionSheet(animation:Bool, handler:(()->Void)?) {
        UIView.animate(withDuration: animation ? dismissDuration : 0.0, delay: 0.0, options: [.allowUserInteraction], animations: {[weak self] in
            guard let self = self else {
                return
            }
            self.contentViewController.view.frame = CGRect(x: Coordinate.safeInsetLeft, y: self.view.frame.maxY, width: self.view.frame.width - Coordinate.safeInsetLeft - Coordinate.safeInsetRight, height: self.view.frame.height)
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
    
    private func addChildView() {
        addChild(contentViewController)
        view.addSubview(contentViewController.view)
        contentViewController.didMove(toParent: self)
    }
    
    private func removeChildView() {
        self.contentViewController.willMove(toParent: nil)
        self.contentViewController.view.removeFromSuperview()
        self.contentViewController.removeFromParent()
    }

    // MARK: - IBAction
    @objc func onClickClose(_ sender: UIButton) {
        HHLog.d(.core, "onClickClose")
        dismissActionSheet(animation: true, handler: nil)
    }
}

class HHBottomSheetChildViewController : UIViewController {
    // MARK: - Private Variable
    private var firstFlag:Bool = true

    // MARK: - Override Method or Basic Method
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        if firstFlag {
            UIView.animate(withDuration: 0.3) { [weak self] in
                if let self = self {
                    let width = Coordinate.width - Coordinate.safeInsetRight - Coordinate.safeInsetLeft
                    self.view.frame = CGRect(x: Coordinate.safeInsetLeft, y: Coordinate.height - self.view.frame.height, width: width, height: self.view.frame.height)
                }
            }
            firstFlag = false
        }
        
        // 모서리 Round 처리
        let maskPath = UIBezierPath(roundedRect: view.bounds, byRoundingCorners: [.topLeft, .topRight], cornerRadii: CGSize(width: 24, height: 24))
        let maskLayer = CAShapeLayer()
        maskLayer.path  = maskPath.cgPath
        view.layer.mask = maskLayer;
    }
}
