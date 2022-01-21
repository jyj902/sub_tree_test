//
//  HHError.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/23.
//

import Foundation

enum HHCommonError: Error {
    case general(HHErrorNo)
}
// no0061 까지 사용
enum HHErrorNo: Int {
    case no0001 = 1, no0002, no0003, no0004, no0005, no0006, no0007, no0008, no0009, no0010
    case no0011, no0012, no0013, no0014, no0015, no0016, no0017, no0018, no0019, no0020
    case no0021, no0022, no0023, no0024, no0025, no0026, no0027, no0028, no0029, no0030
    case no0031, no0032, no0033, no0034, no0035, no0036, no0037, no0038, no0039, no0040
    case no0041, no0042, no0043, no0044, no0045, no0046, no0047, no0048, no0049, no0050
    case no0051, no0052, no0053, no0054, no0055, no0056, no0057, no0058, no0059, no0060
    case no0061, no0062, no0063, no0064, no0065, no0066, no0067, no0068, no0069, no0070
    case no0071, no0072, no0073, no0074, no0075, no0076, no0077, no0078, no0079, no0080
    case no0081, no0082, no0083, no0084, no0085, no0086, no0087, no0088, no0089, no0090
    case no0091, no0092, no0093, no0094, no0095, no0096, no0097, no0098, no0099, no0100
    case noDecide
}
