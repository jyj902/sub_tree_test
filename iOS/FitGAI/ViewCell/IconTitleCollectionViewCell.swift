//
//  IconTitleCollectionViewCell.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/20.
//

import UIKit

class IconTitleCollectionViewCell: AnimationCollectionViewCell {
    // MARK: - IBOutlet
    @IBOutlet weak var ivIcon: UIImageView!
    @IBOutlet weak var lbTitle: UILabel!
    
    // MARK: - Override Method or Basic Method
    override func awakeFromNib() {
        super.awakeFromNib()
        ivIcon.layer.cornerRadius = 7
        ivIcon.layer.masksToBounds = true
        
        lbTitle.font = UIFont.typeA18
        lbTitle.textColor = UIColor.fontDarkPrimary
    }
}
