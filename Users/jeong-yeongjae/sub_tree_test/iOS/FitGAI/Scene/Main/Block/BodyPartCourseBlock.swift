//
//  BodyPartCourseBlock.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/20.
//

import UIKit
import Kingfisher

class BodyPartCourseBlock: UITableViewCell {
    @IBOutlet weak var lbTitle: UILabel!
    @IBOutlet weak var cvBodyPartCourse: UICollectionView!

    var bodyPartCourses:[BodyPartCourseModel] = []
    
    override func awakeFromNib() {
        super.awakeFromNib()
        selectionStyle = UITableViewCell.SelectionStyle.none
        lbTitle.text = TODO("운동하기")
        lbTitle.font = UIFont.typeA28.weight(.bold)
        lbTitle.textColor = UIColor.fontDarkPrimary
        
        cvBodyPartCourse.register(UINib(nibName:"IconTitleCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "IconTitleCollectionViewCell")
        
        let collectionViewFlowLayout = UICollectionViewFlowLayout().then { layout in
            layout.scrollDirection = UICollectionView.ScrollDirection.horizontal
            layout.minimumLineSpacing = 0
        }

        cvBodyPartCourse.backgroundColor = UIColor.clear
        cvBodyPartCourse.bounces = false
        cvBodyPartCourse.showsHorizontalScrollIndicator = false
        cvBodyPartCourse.delegate = self
        cvBodyPartCourse.dataSource = self
        cvBodyPartCourse.collectionViewLayout = collectionViewFlowLayout
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
}

extension BodyPartCourseBlock: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: 140, height: 140)
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 10.0
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        return UIEdgeInsets(top: 0, left: 10, bottom: 0, right: 10)
    }
}

extension BodyPartCourseBlock: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        bodyPartCourses.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell: IconTitleCollectionViewCell
        if let reusableCell = collectionView.dequeueReusableCell(withReuseIdentifier: String(describing: IconTitleCollectionViewCell.self), for: indexPath) as? IconTitleCollectionViewCell {
            cell = reusableCell
        } else {
            let objectArray = Bundle.main.loadNibNamed(String(describing: IconTitleCollectionViewCell.self), owner: nil, options: nil)
            cell = objectArray![0] as! IconTitleCollectionViewCell
        }
        let bodyPartCourse = bodyPartCourses[indexPath.row]
        cell.lbTitle.text = bodyPartCourse.bodyPartName
        if let thumbnailPath = bodyPartCourse.thumbnailPath {
            cell.ivIcon.kf.setImage(with: URL(string: thumbnailPath))
        }
        return cell
    }
}

extension BodyPartCourseBlock: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        HHLog.d(.temporary, "indexPath = \(indexPath)")
        let cell = collectionView.cellForItem(at: indexPath) as? IconTitleCollectionViewCell

        cell?.setSelected(true, animated: true, completion: { [weak self] in
            let vc = UIViewController.getViewController(viewControllerEnum: .exerciseList) as! ExerciseListViewController
            let bodyPartCourse = self?.bodyPartCourses[indexPath.row]
            vc.id = bodyPartCourse?.id ?? 0
            if let thumbnailPath = bodyPartCourse?.thumbnailPath {
                vc.thumbnailPath = thumbnailPath
            }
            if let bodyPartName = bodyPartCourse?.bodyPartName {
                vc.titleText = bodyPartName
            }
            if let viewController = self?.viewContainingController() as? HomeViewController {
                viewController.navigationController?.pushViewController(vc, animated: true)
            }
        })
    }
}
