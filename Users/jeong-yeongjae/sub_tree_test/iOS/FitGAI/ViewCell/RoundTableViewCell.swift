//
//  RoundTableViewCell.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/08.
//

import UIKit

class RoundTableViewCell: AnimationTableViewCell {
    // MARK: - Public Variable
    static let ID = "RoundTableViewCell"
    // MARK: - IBOutlet
    @IBOutlet weak var lbTitle: UILabel!

    // MARK: - Override Method or Basic Method
    override func awakeFromNib() {
        super.awakeFromNib()
        initializeViews()
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        contentView.frame = contentView.frame.inset(by: UIEdgeInsets(top: 10, left: 10, bottom: 10, right: 10))
    }
    
    // MARK: - Private Method
    private func initializeViews() {
        contentView.layer.cornerRadius = 12
        contentView.backgroundColor = UIColor.systemBackground
        
        contentView.layer.borderColor = UIColor.lineLightSecondary.cgColor
        contentView.layer.borderWidth = 1

        selectionStyle = UITableViewCell.SelectionStyle.none
        backgroundColor = UIColor.bgLightGray
    }
}
