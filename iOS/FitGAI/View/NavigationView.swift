//
//  NavigationView.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/07.
//

import UIKit
import SnapKit

// MARK: - Public Outter Class, Struct, Enum, Protocol
protocol NavigationViewButtonDelegate:NSObject {
    func onClickBack(_ sender: Any)
}

class NavigationView: UIView {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    class Style {
        static let none = 0
        static let hiddenBackButton = 1 << 0
        static let transparentBackground = 1 << 1
    }
    
    // MARK: - Public Variable
    var style:HHStyle? {
        get {
            return _style
        }
        set {
            _style = newValue
            reloadStyle()
        }
    }

    weak var delegate: NavigationViewButtonDelegate?
    
    // MARK: - IBOutlet
    @IBOutlet weak var btnBack: UIButton!
    @IBOutlet weak var lbTitle: UILabel!
        
    // MARK: - Private Variable
    private var _style:HHStyle?

    // MARK: - Override Method or Basic Method
    override init(frame: CGRect) {
        super.init(frame: frame)
#if !TARGET_INTERFACE_BUILDER
        initializeViews()
        reloadStyle()
#endif
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
#if !TARGET_INTERFACE_BUILDER
        initializeViews()
        reloadStyle()
#endif
    }
    
    // MARK: - Public Method
    func setTitle(title:String) {
        lbTitle.text = title
    }
    
    // MARK: - Private Method
    private func initializeViews() {
        let loadedNibView = Bundle.main.loadNibNamed("NavigationView", owner: self, options: nil)?.first as? UIView
        loadedNibView?.frame = bounds
        loadedNibView?.autoresizingMask = [.flexibleWidth, .flexibleHeight]

        if let loadedNibView = loadedNibView {
            addSubview(loadedNibView)
            loadedNibView.snp.makeConstraints { m in
                m.edges.equalToSuperview()
            }
        }
        lbTitle.font = UIFont.typeA28.weight(.bold)
        
        // 아래 밑줄
        layer.shadowColor = UIColor.black.cgColor
        layer.shadowOffset = CGSize(width: 0, height: 1)
        layer.shadowRadius = 2
        layer.shadowOpacity = 0.2
        layer.masksToBounds = false
    }
    
    private func reloadStyle() {
        if let _style = _style {
            btnBack.isHidden = _style.isInclude(Style.hiddenBackButton)
            
            if _style.isInclude(Style.transparentBackground) {
                backgroundColor = UIColor.clear
                lbTitle.textColor = UIColor.fontLightPrimary
            } else {
                backgroundColor = UIColor.bgLight
                lbTitle.textColor = UIColor.fontDarkPrimary
            }
        }
    }
    
    // MARK: - IBAction
    @IBAction func onClickBack(_ sender: Any) {
        delegate?.onClickBack(sender)
    }
}

// MARK: - Delegate
