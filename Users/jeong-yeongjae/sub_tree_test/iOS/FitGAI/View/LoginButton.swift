//
//  LoginButton.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/14.
//

import UIKit

// MARK: - Public Outter Class, Struct, Enum, Protocol

class LoginButton: UIButton {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    // MARK: - IBOutlet
    // MARK: - Private Variable

    // MARK: - Override Method or Basic Method
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.layer.cornerRadius = 10
        self.layer.masksToBounds = true
        self.layer.borderColor = UIColor.lineLightSecondary.cgColor
        self.layer.borderWidth = 1
    }
    
    override func imageRect(forContentRect contentRect: CGRect) -> CGRect {
        let margin = 0
        let contHeight = contentRect.size.height
        let imgSize = contHeight - CGFloat(margin * 2)

        titleLabel?.font = UIFont.typeA18
        return CGRect(x: CGFloat(margin), y: CGFloat(margin), width: imgSize, height: imgSize)
    }
    
    // MARK: - Public Method
    // MARK: - Private Method
    // MARK: - IBAction
}

// MARK: - Delegate

