//
//  HHDialogViewController.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/14.
//

import UIKit

// MARK: - Public Method
public func HHDialog(style:HHStyle?, titleText: String?, contentsText: String?, okText: String?, okHandler: (() -> Void)? = nil, cancelText: String? = nil, cancelHandler: (() -> Void)? = nil) {
    HHDialogViewController.show(style:style, titleText: titleText, contentsText: contentsText, okText: okText, okHandler: okHandler, cancelText: cancelText, cancelHandler: cancelHandler)
}

// MARK: - Public Outter Class, Struct, Enum, Protocol
class HHDialogStyle {
    static let none = 0
    static let dismissOnTouchBackground = 1 << 0
}

class HHDialogViewController: UIViewController {
    // MARK: - Public Variable
    typealias OkHandler = () -> Void
    typealias CancelHandler = () -> Void

    static let timeoutInterval:Double = 30

    // MARK: - IBOutlet
    @IBOutlet weak var lbTitle: UILabel!
    @IBOutlet weak var lbContents: UILabel!
    @IBOutlet weak var vBackground: UIView!
    @IBOutlet weak var vDialog: UIView!
    @IBOutlet weak var btnOk: UIButton!
    @IBOutlet weak var btnCancel: UIButton!
    
    // MARK: - Private Variable
    private var style:HHStyle?
    private var titleText:String?
    private var contentsText:String?
    private var okText:String?
    private var okHandler:OkHandler?
    private var cancelText:String?
    private var cancelHandler:CancelHandler?

    // MARK: - Override Method or Basic Method
    override func viewDidLoad() {
        super.viewDidLoad()
        
        initializeViews()
    }
    
    // MARK: - Private Method
    fileprivate class func show(style:HHStyle?, titleText: String?, contentsText: String?, okText: String?, okHandler: OkHandler? = nil, cancelText: String? = nil, cancelHandler: CancelHandler? = nil) {
        let vc = HHDialogViewController.create(style:style, titleText: titleText, contentsText: contentsText, okText: okText, okHandler: okHandler, cancelText: cancelText, cancelHandler: cancelHandler)
        UIViewController.top().present(vc, animated: true)
    }
    
    
    private class func create(style:HHStyle?, titleText: String?, contentsText: String?, okText: String?, okHandler: OkHandler? = nil, cancelText: String? = nil, cancelHandler: CancelHandler? = nil) -> HHDialogViewController {
        let vc = UIViewController.getViewController(viewControllerEnum: .dialog) as! HHDialogViewController
        vc.transitioningDelegate = DialogViewControllerTransitioningDelegate.shared
        vc.modalPresentationStyle = UIModalPresentationStyle.custom
        
        vc.style = style
        vc.titleText = titleText
        vc.contentsText = contentsText
        vc.okText = okText
        vc.okHandler = okHandler
        vc.cancelText = cancelText
        vc.cancelHandler = cancelHandler

        return vc
    }
    
    private func initializeViews() {
        vBackground.backgroundColor = UIColor.bgInactive
        vBackground.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(dismissDialog)))

        vDialog.layer.cornerRadius = 20
        vDialog.layer.masksToBounds = false
        vDialog.layer.shadowColor = UIColor.black.cgColor
        vDialog.layer.shadowOffset = CGSize(width: 0, height: 5)
        vDialog.layer.shadowRadius = 2.0
        vDialog.layer.shadowOpacity = Float(UIColor.shadowOpacity)
        
        lbTitle.text = titleText
        lbTitle.font = UIFont.typeA22.weight(.bold)
        lbTitle.textColor = UIColor.fontDarkPrimary
        
        lbContents.text = contentsText
        lbContents.font = UIFont.typeA17
        lbContents.textColor = UIColor.fontDarkSecondary
        
        btnOk.setTitle(okText, for: .normal)
        btnOk.setTitleColor(UIColor.fontButtonNormal, for: .normal)
        btnOk.setTitleColor(UIColor.fontButtonHighlight, for: .highlighted)
        btnOk.titleLabel?.font = UIFont.typeA18.weight(.bold)
        btnCancel.setTitle(cancelText, for: .normal)
        btnCancel.setTitleColor(UIColor.fontButtonNormal, for: .normal)
        btnCancel.setTitleColor(UIColor.fontButtonHighlight, for: .highlighted)
        btnCancel.titleLabel?.font = UIFont.typeA18.weight(.bold)
    }
    
    // MARK: - IBAction
    @IBAction func onClickOk(_ sender: Any) {
        dismiss(animated: true)
        okHandler?()
    }
    
    @IBAction func onClickCancel(_ sender: Any) {
        dismiss(animated: true)
        cancelHandler?()
    }
    
    @objc func dismissDialog() {
        if style?.isInclude(HHDialogStyle.dismissOnTouchBackground) ?? false {
            dismiss(animated: true)
        }
    }
}
