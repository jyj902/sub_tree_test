//
//  CheckBoxView.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/14.
//

import UIKit

class CheckBoxView: UIView {
    // MARK: - Public Variable
    var status:Bool {
        get {
            _status
        }
        
        set {
            _status = newValue
            if _status {
                ivCheck?.image = UIImage(named: "\(onText)16.png")
            } else {
                ivCheck?.image = UIImage(named: "\(offText)16.png")
            }
        }
    }
    // MARK: - IBOutlet
    // MARK: - Private Variable
    private let onText = "checkbox_on_"
    private let offText = "checkbox_off_"

    private var ivCheck: UIImageView?
    private var lbText: UILabel?
    private var touchControlView: TouchControlView?
    private var onImages: [UIImage]?
    private var offImages: [UIImage]?
    private var _status = false

    // MARK: - Override Method or Basic Method
    override init(frame: CGRect) {
        super.init(frame: frame)
        initializeViews()
        initializeVariables()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        initializeViews()
        initializeVariables()
    }
    
    // MARK: - Public Method
    func setLabelText(text:String) {
        lbText?.text = text
    }
    
    // MARK: - Private Method
    func initializeViews() {
        ivCheck = UIImageView(frame: CGRect.zero)
        lbText = UILabel(frame: CGRect.zero)
        touchControlView = TouchControlView(frame: CGRect.zero)
        if let ivCheck = ivCheck,
           let lbText = lbText,
           let touchControlView = touchControlView {
            ivCheck.animationRepeatCount = 1
            ivCheck.animationDuration = 0.25
            addSubview(ivCheck)
        
            lbText.font = UIFont.typeA16
            lbText.numberOfLines = 2
            lbText.textColor = UIColor.fontDarkPrimary
            addSubview(lbText)

            touchControlView.delegate = self
            addSubview(touchControlView)
        
            ivCheck.snp.makeConstraints { m in
                m.width.equalTo(30)
                m.height.equalTo(30)
                m.leading.equalTo(snp_leadingMargin).offset(2)
                m.centerY.equalTo(self)
            }
            lbText.snp.makeConstraints { m in
                m.top.equalToSuperview()
                m.bottom.equalToSuperview()
                m.leading.equalTo(ivCheck.snp_trailingMargin).offset(8)
                m.trailing.equalToSuperview().offset(-10)
                m.centerY.equalToSuperview()
            }
            touchControlView.snp.makeConstraints { m in
                m.leading.equalTo(ivCheck.snp_leadingMargin)
                m.trailing.equalTo(lbText.snp_trailingMargin)
                m.top.equalToSuperview()
                m.bottom.equalToSuperview()
            }
        }
    }
    
    func initializeVariables() {
        onImages = [UIImage]()
        offImages = [UIImage]()
        for i in 1...16 {
            if let image = UIImage(named: String(format: "\(onText)%02d", i)) {
                onImages?.append(image)
            }
            if let image = UIImage(named: String(format: "\(offText)%02d", i)) {
                offImages?.append(image)
            }
        }
        status = false
    }
    
    func animateImages(withStatus status: Bool) {
        ivCheck?.stopAnimating()
        if self.status != status {
            if status {
                ivCheck?.animationImages = onImages
                ivCheck?.startAnimating()
                ivCheck?.image = UIImage(named: "\(onText)16.png")
                self.status = true
            } else {
                ivCheck?.animationImages = offImages
                ivCheck?.startAnimating()
                ivCheck?.image = UIImage(named: "\(offText)16.png")
                self.status = false
            }
        }
    }
}

extension CheckBoxView: TouchControlDelegate {
    func handleTap() {
        HHLog.d(.view, "button clicked")
        if status {
            animateImages(withStatus: false)
        } else {
            animateImages(withStatus: true)
        }
    }
}
