//
//  LegacyExerciseListViewController.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/08.
//

import UIKit

class LegacyExerciseListViewController: BaseViewController {
    @IBOutlet weak var tbvExerciseList: UITableView!
    @IBOutlet weak var vHeader: MainHeaderView!
    
    private var drawerViewController: DrawerViewController?
    private var drawerPanGestureRecognizer:UIPanGestureRecognizer?
    private var drawerOpenGestureRecognizer:UIScreenEdgePanGestureRecognizer?

    var exerciseList:[ExerciseType] = ExerciseType.allCases
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tbvExerciseList.delegate = self
        tbvExerciseList.dataSource = self
        tbvExerciseList.backgroundColor = UIColor.bgLightGray
        tbvExerciseList.register(UINib(nibName: "RoundTableViewCell", bundle: nil), forCellReuseIdentifier: RoundTableViewCell.ID)
        tbvExerciseList.tableFooterView = UIView(frame:CGRect.zero)
        tbvExerciseList.estimatedRowHeight = 140
        tbvExerciseList.rowHeight = UITableView.automaticDimension
        
        vHeader.mainHeaderViewDelegate = self
        vHeader.backgroundColor = UIColor.bgTheme
        view.backgroundColor = UIColor.bgTheme

        if let drawer = UIViewController.getViewController(viewControllerEnum: .drawer) as? DrawerViewController {
            addChild(drawer)
            drawer.didMove(toParent: self)
            view.addSubview(drawer.view)

            drawer.view.snp.makeConstraints {
                $0.top.bottom.equalToSuperview()
                $0.trailing.equalTo(self.view.snp.leading)
                $0.width.equalToSuperview()
            }
            drawer.collapse(animated:false)
            drawerViewController = drawer
        }
        setupGesture()
    }
    
    
    private func setupGesture() {
        drawerOpenGestureRecognizer = UIScreenEdgePanGestureRecognizer(target: self, action: #selector(touchMove(_:)))
        drawerOpenGestureRecognizer?.edges = UIRectEdge.left;
        
        drawerPanGestureRecognizer = UIPanGestureRecognizer(target: self, action: #selector(touchMove(_:)))
        drawerPanGestureRecognizer?.minimumNumberOfTouches = 1
        drawerPanGestureRecognizer?.maximumNumberOfTouches = 1

        view.addGestureRecognizer(drawerOpenGestureRecognizer!)
        drawerViewController?.view.addGestureRecognizer(drawerPanGestureRecognizer!)
    }
}

extension LegacyExerciseListViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let cell = tableView.cellForRow(at: indexPath) as? RoundTableViewCell
        cell?.setSelected(true, animated: true, completion: { [weak self] in
            let vc = UIViewController.getViewController(viewControllerEnum: .poseDetector) as! PoseDetectorViewController
            self?.navigationController?.pushViewController(vc, animated: true)
            vc.exerciseType = ExerciseType(rawValue:indexPath.row) ?? ExerciseType.none
        })
        tableView.deselectRow(at: indexPath, animated: true)
    }
}

extension LegacyExerciseListViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 70
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        exerciseList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: RoundTableViewCell
        if let reusableCell = tableView.dequeueReusableCell(withIdentifier: "RoundTableViewCell", for: indexPath) as? RoundTableViewCell {
            cell = reusableCell
        } else {
            let objectArray = Bundle.main.loadNibNamed("RoundTableViewCell", owner: nil, options: nil)
            cell = objectArray![0] as! RoundTableViewCell
        }
        cell.lbTitle.text = exerciseList[indexPath.row].getLongName()
        return cell
    }
}

extension LegacyExerciseListViewController: MainHeaderViewDelegate {
    func onClickDrawerMenu() {
        self.drawerViewController?.expand(animated:true)
    }
}

/// pangesture
extension LegacyExerciseListViewController {
    @objc func touchMove(_ sender: UIPanGestureRecognizer?) {
        //drawer dragging
        drawerViewController?.touchMove(sender)
    }
}
