//
//  OutlineLabel.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/29.
//

import UIKit

// MARK: - Public Outter Class, Struct, Enum, Protocol
class OutlineLabel: UILabel {
    
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    // MARK: - IBOutlet
    // MARK: - Private Variable
    private var padding = UIEdgeInsets(top: 4.0, left: 6.0, bottom: 4.0, right: 6.0)

    // MARK: - Override Method or Basic Method
    convenience init(padding: UIEdgeInsets) {
        self.init(padding: padding)
        self.padding = padding
    }
    
    override required init?(coder: NSCoder) {
        super.init(coder: coder)
        layer.cornerRadius = 5
        layer.borderColor = UIColor.lineLightSecondary.cgColor
        layer.borderWidth = 1
        layer.masksToBounds = true
    }

    override func drawText(in rect: CGRect) {
        super.drawText(in: rect.inset(by: padding))
    }

    override var intrinsicContentSize: CGSize {
        var contentSize = super.intrinsicContentSize
        contentSize.height += padding.top + padding.bottom
        contentSize.width += padding.left + padding.right

        return contentSize
    }
    
    // MARK: - Public Method
    // MARK: - Private Method
    // MARK: - IBAction
}

// MARK: - Delegate
