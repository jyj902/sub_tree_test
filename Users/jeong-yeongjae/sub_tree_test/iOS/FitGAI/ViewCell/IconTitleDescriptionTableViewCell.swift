//
//  IconTitleDescriptionTableViewCell.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/27.
//

import UIKit

class IconTitleDescriptionTableViewCell: UITableViewCell {
    // MARK: - IBOutlet
    @IBOutlet weak var ivIcon: UIImageView!
    @IBOutlet weak var lbTitle: UILabel!
    @IBOutlet weak var lbDescription: UILabel!
    
    // MARK: - Override Method or Basic Method
    override func awakeFromNib() {
        super.awakeFromNib()
        lbTitle.font = UIFont.typeA17
        lbTitle.textColor = UIColor.fontDarkPrimary
        lbDescription.font = UIFont.typeA15
        lbTitle.textColor = UIColor.fontDarkSecondary
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
}
