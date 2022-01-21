//
//  UIFont+GUI.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/11/24.
//

import UIKit

// MARK: - Public Outter Class, Struct, Enum, Protocol
enum UIFontSize : Int {
    case unspecified = 0
    case extraSmall = 1
    case small // 2
    case medium // 3
    case large // 4
    case extraLarge // 5
    case extraExtraLarge // 6
    case extraExtraExtraLarge // 7
    case accessibilityMedium // 8
    case accessibilityLarge // 9
    case accessibilityExtraLarge //10
    case accessibilityExtraExtraLarge     //11
    case accessibilityExtraExtraExtraLarge //12
    case max
}

enum UIFontTextStyleInt : Int {
    case largeTitle = 0
    case title1 = 1
    case title2                      // 2
    case title3                      // 3
    case headline                    // 4
    case subheadline                 // 5
    case body                        // 6
    case callout                     // 7
    case footnote                    // 8
    case caption1                    // 9
    case caption2                     //10
    case max
}

public enum FontScalableLevel : Int {
    case level01 = 1
    case level02
    case level03
    case level04
    case level05
    case level06
    case level07
    case level08
    case max
}

public extension UIFont {
    
    // MARK: - Public Variable
    static let isFixedSize = true
    
    //caption2          11
    //caption1          12
    //footnote          13
    //callout           15
    //body              16
    //subheadline       17
    //headline          17
    //title3            20
    //title2            22
    //title1            28
    //largeTitle        34
    // MARK: - define 값
    static var typeA11:UIFont {
        get {
            if UIFont.isFixedSize {
                return UIFont.systemFont(ofSize: 11)
            } else {
                return UIFont.preferredFont(forTextStyle: .caption2)
            }
        }
    }

    static var typeA12:UIFont {
        get {
            if UIFont.isFixedSize {
                return UIFont.systemFont(ofSize: 12)
            } else {
                return UIFont.preferredFont(forTextStyle: .caption1)
            }
        }
    }

    static var typeA13:UIFont {
        get {
            if UIFont.isFixedSize {
                return UIFont.systemFont(ofSize: 13)

            } else {
                return UIFont.preferredFont(forTextStyle: .footnote)
            }
        }
    }

    static var typeA15:UIFont {
        get {
            if UIFont.isFixedSize {
                return UIFont.systemFont(ofSize: 15)

            } else {
                return UIFont.preferredFont(forTextStyle: .callout)
            }
        }
    }

    static var typeA16:UIFont {
        get {
            if UIFont.isFixedSize {
                return UIFont.systemFont(ofSize: 16)

            } else {
                return UIFont.preferredFont(forTextStyle: .body)
            }
        }
    }

    static var typeA17:UIFont {
        get {
            if UIFont.isFixedSize {
                return UIFont.systemFont(ofSize: 17)

            } else {
                return UIFont.preferredFont(forTextStyle: .subheadline)
            }
        }
    }

    static var typeA18:UIFont {
        get {
            if UIFont.isFixedSize {
                return UIFont.systemFont(ofSize: 18)

            } else {
                return UIFont.preferredFont(forTextStyle: .headline)
            }
        }
    }

    static var typeA20:UIFont {
        get {
            if UIFont.isFixedSize {
                return UIFont.systemFont(ofSize: 20)

            } else {
                return UIFont.preferredFont(forTextStyle: .title3)
            }
        }
    }

    static var typeA22:UIFont {
        get {
            if UIFont.isFixedSize {
                return UIFont.systemFont(ofSize: 22)

            } else {
                return UIFont.preferredFont(forTextStyle: .title2)
            }
        }
    }

    static var typeA28:UIFont {
        get {
            if UIFont.isFixedSize {
                return UIFont.systemFont(ofSize: 28)

            } else {
                return UIFont.preferredFont(forTextStyle: .title1)
            }
        }
    }

    static var typeA34:UIFont {
        get {
            if UIFont.isFixedSize {
                return UIFont.systemFont(ofSize: 34)

            } else {
                return UIFont.preferredFont(forTextStyle: .largeTitle)
            }
        }
    }
    
    // MARK: - Public Method
    class func getPreferredContentSizeIntValue() -> Int {
        let preferredContentSize = UIApplication.shared.preferredContentSizeCategory.rawValue
        return UIFont.getContentSizeIntValue(preferredContentSize)
    }

