//
//  HHFatalErrorViewController.swift
//  SwiftSampleCollection
//
//  Created by 김기훈 on 2021/07/30.
//  Copyright © 2021 com.hnh. All rights reserved.
//

import UIKit

// MARK: - Public Method
public func HHFatalError(_ titleText: String, filename: String = #file, line:Int = #line, funcName:String = #function) {
    let th = Thread.current.isMainThread ? "main" : Thread.current.name ?? "-"
    let contentsText = "\(Date()) \(th) \(filename.components(separatedBy: "/").last ?? "") (LINE\(line)) :: \(funcName)\n\n\(Thread.callStackSymbols)"
    HHFatalErrorViewController.show(titleText: titleText, contentsText: contentsText)
}

class HHFatalErrorViewController: UIViewController {
    // MARK: - IBOutlet
    @IBOutlet weak var lbTitle: UILabel!
    @IBOutlet weak var lbContents: UILabel!
    @IBOutlet weak var vBackgroundView: UIView!
    
    // MARK: - Private Variable
    private let backgroundAlpha:CGFloat = 0.4
    private var titleText:String?
    private var contentsText:String?

    // MARK: - Override Method or Basic Method
    override func viewDidLoad() {
        super.viewDidLoad()
        vBackgroundView.backgroundColor = UIColor.bgInactive

        lbTitle.text = titleText
        lbContents.text = contentsText
    }
        
    // MARK: - Private Method
    fileprivate class func show(titleText:String, contentsText:String) {
        HHLog.e(.core, .no0043, "\(titleText) - \(contentsText)")
        let vc = UIViewController.getViewController(viewControllerEnum: .fatalError) as! HHFatalErrorViewController
        vc.modalPresentationStyle = UIModalPresentationStyle.custom;
        vc.titleText = titleText
        vc.contentsText = contentsText
        UIViewController.top().present(vc, animated:false)
    }
}
