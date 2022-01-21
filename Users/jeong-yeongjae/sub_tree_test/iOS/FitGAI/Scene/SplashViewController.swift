//
//  SplashViewController.swift
//  UIComponentSample
//
//  Created by kihoon.kim on 04/09/2019.
//  Copyright © 2019 hnh. All rights reserved.
//

import UIKit
// MARK: - Public Outter Class, Struct, Enum, Protocol

class SplashViewController: BaseViewController, UIDynamicAnimatorDelegate {
    
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    // MARK: - IBOutlet
    // MARK: - Private Variable
    private var lbLfront:UILabel!
    private var lbIfront:UILabel!
    private var lbFfront:UILabel!
    private var lbLback:UILabel!
    private var lbIback:UILabel!
    private var lbFback:UILabel!

    private var timer:Timer?

    private var animator: UIDynamicAnimator!
    private var frameWidth:CGFloat = 0
    private var frameHeight:CGFloat = 0
    private var largeFontSize:CGFloat = 0
    private var smallFontSize:CGFloat = 0
    private var bottomCoord:CGFloat = 0

    // MARK: - Override Method or Basic Method
    override func viewDidLoad() {
        super.viewDidLoad()
        frameWidth = CGFloat(view.frame.size.width)
        frameHeight = CGFloat(view.frame.size.height)
        largeFontSize = CGFloat(frameWidth * 0.2)
        smallFontSize = CGFloat(frameWidth * 0.05)
        bottomCoord = CGFloat(frameHeight/2 + largeFontSize/2)

        lbLfront = UILabel(frame: CGRect(x:frameWidth * 0.06, y:bottomCoord - largeFontSize, width:largeFontSize, height: largeFontSize))
        lbLfront.backgroundColor = UIColor(displayP3Red: 0, green: 0, blue: 0, alpha: 0)
        lbLfront.text = "H"
        lbLfront.font = UIFont.systemFont(ofSize: largeFontSize)
        lbLfront.textColor = UIColor(rgb: 0xcc0900)
        lbLfront.sizeToFit()
        view.addSubview(lbLfront)

        lbIfront = UILabel(frame: CGRect(x:frameWidth/2 - largeFontSize * 0.7, y:bottomCoord - largeFontSize, width:largeFontSize, height: largeFontSize))
        lbIfront.backgroundColor = UIColor(displayP3Red: 0, green: 0, blue: 0, alpha: 0)
        lbIfront.text = "&"
        lbIfront.font = UIFont.systemFont(ofSize: largeFontSize)
        lbIfront.textColor = UIColor(rgb: 0xfffc00)
        lbIfront.sizeToFit()
        view.addSubview(lbIfront)

        lbFfront = UILabel(frame: CGRect(x:frameWidth/2 + largeFontSize * 0.5, y:bottomCoord - largeFontSize, width:largeFontSize, height: largeFontSize))
        lbFfront.backgroundColor = UIColor(displayP3Red: 0, green: 0, blue: 0, alpha: 0)
        lbFfront.text = "H"
        lbFfront.font = UIFont.systemFont(ofSize: largeFontSize)
        lbFfront.textColor = UIColor(rgb: 0x0052ff)
        lbFfront.sizeToFit()
        view.addSubview(lbFfront)

        lbLback = UILabel(frame: CGRect(x:frameWidth/2 - largeFontSize * 1.5, y:0, width:smallFontSize, height: smallFontSize))
        lbLback.backgroundColor = UIColor(displayP3Red: 0, green: 0, blue: 0, alpha: 0)
        lbLback.text = "ealth"
        lbLback.font = UIFont.systemFont(ofSize: smallFontSize)
        lbLback.textColor = UIColor(rgb: 0xff7500)
        lbLback.sizeToFit()
        view.addSubview(lbLback)

        lbIback = UILabel(frame: CGRect(x:frameWidth/2 - largeFontSize*0.06, y:0, width:smallFontSize, height: smallFontSize))
        lbIback.backgroundColor = UIColor(displayP3Red: 0, green: 0, blue: 0, alpha: 0)
        lbIback.text = "nd"
        lbIback.font = UIFont.systemFont(ofSize: smallFontSize)
        lbIback.textColor = UIColor(rgb: 0x01ff16)
        lbIback.sizeToFit()
        view.addSubview(lbIback)

        lbFback = UILabel(frame: CGRect(x:frameWidth/2 + largeFontSize * 1.25, y:0, width:smallFontSize, height: smallFontSize))
        lbFback.backgroundColor = UIColor(displayP3Red: 0, green: 0, blue: 0, alpha: 0)
        lbFback.text = "appyness"
        lbFback.font = UIFont.systemFont(ofSize: smallFontSize/2)
        lbFback.textColor = UIColor(rgb: 0x6700cc)
        lbFback.sizeToFit()
        view.addSubview(lbFback)


        // 중력, 충돌 behavior 생성
        let gravity: UIGravityBehavior = UIGravityBehavior(items: [ lbLback, lbIback, lbFback])
        let collision: UICollisionBehavior = UICollisionBehavior(items: [ lbLback, lbIback, lbFback])
        collision.translatesReferenceBoundsIntoBoundary = true
        collision.collisionMode = .boundaries

        // 화면 바닥면에

        collision.addBoundary(withIdentifier:"block" as NSCopying,
                      from: CGPoint(x:0, y:bottomCoord),
            to: CGPoint(x:view.bounds.size.width, y:bottomCoord))

        animator =  UIDynamicAnimator(referenceView: self.view)
        animator.addBehavior(gravity)
        animator.addBehavior(collision)

        timer = Timer.scheduledTimer(timeInterval: 1.7, target: self, selector: #selector(finish), userInfo: nil, repeats: true)
    }
    
    // MARK: - Public Method
    // MARK: - Private Method
    @objc private func finish() {
        self.timer!.invalidate()
        self.timer = nil
        
        let vc:UIViewController
        
        #if false    // Debugging 용
        vc = SampleMainViewController()
        #else
        if AccountManager.shared.isLogin() {
            vc = UIViewController.getViewController(viewControllerEnum: .main)
        } else {
            vc = UIViewController.getViewController(viewControllerEnum: .login)
        }
        #endif
        
        UINavigationController.setRootViewController(viewController: vc, withNavigationController: true)
    }
    // MARK: - IBAction
}

// MARK: - Delegate