    class func getFontSizeWithTextStyleInt(textStyleInt: Int) -> Int {
        return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt: UIFont.getPreferredContentSizeIntValue())
    }
    
    class func getTextStyleIntValue(_ textStyle: String?) -> Int {
        let textStyleDic:[String:NSNumber] = [
            UIFont.TextStyle.largeTitle.rawValue: NSNumber(value: UIFontTextStyleInt.largeTitle.rawValue),
            UIFont.TextStyle.title1.rawValue: NSNumber(value: UIFontTextStyleInt.title1.rawValue),
            UIFont.TextStyle.title2.rawValue: NSNumber(value: UIFontTextStyleInt.title2.rawValue),
            UIFont.TextStyle.title3.rawValue:          NSNumber(value: UIFontTextStyleInt.title3.rawValue),
            UIFont.TextStyle.headline.rawValue:        NSNumber(value: UIFontTextStyleInt.headline.rawValue),
            UIFont.TextStyle.subheadline.rawValue:     NSNumber(value: UIFontTextStyleInt.subheadline.rawValue),
            UIFont.TextStyle.body.rawValue:            NSNumber(value: UIFontTextStyleInt.body.rawValue),
            UIFont.TextStyle.callout.rawValue:         NSNumber(value: UIFontTextStyleInt.callout.rawValue),
            UIFont.TextStyle.footnote.rawValue:        NSNumber(value: UIFontTextStyleInt.footnote.rawValue),
            UIFont.TextStyle.caption1.rawValue:        NSNumber(value: UIFontTextStyleInt.caption1.rawValue),
            UIFont.TextStyle.caption2.rawValue:        NSNumber(value: UIFontTextStyleInt.caption2.rawValue),
//            "UICTFontTextStyleTitle0":     NSNumber(value: UIFontTextStyleInt.largeTitle.rawValue),
//            "UICTFontTextStyleTitle1":     NSNumber(value: UIFontTextStyleInt.title1.rawValue),
//            "UICTFontTextStyleTitle2":     NSNumber(value: UIFontTextStyleInt.title2.rawValue),
//            "UICTFontTextStyleTitle3":     NSNumber(value: UIFontTextStyleInt.title3.rawValue),
//            "UICTFontTextStyleHeadline":   NSNumber(value: UIFontTextStyleInt.headline.rawValue),
//            "UICTFontTextStyleSubhead":    NSNumber(value: UIFontTextStyleInt.subheadline.rawValue),
//            "UICTFontTextStyleBody":       NSNumber(value: UIFontTextStyleInt.body.rawValue),
//            "UICTFontTextStyleCallout":    NSNumber(value: UIFontTextStyleInt.callout.rawValue),
//            "UICTFontTextStyleFootnote":   NSNumber(value: UIFontTextStyleInt.footnote.rawValue),
//            "UICTFontTextStyleCaption1":   NSNumber(value: UIFontTextStyleInt.caption1.rawValue),
//            "UICTFontTextStyleCaption2":   NSNumber(value: UIFontTextStyleInt.caption2.rawValue),
        ]
        
        let textStyleNumber = textStyleDic[textStyle!] ?? NSNumber(value: UIFontTextStyleInt.callout.rawValue);
        return textStyleNumber.intValue
    }

    class func getFontSizeWithTextStyle(textStyle: String?, contentSize: String?) -> Int {
        let textStyleInt = UIFont.getTextStyleIntValue(textStyle)
        let contentSizeInt = UIFont.getContentSizeIntValue(contentSize)
        return self.getFontSizeWithTextStyleInt(textStyleInt: textStyleInt, contentSizeInt: contentSizeInt)
    }

    class func getFontSizeWithTextStyleInt(textStyleInt: Int, contentSizeInt: Int) -> Int {
        if textStyleInt < 0 || 11 <= textStyleInt || contentSizeInt <= 0 || 13 <= contentSizeInt {
            HHLog.e(.core, .no0001, "ERROR!!! not suitable value !!!!! textStyleInt = \(textStyleInt), contentSizeInt = \(contentSizeInt)")
            return 0
        }
        let fontSizeArray = [
            //      xS  S    M   L  x  xxL xxxL AX1 AX2 AX3 AX4 AX5
            [0, 31, 32, 33, 34, 36, 38, 40, 44, 48, 52, 56, 60], //LargeTitle
            [0, 25, 26, 27, 28, 30, 32, 34, 38, 43, 48, 53, 58], //Title1
            [0, 19, 20, 21, 22, 24, 26, 28, 34, 39, 44, 50, 56], //Title2
            [0, 17, 18, 19, 20, 22, 24, 26, 31, 37, 43, 49, 55], //Title3
            [0, 14, 15, 16, 17, 19, 21, 23, 28, 33, 40, 47, 53], //Headline
            [0, 14, 15, 16, 17, 19, 21, 23, 28, 33, 40, 47, 53], //Subheadline
            [0, 13, 14, 15, 16, 18, 20, 22, 26, 32, 38, 44, 51], //Body
            [0, 12, 13, 14, 15, 17, 19, 21, 25, 30, 36, 42, 49], //Callout
            [0, 12, 12, 12, 13, 15, 17, 19, 23, 27, 33, 38, 44], //Footnote
            [0, 11, 11, 11, 12, 14, 16, 18, 22, 26, 32, 37, 43], //Caption1
            [0, 11, 11, 11, 11, 13, 15, 17, 20, 24, 29, 34, 40], //Caption2
        ];
            return fontSizeArray[textStyleInt][contentSizeInt];
    }

    class func getContentSizeIntValue(_ contentSize: String?) -> Int {
        let contentSizeDic:[String:NSNumber] = [
            UIContentSizeCategory.unspecified.rawValue: NSNumber(value: UIFontSize.unspecified.rawValue),
            UIContentSizeCategory.extraSmall.rawValue: NSNumber(value: UIFontSize.extraSmall.rawValue),
            UIContentSizeCategory.small.rawValue: NSNumber(value: UIFontSize.small.rawValue),
            UIContentSizeCategory.medium.rawValue:                NSNumber(value: UIFontSize.medium.rawValue),
            UIContentSizeCategory.large.rawValue:                 NSNumber(value: UIFontSize.large.rawValue),
            UIContentSizeCategory.extraLarge.rawValue:            NSNumber(value: UIFontSize.extraLarge.rawValue),
            UIContentSizeCategory.extraExtraLarge.rawValue:       NSNumber(value: UIFontSize.extraExtraLarge.rawValue),
            UIContentSizeCategory.extraExtraExtraLarge.rawValue:  NSNumber(value: UIFontSize.extraExtraExtraLarge.rawValue),
            UIContentSizeCategory.accessibilityMedium.rawValue:   NSNumber(value: UIFontSize.accessibilityMedium.rawValue),
            UIContentSizeCategory.accessibilityLarge.rawValue:    NSNumber(value: UIFontSize.accessibilityLarge.rawValue),
            UIContentSizeCategory.accessibilityExtraLarge.rawValue:NSNumber(value: UIFontSize.accessibilityExtraLarge.rawValue),
            UIContentSizeCategory.accessibilityExtraExtraLarge.rawValue:NSNumber(value: UIFontSize.accessibilityExtraExtraLarge.rawValue),
            UIContentSizeCategory.accessibilityExtraExtraExtraLarge.rawValue:NSNumber(value: UIFontSize.accessibilityExtraExtraExtraLarge.rawValue),
       ]
        let contentSizeNumber = contentSizeDic[contentSize!]!
        return contentSizeNumber.intValue
    }
    
    func weight(_ weight: UIFont.Weight) -> UIFont? {
        var symbolicTraits = fontDescriptor.symbolicTraits
        if weight == .bold || weight == .semibold {
            symbolicTraits.insert(.traitBold)
        } else {
            symbolicTraits.remove(.traitBold)
        }
        if let font = fontDescriptor.withSymbolicTraits(symbolicTraits) {
            return UIFont(descriptor: font, size: 0)
        }
        return nil
    }
    
    func getFontSize() -> Int {
        if UIFont.isFixedSize {
            return Int(self.pointSize)
        } else {
            let textStyle = fontDescriptor.fontAttributes[UIFontDescriptor.AttributeName(rawValue: "NSCTFontUIUsageAttribute")] as? String
            return UIFont.getFontSizeWithTextStyle(textStyle:textStyle, contentSize: UIApplication.shared.preferredContentSizeCategory.rawValue)
        }
    }
    
    func getFontSize(with level: FontScalableLevel) -> Int {
        if UIFont.isFixedSize {
            return Int(self.pointSize)
        }
        let textStyle = fontDescriptor.fontAttributes[UIFontDescriptor.AttributeName(rawValue: "NSCTFontUIUsageAttribute")] as? String
        let textStyleInt = UIFont.getTextStyleIntValue(textStyle)
        let contentSizeInt = UIFont.getContentSizeIntValue(UIApplication.shared.preferredContentSizeCategory.rawValue)

        if contentSizeInt <= UIFontSize.large.rawValue {
            return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt: contentSizeInt)
        }

        switch level {
        case FontScalableLevel.level01 /* 8 */:
            return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt: UIFontSize.extraLarge.rawValue)
        case FontScalableLevel.level02: // 4 4
            if(UIFontSize.extraLarge.rawValue <= contentSizeInt && contentSizeInt <= UIFontSize.accessibilityMedium.rawValue) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraLarge.rawValue)
            } else {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraExtraLarge.rawValue)
            }
        case FontScalableLevel.level03: // 3 3 2
            if(UIFontSize.extraLarge.rawValue <= contentSizeInt && contentSizeInt <= UIFontSize.extraExtraExtraLarge.rawValue) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraLarge.rawValue)
            } else if(UIFontSize.accessibilityMedium.rawValue <= contentSizeInt && contentSizeInt <= UIFontSize.accessibilityExtraLarge.rawValue) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraExtraLarge.rawValue)
            } else {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraExtraExtraLarge.rawValue)
            }
        case FontScalableLevel.level04:   // 2 2 2 2
            if(UIFontSize.extraLarge.rawValue <= contentSizeInt && contentSizeInt <= UIFontSize.extraExtraLarge.rawValue) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraLarge.rawValue)
            } else if(UIFontSize.extraExtraExtraLarge.rawValue <= contentSizeInt && contentSizeInt <= UIFontSize.accessibilityMedium.rawValue) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraExtraLarge.rawValue)
            } else if(UIFontSize.accessibilityLarge.rawValue <= contentSizeInt && contentSizeInt <= UIFontSize.accessibilityExtraLarge.rawValue) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraExtraExtraLarge.rawValue)
            } else {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.accessibilityMedium.rawValue)
            }
        case FontScalableLevel.level05:  // 2 2 2 1 1
            if(UIFontSize.extraLarge.rawValue <= contentSizeInt && contentSizeInt <= UIFontSize.extraExtraLarge.rawValue) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraLarge.rawValue)
            } else if(UIFontSize.extraExtraExtraLarge.rawValue <= contentSizeInt && contentSizeInt <= UIFontSize.accessibilityMedium.rawValue) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraExtraLarge.rawValue)
            } else if(UIFontSize.accessibilityLarge.rawValue <= contentSizeInt && contentSizeInt <= UIFontSize.accessibilityExtraLarge.rawValue) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraExtraExtraLarge.rawValue)
            } else if(UIFontSize.accessibilityExtraExtraLarge.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.accessibilityMedium.rawValue)
            } else {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.accessibilityLarge.rawValue)
            }
        case FontScalableLevel.level06:  // 2 2 1 1 1 1
            if(UIFontSize.extraLarge.rawValue <= contentSizeInt && contentSizeInt <= UIFontSize.extraExtraLarge.rawValue) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraLarge.rawValue)
            } else if(UIFontSize.extraExtraExtraLarge.rawValue <= contentSizeInt && contentSizeInt <= UIFontSize.accessibilityMedium.rawValue) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraExtraLarge.rawValue)
            } else if(UIFontSize.accessibilityLarge.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraExtraExtraLarge.rawValue)
            } else if(UIFontSize.accessibilityExtraLarge.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.accessibilityMedium.rawValue)
            } else if(UIFontSize.accessibilityExtraExtraLarge.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.accessibilityLarge.rawValue)
            } else {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.accessibilityExtraLarge.rawValue)
            }
        case FontScalableLevel.level07:  // 2 1 1 1 1 1 1
            if(UIFontSize.extraLarge.rawValue <= contentSizeInt && contentSizeInt <= UIFontSize.extraExtraLarge.rawValue) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraLarge.rawValue)
            } else if(UIFontSize.extraExtraExtraLarge.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraExtraLarge.rawValue)
            } else if(UIFontSize.accessibilityMedium.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraExtraExtraLarge.rawValue)
            } else if(UIFontSize.accessibilityLarge.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.accessibilityMedium.rawValue)
            } else if(UIFontSize.accessibilityExtraLarge.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.accessibilityLarge.rawValue)
            } else if(UIFontSize.accessibilityExtraExtraLarge.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.accessibilityExtraLarge.rawValue)
            } else {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.accessibilityExtraExtraLarge.rawValue)
            }
        case FontScalableLevel.level08:  // 1 1 1 1 1 1 1
            if(UIFontSize.extraLarge.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraLarge.rawValue)
            } else if(UIFontSize.extraExtraLarge.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraExtraLarge.rawValue)
            } else if(UIFontSize.extraExtraExtraLarge.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.extraExtraExtraLarge.rawValue)
            } else if(UIFontSize.accessibilityMedium.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.accessibilityMedium.rawValue)
            } else if(UIFontSize.accessibilityLarge.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.accessibilityLarge.rawValue)
            } else if(UIFontSize.accessibilityExtraLarge.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.accessibilityExtraLarge.rawValue)
            } else if(UIFontSize.accessibilityExtraExtraLarge.rawValue == contentSizeInt) {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.accessibilityExtraExtraLarge.rawValue)
            } else {
                return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt:UIFontSize.accessibilityExtraExtraExtraLarge.rawValue)
            }
        default:
            break
        }
        return UIFont.getFontSizeWithTextStyleInt(textStyleInt:textStyleInt, contentSizeInt: contentSizeInt)
    }
}
