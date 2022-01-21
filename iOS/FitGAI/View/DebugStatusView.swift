//
//  DebugStatusView.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/11/25.
//

import UIKit
import RxSwift
import RxCocoa

// MARK: - Public Outter Class, Struct, Enum, Protocol

class DebugStatusView: UIView {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    // MARK: - IBOutlet
    // MARK: - Private Variable
    private var stackView: UIStackView
    private var label: UILabel
    private var countLabelArray: [UILabel] = []
    private var disposeBag = DisposeBag()

    // MARK: - Override Method or Basic Method
    override init(frame: CGRect) {
        stackView = UIStackView(frame: CGRect.zero)
        label = UILabel(frame: CGRect.zero)

        super.init(frame: frame)
        initializeViews()
        initializeVariables()
    }
    
    required init?(coder: NSCoder) {
        stackView = UIStackView(frame: CGRect.zero)
        label = UILabel(frame: CGRect.zero)

        super.init(coder: coder)
        initializeViews()
        initializeVariables()
    }
    
    // MARK: - Public Method
    
    // MARK: - Private Method
    private func initializeViews() {
        isHidden = false
        isUserInteractionEnabled = false
        backgroundColor = UIColor.clear
        layer.borderColor = UIColor.red.cgColor
        layer.borderWidth = 1
        
        stackView.axis = NSLayoutConstraint.Axis.vertical
        stackView.distribution = .fillProportionally
        stackView.alignment = .center
        stackView.spacing = 10.0
        
        label.text = "label"
        label.textColor = .red
        label.numberOfLines = 0
        
        self.addSubview(stackView)
        
        stackView.addArrangedSubview(label)
        
        stackView.snp.makeConstraints { m in
            m.edges.equalToSuperview()
        }
    }
    
    private func initializeVariables() {
        NotificationCenter.default.rx.notification(Notification.Name(NotificationConst.debugNotificationAllocUpdated.rawValue))
            .subscribe { [weak self] emit in
                //HHLog.d(.view, "emit = \(emit)")
                self?.updateAllocCount()
            }
            .disposed(by: disposeBag)
    }

    private func updateAllocCount() {
        var text:String = ""
        for item in DebugVariable.shared.viewAllocCountDic {
            text += "\(item.key) : \(item.value)\n"
        }
        text = String(text.dropLast(1))
        label.text = text
    }
    // MARK: - IBAction
}

// MARK: - Delegate
