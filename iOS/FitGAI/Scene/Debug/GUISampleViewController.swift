//
//  GUISampleViewController.swift
//  FitGAI
//
//  Created by 김기훈 on 2022/01/14.
//

import UIKit

class GUISampleViewController: SampleBaseViewController {

    @IBOutlet weak var chSetMode:CheckBoxView!
    @IBOutlet weak var chFreeMode:CheckBoxView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        chSetMode.setLabelText(text: "셋트모드")
        chSetMode.backgroundColor = UIColor.bgLight
        chFreeMode.setLabelText(text: "자유모드")
        chFreeMode.backgroundColor = UIColor.bgLight
    }
}
