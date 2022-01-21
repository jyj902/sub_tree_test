//
//  RoundButton.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/14.
//

import Foundation
import UIKit

// MARK: - Public Outter Class, Struct, Enum, Protocol
class RoundButton : UIButton {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    // MARK: - IBOutlet
    // MARK: - Private Variable
    private let disableColor = UIColor(hexString: "#80000000")
    private let highlightedColor = UIColor(hexString: "#FF808080")
    private let normalColor = UIColor(hexString: "#FF000000")

    // MARK: - Override Method or Basic Method
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        layer.borderWidth = 1
        layer.borderColor = UIColor(hexString: "#FF000000")?.cgColor
        layer.masksToBounds = true

        setTitleColor(normalColor, for: .normal)
        setTitleColor(highlightedColor, for: .highlighted)
        setTitleColor(highlightedColor, for: .selected)
        setTitleColor(disableColor, for: .disabled)

        setBackgroundImage(UIImage.image(fromColor: "#FFFFFFFF"), for: .normal)
        setBackgroundImage(UIImage.image(fromColor: "#10E5E5E5"), for: .highlighted)
        setBackgroundImage(UIImage.image(fromColor: "#FFFFFFFF"), for: .selected)
        titleLabel?.font = UIFont.typeA18
        setColors()
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        layer.cornerRadius = frame.size.height / 2
    }

    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        super.touchesBegan(touches, with: event)
        setColors()
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        super.touchesMoved(touches, with: event)
        setColors()
    }

    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        super.touchesEnded(touches, with: event)
        setColors()
    }
    
    override func touchesCancelled(_ touches: Set<UITouch>, with event: UIEvent?) {
        super.touchesCancelled(touches, with: event)
        setColors()
    }
    
    // MARK: - Public Method
    // MARK: - Private Method
    private func setColors() {
        if isEnabled {
            if isHighlighted {
                layer.borderColor = highlightedColor?.cgColor
            } else {
                layer.borderColor = normalColor?.cgColor
            }
        } else {
            layer.borderColor = disableColor?.cgColor
        }
    }
    // MARK: - IBAction
}

// MARK: - Delegate

