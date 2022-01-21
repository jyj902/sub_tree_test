//
//  SSCModel.swift
//  SwiftSampleCollection
//
//  Created by 김기훈 on 2021/06/06.
//  Copyright © 2021 com.hnh. All rights reserved.
//

import UIKit

class Configuration {
    // MARK: - Public Variable
    public static let shared = Configuration()
    let urlHost = "http://hnhinc.co.kr:8080"
    //let urlHost = "http://10.10.0.54:3000"
    
    // MARK: - Override Method or Basic Method
    private init() {
    }
}
