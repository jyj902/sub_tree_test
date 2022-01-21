//
//  AnalyzeBlock.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/20.
//

import UIKit
import PNChart
import PMSuperButton

class AnalyzeBlock: UITableViewCell {
    @IBOutlet weak var lbTitle: UILabel!
    @IBOutlet weak var btnGotoExerciseAnalyze: PMSuperButton!
    @IBOutlet weak var vExerciseRest: UIView!
    @IBOutlet weak var lbExerciseRest: UILabel!
    @IBOutlet weak var vSuccessFail: UIView!
    @IBOutlet weak var lbSuccessFail: UILabel!
    var pcExerciseRest: PNPieChart?
    var pcSuccessFail: PNPieChart?
    var exerciseRests = [
        PNPieChartDataItem(value: 130, color: UIColor.bgChartExercise, description: TODO("운동")),
        PNPieChartDataItem(value: 80, color: UIColor.bgChartRest, description: TODO("휴식")),
    ]
    var successFails = [
        PNPieChartDataItem(value:78, color: UIColor.bgChartSuccess, description: TODO("성공")),
        PNPieChartDataItem(value:21, color: UIColor.bgChartFail, description: TODO("실패")),
    ]

    override func awakeFromNib() {
        super.awakeFromNib()
        selectionStyle = UITableViewCell.SelectionStyle.none
        lbTitle.text = TODO("운동 분석")
        lbTitle.font = UIFont.typeA28.weight(.bold)
        lbTitle.textColor = UIColor.fontDarkPrimary
        
        btnGotoExerciseAnalyze.gradientStartColor = UIColor.bgThemePrimary
        btnGotoExerciseAnalyze.gradientEndColor = UIColor.bgThemeSecondary
        btnGotoExerciseAnalyze.rippleColor = UIColor.bgShadow

        pcExerciseRest = PNPieChart(frame: vExerciseRest.frame, items: exerciseRests)
        if let pcExerciseRest = pcExerciseRest {
            pcExerciseRest.descriptionTextColor = UIColor.fontDarkPrimary
            pcExerciseRest.descriptionTextFont = UIFont.typeA12
            pcExerciseRest.descriptionTextShadowColor = UIColor.clear
            pcExerciseRest.showAbsoluteValues = true
            pcExerciseRest.showOnlyValues = false
            pcExerciseRest.stroke()

            pcExerciseRest.legendStyle = PNLegendItemStyle.stacked

            vExerciseRest.addSubview(pcExerciseRest)
            pcExerciseRest.snp.makeConstraints { m in
                m.centerX.equalToSuperview()
                m.centerY.equalToSuperview()
                m.height.equalTo(vExerciseRest.frame.size.height)
                m.width.equalTo(vExerciseRest.frame.size.height)
            }
        }
        
        lbExerciseRest.text = TODO("운동/휴식")
        lbExerciseRest.font = UIFont.typeA12
        
        pcSuccessFail = PNPieChart(frame: vSuccessFail.frame, items: successFails)
        if let pcSuccessFail = pcSuccessFail {
            pcSuccessFail.descriptionTextColor = UIColor.fontDarkPrimary
            pcSuccessFail.descriptionTextFont = UIFont.typeA12
            pcSuccessFail.descriptionTextShadowColor = UIColor.clear
            pcSuccessFail.showAbsoluteValues = true
            pcSuccessFail.showOnlyValues = false
            pcSuccessFail.stroke()

            pcSuccessFail.legendStyle = PNLegendItemStyle.stacked

            vSuccessFail.addSubview(pcSuccessFail)
            pcSuccessFail.snp.makeConstraints { m in
                m.centerX.equalToSuperview()
                m.centerY.equalToSuperview()
                m.height.equalTo(vSuccessFail.frame.size.height)
                m.width.equalTo(vSuccessFail.frame.size.height)
            }
        }
        lbSuccessFail.text = TODO("성공/실패")
        lbSuccessFail.font = UIFont.typeA12
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    @IBAction func onClickGotoExerciseAnalysis(_ sender: Any) {
        let vc = UIViewController.getViewController(viewControllerEnum: .webBrowser) as! WebBrowserViewController
        //전체 보기 할때
        //vc.url = URL(string: "http://hnh.ai:8080/pose/skeleton/upload/view")
        vc.url = URL(string: "http://hnhinc.co.kr:8080/record/personal?userId=\(AccountManager.shared.id!)")
    
        if let viewController = viewContainingController() as? HomeViewController {
            viewController.navigationController?.pushViewController(vc, animated: true)
        }
    }
}
