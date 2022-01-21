//
//  ViewController.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/10.
//

import UIKit
// MARK: - Public Outter Class, Struct, Enum, Protocol
fileprivate enum ButtonId:Int {
    case cancel = 0
    case ok     = 1
}

class ExerciseSettingViewController: UIViewController {

    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    // MARK: - IBOutlet
    @IBOutlet weak var vBackground: UIView!
    @IBOutlet weak var vDialog: UIView!
    @IBOutlet weak var lbTitle: UILabel!
    @IBOutlet weak var lbMode: UILabel!
    @IBOutlet weak var lbSetCount: UILabel!
    @IBOutlet weak var lbExerciseCount: UILabel!
    @IBOutlet weak var lbRestTime: UILabel!
    @IBOutlet weak var scMode: UISegmentedControl!
    @IBOutlet weak var scSetCount: UISegmentedControl!
    @IBOutlet weak var scExerciseCount: UISegmentedControl!
    @IBOutlet weak var scRestTime: UISegmentedControl!
    @IBOutlet weak var btnInit: UIButton!
    @IBOutlet weak var barButtonView: BarButtonView!


    // MARK: - Private Variable
    
    // MARK: - Override Method or Basic Method
    override func viewDidLoad() {
        super.viewDidLoad()
        initializeViews()
    }
    
    // MARK: - Public Method
    // MARK: - Private Method

    func initializeViews() {
        vBackground.backgroundColor = UIColor.bgInactive
        vBackground.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(dismissDialog)))
        
        vDialog.layer.cornerRadius = 20
        vDialog.layer.masksToBounds = false
        vDialog.layer.shadowColor = UIColor.black.cgColor
        vDialog.layer.shadowOffset = CGSize(width: 0, height: 5)
        vDialog.layer.shadowRadius = 2.0
        vDialog.layer.shadowOpacity = Float(UIColor.shadowOpacity)
        
        lbTitle.text = TODO("운동 구성을 설정해 보세요.")
        lbTitle.font = UIFont.typeA22.weight(.bold)
        lbTitle.textColor = UIColor.fontDarkPrimary

        lbMode.text = TODO("운동 모드")
        lbMode.font = UIFont.typeA17
        lbMode.textColor = UIColor.fontDarkPrimary
        
        scMode.removeAllSegments()
        scMode.insertSegment(withTitle: TODO("세트모드"), at: 0, animated: false)
        scMode.insertSegment(withTitle: TODO("자유모드"), at: 1, animated: false)
        scMode.selectedSegmentIndex = 0

        lbSetCount.text = TODO("운동 세트수")
        lbSetCount.font = UIFont.typeA17
        lbSetCount.textColor = UIColor.fontDarkPrimary
        
        scSetCount.removeAllSegments()
        scSetCount.insertSegment(withTitle: TODO("1세트"), at: 0, animated: false)
        scSetCount.insertSegment(withTitle: TODO("2세트"), at: 1, animated: false)
        scSetCount.insertSegment(withTitle: TODO("3세트"), at: 2, animated: false)
        scSetCount.insertSegment(withTitle: TODO("4세트"), at: 3, animated: false)
        scSetCount.insertSegment(withTitle: TODO("5세트"), at: 4, animated: false)
        scSetCount.selectedSegmentIndex = 2

        lbExerciseCount.text = TODO("운동 세트별 운동 횟수")
        lbExerciseCount.font = UIFont.typeA17
        lbExerciseCount.textColor = UIColor.fontDarkPrimary
        
        scExerciseCount.removeAllSegments()
        scExerciseCount.insertSegment(withTitle: TODO("10번"), at: 0, animated: false)
        scExerciseCount.insertSegment(withTitle: TODO("12번"), at: 1, animated: false)
        scExerciseCount.insertSegment(withTitle: TODO("15번"), at: 2, animated: false)
        scExerciseCount.insertSegment(withTitle: TODO("18번"), at: 3, animated: false)
        scExerciseCount.insertSegment(withTitle: TODO("20번"), at: 4, animated: false)
        scExerciseCount.selectedSegmentIndex = 2

        lbRestTime.text = TODO("휴식 시간 (초)")
        lbRestTime.font = UIFont.typeA17
        lbRestTime.textColor = UIColor.fontDarkPrimary

        scRestTime.removeAllSegments()
        scRestTime.insertSegment(withTitle: TODO("20초"), at: 0, animated: false)
        scRestTime.insertSegment(withTitle: TODO("30초"), at: 1, animated: false)
        scRestTime.insertSegment(withTitle: TODO("40초"), at: 2, animated: false)
        scRestTime.selectedSegmentIndex = 2

        btnInit.setTitle(TODO("초기화"), for:.normal)
        btnInit.setImage(R.image.settings(), for: .normal)
        btnInit.setTitleColor(UIColor.fontButtonNormal, for: .normal)
        btnInit.setTitleColor(UIColor.fontButtonHighlight , for: .highlighted)
        btnInit.tintColor = UIColor.fontButtonNormal

        barButtonView.addButtons(barButtonItems: [
            BarButtonItem(tag: ButtonId.cancel.rawValue, title: TODO("취소")),
            BarButtonItem(tag: ButtonId.ok.rawValue, title: TODO("저장")),
        ])
        barButtonView.backgroundColor = .clear
        barButtonView.delegate = self
    }
    
    // MARK: - IBAction
    @objc func dismissDialog() {
        dismiss(animated: true)
    }
}
// MARK: - Delegate
extension ExerciseSettingViewController: BarButtonViewDelegate {
    func onClickBarButton(_ sender: UIButton) {
        switch sender.tag {
        case ButtonId.ok.rawValue:
            HHLog.d(.core, "ok scMode = \(scMode.selectedSegmentIndex)")
            
            // 값 저장
            dismiss(animated: true)
        case ButtonId.cancel.rawValue:
            HHLog.d(.core, "cancel")
            dismiss(animated: true)
        default:
            HHLog.e(.core, .no0060, "ERROR")
        }
    }
}
