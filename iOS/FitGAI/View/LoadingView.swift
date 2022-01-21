//
//  LoadingView.swift
//  NetworkSample
//
//  Created by 김기훈 on 2021/11/22.
//

import UIKit
import Lottie
import SnapKit
// MARK: - Public Outter Class, Struct, Enum, Protocol

class LoadingView: UIView {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    public static var shared = Bundle.main.loadNibNamed("LoadingView", owner: nil, options: nil)!.first as! LoadingView
    // MARK: - IBOutlet
    @IBOutlet weak var backgroundView: UIView!
    @IBOutlet weak var vActivityIndicator: AnimationView!
    
    // MARK: - Private Variable
    private var controlView: UIControl?
    private var frontWindow: UIWindow?

    // MARK: - Override Method or Basic Method
    deinit {
        HHLog.d(.view, "deinit")
    }
    
    required init?(coder: NSCoder) {
        super.init(coder:coder)
        
        controlView = UIControl()
        if let controlView = controlView {
            controlView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
            controlView.backgroundColor = UIColor.init(red: 0, green: 0, blue: 0, alpha: 0.3)
            controlView.isUserInteractionEnabled = true
        }
        
        let frontToBackWindows = (UIApplication.shared.windows as NSArray).reverseObjectEnumerator()
        for window in frontToBackWindows {
            guard let window = window as? UIWindow else {
                continue
            }
            let windowOnMainScreen = window.screen == UIScreen.main
            let windowIsVisible = !window.isHidden && window.alpha > 0
            let windowLevelSupported = window.windowLevel >= .normal

            let windowKeyWindow = window.isKeyWindow

            if windowOnMainScreen && windowIsVisible && windowLevelSupported && windowKeyWindow {
                frontWindow = window
            }
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()

        let animation = Animation.named("loading_02")
        vActivityIndicator.isHidden = false
        vActivityIndicator.animation = animation
    }
    
    // MARK: - Public Method
    func show() {
        if controlView?.superview == nil {
            let frontWindow = self.frontWindow
            controlView?.frame = frontWindow?.bounds ?? CGRect.zero
            if let controlView = controlView {
                frontWindow?.addSubview(controlView)
            }
        } else {
            if let controlView = controlView {
                controlView.superview?.bringSubviewToFront(controlView)
            }
        }

        if (superview == nil) {
            frame = controlView!.bounds
            controlView?.addSubview(self)
            alpha = 0
        }
        
        if UIApplication.shared.applicationState == .background {
            alpha = 1
        } else {
            UIView.animate(withDuration: 0.1, delay: 0, options: .curveEaseInOut, animations: { [weak self] in
                self?.alpha = 1
            })
        }
        if let vActivityIndicator = vActivityIndicator {
            vActivityIndicator.play(fromProgress: 0, toProgress: 1, loopMode: LottieLoopMode.loop)
        }
    }
    
    func dismiss() {
        guard let vActivityIndicator = vActivityIndicator else {
            return
        }
        vActivityIndicator.stop()
        
        if UIApplication.shared.applicationState == .background {
            removeFromSuperview()
            controlView?.removeFromSuperview()
            return
        }
        
        UIView.animate(withDuration: 0.1, delay: 0, options: .curveEaseInOut, animations: { [weak self] in
            self?.alpha = 0
        }) { [weak self] finished in
            if finished {
                self?.removeFromSuperview()
                self?.controlView?.removeFromSuperview()
            }
        }
    }
    // MARK: - Private Method
    // MARK: - IBAction
}
// MARK: - Delegate
