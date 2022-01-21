//
//  WebBrowserViewController.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/21.
//

import UIKit
import WebKit
// MARK: - Public Outter Class, Struct, Enum, Protocol


class WebBrowserViewController: BaseViewController {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    var url:URL?
    // MARK: - IBOutlet
    @IBOutlet weak var wvMain: WKWebView!
    // MARK: - Private Variable

    // MARK: - Override Method or Basic Method
    override func viewDidLoad() {
        super.viewDidLoad()

        let request = URLRequest(url: url!)
        //self.webView?.allowsBackForwardNavigationGestures = true  //뒤로가기 제스쳐 허용
        wvMain.configuration.preferences.javaScriptEnabled = true  //자바스크립트 활성화
        wvMain.load(request)
        wvMain.uiDelegate = self
        wvMain.navigationDelegate = self
    }
    // MARK: - Public Method
    // MARK: - Private Method
    // MARK: - IBAction
}

// MARK: - Delegate
extension WebBrowserViewController: WKNavigationDelegate {
    
}

extension WebBrowserViewController: WKUIDelegate {
    //alert 처리
    func webView(_ webView: WKWebView, runJavaScriptAlertPanelWithMessage message: String, initiatedByFrame frame: WKFrameInfo, completionHandler: @escaping () -> Void){
        let alertController = UIAlertController(title: "", message: message, preferredStyle: .alert)
        alertController.addAction(UIAlertAction(title: "확인", style: .default, handler: { (action) in
            completionHandler()
        }))
        self.present(alertController, animated: true)
    }
    
    //confirm 처리
    func webView(_ webView: WKWebView, runJavaScriptConfirmPanelWithMessage message: String, initiatedByFrame frame: WKFrameInfo, completionHandler: @escaping (Bool) -> Void) {
        let alertController = UIAlertController(title: "", message: message, preferredStyle: .alert)
        alertController.addAction(UIAlertAction(title: "취소", style: .default, handler: { (action) in
            completionHandler(false)
        }))
        alertController.addAction(UIAlertAction(title: "확인", style: .default, handler: { (action) in
            completionHandler(true)
        }))
        self.present(alertController, animated: true)
    }
    
    // href="_blank" 처리
    func webView(_ webView: WKWebView, createWebViewWith configuration: WKWebViewConfiguration, for navigationAction: WKNavigationAction, windowFeatures: WKWindowFeatures) -> WKWebView? {
        if navigationAction.targetFrame == nil {
            webView.load(navigationAction.request)
        }
        return nil
    }
}


