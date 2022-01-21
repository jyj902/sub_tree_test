//
//  CountView.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/27.
//

import UIKit

// MARK: - Public Outter Class, Struct, Enum, Protocol

class CountView: UIView {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    var countNumber : Int
    var totalNumber : Int
    
    // MARK: - IBOutlet
    // MARK: - Private Variable

    // MARK: - Override Method or Basic Method
    override init(frame: CGRect) {
        countNumber = 0
        totalNumber = 0
        super.init(frame: frame)
    }
    
    required init?(coder: NSCoder) {
        countNumber = 0
        totalNumber = 0
        super.init(coder: coder)
    }
    
    override func draw(_ rect: CGRect) {
        let context = UIGraphicsGetCurrentContext()
        let color = UIColor.bgInactive.cgColor
        context?.setFillColor(color)
        let rectangle = CGRect(x: 0, y: 0, width: bounds.width, height: bounds.height)
        context?.addEllipse(in: rectangle)
        context?.fillPath()
        
        let paragraphStyle = NSMutableParagraphStyle()
        let font = UIFont.typeA34
        paragraphStyle.alignment = .center
        paragraphStyle.alignment = .center
        let attrs = [
            NSAttributedString.Key.font: font,
            NSAttributedString.Key.paragraphStyle: paragraphStyle,
            NSAttributedString.Key.foregroundColor: UIColor.fontLightPrimary]
        HHLog.d(.view, "countNumber = \(countNumber), totalNumber = \(totalNumber)")
        let scaleString = "\(countNumber)/\(totalNumber)"
        let fontHeight = font.getFontSize()
        let stringRect = CGRect(x: 0, y: (bounds.height - CGFloat(fontHeight))/2 , width: bounds.width, height: CGFloat(fontHeight))
        scaleString.draw(in: stringRect, withAttributes: attrs)
    }

    // MARK: - Public Method
    // MARK: - Private Method
    // MARK: - IBAction
}

// MARK: - Delegate
