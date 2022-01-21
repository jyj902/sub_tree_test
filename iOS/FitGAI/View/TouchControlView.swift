//
//  TouchControlView.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/14.
//

import UIKit
// MARK: - Public Outter Class, Struct, Enum, Protocol
protocol TouchControlDelegate: NSObjectProtocol {
    func handleTap()
}

class TouchControlView: UIView {

    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    weak var delegate: TouchControlDelegate?
    // MARK: - IBOutlet
    // MARK: - Private Variable
    var tapGesture: UITapGestureRecognizer?

    // MARK: - Override Method or Basic Method
    override init(frame: CGRect) {
        super.init(frame: frame)
        initializeViews()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        initializeViews()
    }
    
    deinit {
        if let tapGesture = tapGesture {
            removeGestureRecognizer(tapGesture)
        }
    }
    // MARK: - Public Method
    // MARK: - Private Method
    func initializeViews() {
        tapGesture = UITapGestureRecognizer(target: self, action: #selector(handleTap(_:)))
        if let tapGesture = tapGesture {
            addGestureRecognizer(tapGesture)
        }
    }
    // MARK: - IBAction
    @objc func handleTap(_ sendor: UITapGestureRecognizer?) {
        delegate?.handleTap()
    }
}
// MARK: - Delegate

