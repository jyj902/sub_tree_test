//
//  BarButtonView.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/07.
//

import UIKit

// MARK: - Public Outter Class, Struct, Enum, Protocol
struct BarButtonItem {
    var tag:Int
    var title:String
}

protocol BarButtonViewDelegate: NSObject {
    func onClickBarButton(_ sender:UIButton)
}

class BarButtonView: UIView {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    
    // MARK: - Public Variable
    weak var delegate:BarButtonViewDelegate?
    
    // MARK: - IBOutlet

    // MARK: - Private Variable
    private var stackView:UIStackView

    // MARK: - Override Method or Basic Method
    override init(frame:CGRect) {
        stackView = UIStackView(frame: CGRect.zero)
        super.init(frame: frame)
        initializeViews()
    }
    
    required init?(coder: NSCoder) {
        stackView = UIStackView(frame: CGRect.zero)
        super.init(coder: coder)
        initializeViews()
    }
            
    // MARK: - Public Method
    func addButtons(barButtonItems:[BarButtonItem]) {
        for barButtonItem in barButtonItems {
            let button = UIButton(frame: CGRect.zero)
            button.setTitle(barButtonItem.title, for: .normal)
            button.tag = barButtonItem.tag
            button.snp.makeConstraints { m in
                m.width.equalTo(button.intrinsicContentSize.width + 20)
            }
            button.setTitleColor(UIColor.fontButtonNormal, for: .normal)
            button.setTitleColor(UIColor.fontButtonHighlight, for: .highlighted)
            button.titleLabel?.font = UIFont.typeA18.weight(.bold)
            button.addTarget(self, action: #selector(onClickBarButton(_:)), for: .touchUpInside)
            stackView.addArrangedSubview(button)
        }
    }
    
    // MARK: - Private Method
    private func initializeViews() {
        addSubview(stackView)

        stackView.axis = NSLayoutConstraint.Axis.horizontal
        stackView.distribution = .fillProportionally
        stackView.alignment = .center
        stackView.spacing = 10.0
                
        stackView.snp.makeConstraints { m in
            m.trailing.equalTo(self).offset(-20)
            m.centerY.equalToSuperview()
        }
    }
    
    // MARK: - IBAction
    @objc func onClickBarButton(_ sender:UIButton) {
        delegate?.onClickBarButton(sender)
    }
}

// MARK: - Delegate
