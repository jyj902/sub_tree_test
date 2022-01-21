//
//  UIColor+GUI.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/11/24.
//

import UIKit

public extension UIColor {
    
    // MARK: - Public Variable
    static let shadowOpacity:CGFloat  = 0.18
    static let inactiveAlpha:CGFloat  = 0.50
    
    // MARK: - define 값
    static let fontDefault                          = UIColor.label
    static let fontDarkPrimary                      = UIColor(rgb: 0x505050)
    static let fontDarkSecondary                    = UIColor(rgb: 0x707070)
    static let fontDarkThird                        = UIColor(rgb: 0x909090)

    static let fontLightPrimary                     = UIColor(rgb: 0xF0F0F0)
    static let fontLightSecondary                   = UIColor(rgb: 0xC0C0C0)
    static let fontLightThird                       = UIColor(rgb: 0xA0A0A0)
    
    static let fontButtonNormal                     = UIColor(rgb: 0x104B75)
    static let fontButtonHighlight                  = UIColor(rgb: 0x6090B0)

    static let bgTheme                              = UIColor(rgb: 0x6BB9ED)
    static let bgLight                              = UIColor(rgb: 0xFFFFFF)
    static let bgLightGray                          = UIColor(rgb: 0xF0F0F0)
    static let bgDark                               = UIColor(rgb: 0x000000)
    static let bgInactive                           = UIColor.black.withAlphaComponent(UIColor.inactiveAlpha)
    static let bgShadow                             = UIColor(rgb: 0x000000)

    static let bgGoogleLogin                        = UIColor(rgb: 0xFFFFFF)
    static let bgKakaoLogin                         = UIColor(rgb: 0xFFE500)
    static let bgNaverLogin                         = UIColor(rgb: 0x02C75B)
    static let bgAppleLogin                         = UIColor(rgb: 0x000000)
    static let bgDeveloperLogin                     = UIColor(rgb: 0x6EC0FF)
    static let bgSampleLogin                        = UIColor(rgb: 0xF26191)

    static let bgChartExercise                      = UIColor(rgb: 0x24A0FF)
    static let bgChartRest                          = UIColor(rgb: 0x45C48B)
    static let bgChartSuccess                       = UIColor(rgb: 0x24A0FF)
    static let bgChartFail                          = UIColor(rgb: 0xFC1E1E)

    static let bgThemePrimary                       = UIColor(rgb: 0x0281FF)
    static let bgThemeSecondary                     = UIColor(rgb: 0x91CFFF)
    static let bgThemeThird                         = UIColor(rgb: 0xA0F0FF)

    static let lineLightPrimary                     = UIColor(rgb: 0xF0F0F0)
    static let lineLightSecondary                   = UIColor(rgb: 0xD0D0D0)
    static let lineLightThird                       = UIColor(rgb: 0xB0B0B0)
    static let lineDarkPrimary                      = UIColor(rgb: 0x101010)
    static let lineDarkSecondary                    = UIColor(rgb: 0x404040)
    static let lineDarkThird                        = UIColor(rgb: 0x606060)

    
    // MARK: - Override Method or Basic Method
    convenience init(red: Int, green: Int, blue: Int, a: CGFloat = 1.0) {
        self.init(
            red: CGFloat(red) / 255.0,
            green: CGFloat(green) / 255.0,
            blue: CGFloat(blue) / 255.0,
            alpha: a
        )
    }

    convenience init(rgb: Int, a: CGFloat = 1.0) {
        self.init(
            red: (rgb >> 16) & 0xFF,
            green: (rgb >> 8) & 0xFF,
            blue: rgb & 0xFF,
            a: a
        )
    }

    convenience init(rgb: Int) {
        self.init(
            red: (rgb >> 16) & 0xFF,
            green: (rgb >> 8) & 0xFF,
            blue: rgb & 0xFF,
            a: 1.0
        )
    }
    
    convenience init?(hexString: String?) {
        let input: String! = (hexString ?? "")
            .replacingOccurrences(of: "#", with: "")
            .uppercased()
        var alpha: CGFloat = 1.0
        var red: CGFloat = 0
        var blue: CGFloat = 0
        var green: CGFloat = 0
        switch (input.count) {
        case 3 /* #RGB */:
            red = Self.colorComponent(from: input, start: 0, length: 1)
            green = Self.colorComponent(from: input, start: 1, length: 1)
            blue = Self.colorComponent(from: input, start: 2, length: 1)
            break
        case 4 /* #ARGB */:
            alpha = Self.colorComponent(from: input, start: 0, length: 1)
            red = Self.colorComponent(from: input, start: 1, length: 1)
            green = Self.colorComponent(from: input, start: 2, length: 1)
            blue = Self.colorComponent(from: input, start: 3, length: 1)
            break
        case 6 /* #RRGGBB */:
            red = Self.colorComponent(from: input, start: 0, length: 2)
            green = Self.colorComponent(from: input, start: 2, length: 2)
            blue = Self.colorComponent(from: input, start: 4, length: 2)
            break
        case 8 /* #AARRGGBB */:
            alpha = Self.colorComponent(from: input, start: 0, length: 2)
            red = Self.colorComponent(from: input, start: 2, length: 2)
            green = Self.colorComponent(from: input, start: 4, length: 2)
            blue = Self.colorComponent(from: input, start: 6, length: 2)
            break
        default:
            NSException.raise(NSExceptionName("Invalid color value"), format: "Color value \"%@\" is invalid.  It should be a hex value of the form #RBG, #ARGB, #RRGGBB, or #AARRGGBB", arguments:getVaList([hexString ?? ""]))
        }
        self.init(red: red, green: green, blue: blue, alpha: alpha)
    }
    
    // MARK: - Private Method
    private static func colorComponent(from string: String!, start: Int, length: Int) -> CGFloat {
        let substring = (string as NSString)
            .substring(with: NSRange(location: start, length: length))
        let fullHex = length == 2 ? substring : "\(substring)\(substring)"
        var hexComponent: UInt64 = 0
        Scanner(string: fullHex)
            .scanHexInt64(&hexComponent)
        return CGFloat(Double(hexComponent) / 255.0)
    }
}
