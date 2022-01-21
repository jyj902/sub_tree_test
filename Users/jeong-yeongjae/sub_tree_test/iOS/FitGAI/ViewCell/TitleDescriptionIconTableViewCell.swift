//
//  TitleDescriptionIconTableViewCell.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/24.
//

import UIKit

class TitleDescriptionIconTableViewCell: AnimationTableViewCell {
    // MARK: - IBOutlet
    @IBOutlet weak var lbTitle: UILabel!
    @IBOutlet weak var lbDescription: UILabel!
    @IBOutlet weak var ivThumbnail: UIImageView!
    @IBOutlet weak var lbAI: OutlineLabel!
    @IBOutlet weak var lbLevel: OutlineLabel!
    @IBOutlet weak var lbCalorie: OutlineLabel!
    @IBOutlet weak var btnSetting: UIButton!
    
    var onClickSetting:(()->Void)?
    
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

        lbTitle.font = UIFont.typeA20.weight(.bold)
        lbTitle.textColor = UIColor.label
        
        lbDescription.font = UIFont.typeA15
        lbDescription.textColor = UIColor.fontDarkSecondary
        
        ivThumbnail.layer.cornerRadius = 6
        ivThumbnail.layer.masksToBounds = true
        ivThumbnail.contentMode = .scaleToFill
        
        btnSetting.setTitle("", for: .normal)
        
        selectionStyle = UITableViewCell.SelectionStyle.none
        backgroundColor = UIColor.bgLightGray
    }
    
    @IBAction func onClickSetting(_ sender: Any) {
        HHLog.d(.temporary, "onClickSetting")
        if let onClickSetting = onClickSetting {
            onClickSetting()
        }
    }
}
