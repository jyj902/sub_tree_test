//
//  ExerciseListViewController.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/24.
//

import UIKit
import RxSwift

class ExerciseListViewController: BaseViewController {
    // MARK: - Public Variable
    var id:Int = 0
    var thumbnailPath:String = ""
    var titleText:String = ""
    // MARK: - IBOutlet
    @IBOutlet weak var tbvExercise: UITableView!
    @IBOutlet weak var ivTitle: UIImageView!
    @IBOutlet weak var lbNoExerciseList: UILabel!
    @IBOutlet weak var navigationView: NavigationView!

    // MARK: - Private Variable
    private var exerciseContents:[ExerciseContentModel] = []
    private var disposeBag = DisposeBag()
    private var requestNetworkSubject = PublishSubject<Void>()
    private lazy var healthRepository = HealthRepository(requestNetworkSubject:self.requestNetworkSubject)

    // MARK: - Override Method or Basic Method
    override func viewDidLoad() {
        super.viewDidLoad()

        view.backgroundColor = UIColor.bgLightGray
        tbvExercise.delegate = self
        tbvExercise.dataSource = self
        tbvExercise.backgroundColor = UIColor.bgLightGray
        tbvExercise.register(UINib(nibName: String(describing: TitleDescriptionIconTableViewCell.self), bundle: nil), forCellReuseIdentifier: String(describing: TitleDescriptionIconTableViewCell.self))
        tbvExercise.tableFooterView = UIView.init(frame: .zero)
        ivTitle.kf.setImage(with: URL(string: thumbnailPath))
        lbNoExerciseList.isHidden = true
        navigationView.delegate = self
        navigationView.style = HHStyle(NavigationView.Style.transparentBackground)
        navigationView.lbTitle.text = titleText
        
        ivTitle.snp.updateConstraints { m in
            m.top.equalToSuperview().offset(-Coordinate.safeInsetTop)
        }

        bindUI()
        requestNetworkSubject.onNext(Void())
    }
    
    // MARK: - Private Method
    private func bindUI() {
        requestNetworkSubject.subscribe(onNext: { [weak self] in
            HHLog.t(.network)
            self?.healthRepository.requestExerciseContentList(id:self?.id ?? 0)
                .subscribe{ [weak self] result in
                    switch result {
                    case .success(let exerciseContents):
                        HHLog.d(.temporary, "exerciseContents = \(exerciseContents)")
//                        let realm = try! Realm()
//                        try! realm.write {
//                            realm.deleteAll()
//                        }
//                        for bodyPartCourse in exerciseContents {
//                            try! realm.write {
//                                realm.add(BodyPartCourseDB(bodyPartCourse:bodyPartCourse))
//                            }
//                        }
                        self?.exerciseContents = exerciseContents
                        if exerciseContents.count == 0 {
                            self?.lbNoExerciseList.isHidden = false
                        } else {
                            self?.lbNoExerciseList.isHidden = true
                        }
                        self?.tbvExercise.reloadData()
                    case .failure(let error) :
                        HHLog.d(.temporary, "error = \(error)")
                    }

                }
        }).disposed(by: disposeBag)
    }
}

// MARK: - Delegate
extension ExerciseListViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return UITableView.automaticDimension
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let cell = tableView.cellForRow(at: indexPath) as? TitleDescriptionIconTableViewCell
        cell?.setSelected(true, animated: true, completion: { [weak self] in
            if let exerciseName = self?.exerciseContents[indexPath.row].exerciseName {
                let vc = UIViewController.getViewController(viewControllerEnum: .poseDetector) as! PoseDetectorViewController
                switch exerciseName {
                case TODO("ShoulderPress"):
                    vc.exerciseType = ExerciseType.shoulderPress
                    break
                case TODO("LatPullDownBack"):
                    vc.exerciseType = ExerciseType.latPullDown
                    break
                case TODO("Lunge"):
                    vc.exerciseType = ExerciseType.lunge
                    break
                case TODO("Squat"):
                    vc.exerciseType = ExerciseType.squat
                    break
                case TODO("SideLateralRaise"):
                    vc.exerciseType = ExerciseType.sideLateralRaise
                    break
                case TODO("CablePushDown"):
                    vc.exerciseType = ExerciseType.cablePushDown
                    break
                case TODO("StandingBarbellCurl"):
                    vc.exerciseType = ExerciseType.standingBarbellCurl
                    break
                default:
                    break
                }
                if let exerciseContent = self?.exerciseContents[indexPath.row] {
                    vc.titleText = exerciseContent.title ?? ""
                    vc.videoPath = exerciseContent.videoPath ?? ""
                    vc.id = self?.id ?? 0
                }
                self?.navigationController?.pushViewController(vc, animated: true)
            }

        })
        tableView.deselectRow(at: indexPath, animated: true)
    }
}


extension ExerciseListViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return exerciseContents.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: TitleDescriptionIconTableViewCell
        if let reusableCell = tableView.dequeueReusableCell(withIdentifier: String(describing: TitleDescriptionIconTableViewCell.self), for: indexPath) as? TitleDescriptionIconTableViewCell {
            cell = reusableCell
        } else {
            let objectArray = Bundle.main.loadNibNamed(String(describing: TitleDescriptionIconTableViewCell.self), owner: nil, options: nil)
            cell = objectArray![0] as! TitleDescriptionIconTableViewCell
        }
        let exerciseContent = exerciseContents[indexPath.row]
        cell.lbTitle.text = exerciseContent.title
        cell.lbDescription.text = exerciseContent.description
        cell.lbAI.text = "AI"
        cell.lbAI.isHidden = exerciseContent.isAi
        cell.lbLevel.text = exerciseContent.getDifficultyString()
        cell.lbCalorie.text = "\(exerciseContent.calorie)kcal"
        cell.onClickSetting = { [weak self] in
            HHLog.d(.temporary, "ExerciseList onClickSetting")            
            let vc = UIViewController.getViewController(viewControllerEnum: .exerciseSetting) as! ExerciseSettingViewController
            vc.transitioningDelegate = DialogViewControllerTransitioningDelegate.shared
            vc.modalPresentationStyle = UIModalPresentationStyle.custom
            self?.present(vc, animated: true)
        }
        
        if let thumbnailPath = exerciseContent.thumbnailPath {
            cell.ivThumbnail.kf.setImage(with: URL(string: thumbnailPath))
        }

        return cell
    }
}

extension ExerciseListViewController:NavigationViewButtonDelegate {
    func onClickBack(_ sender: Any) {
        navigationController?.popViewController(animated: true)
    }
}
