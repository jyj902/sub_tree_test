//
//  AnimationTableViewCell.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/24.
//

import UIKit

class AnimationTableViewCell: UITableViewCell {
    
    // MARK: - Public Method
    func setSelected(_ selected: Bool, animated flag: Bool, completion: (() -> Void)? = nil) {
        if !flag {
            completion?()
            return
        }
        UIView.animate(
            withDuration: 0.15,
            animations: { [weak self] in
                self?.transform = CGAffineTransform.identity.scaledBy(x: 0.95, y: 0.95)
            }) { [weak self] finished in
                UIView.animate(
                    withDuration: 0.15,
                    animations: { [weak self] in
                        self?.transform = CGAffineTransform.identity.scaledBy(x: 1.0, y: 1.0)
                    }) { finished in
                        completion?()
                }
            }
    }
}
