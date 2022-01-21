import AVFoundation
import CoreVideo
import MLImage
import MLKit
import PMSuperButton
import RxSwift
import Lottie
import UIKit
// MARK: - Public Outter Class, Struct, Enum, Protocol

class PoseDetectorViewController: BaseViewController {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    var id:Int = 0                  // 전신운동, 상체운동, 하체운동, 가슴운동, 어깨운동, 팔운동...
    var exerciseName = ""
    var type = 0
    var exerciseType:ExerciseType = ExerciseType.none  // 숄더프레스, 렛플다운, 런지, 스쿼트...
    var titleText:String = ""
    var videoPath:String = ""

    // MARK: - IBOutlet
    @IBOutlet weak var previewOverlayView: UIImageView!
    @IBOutlet weak var annotationOverlayView: UIView!
    @IBOutlet weak var lbTitle: OutlineLabel!
    @IBOutlet weak var lbInfo: UILabel!
    @IBOutlet weak var btnMovie: PMSuperButton!
    @IBOutlet weak var btnGuide: PMSuperButton!
    @IBOutlet weak var btnCamera: PMSuperButton!
    @IBOutlet weak var btnBack: UIButton!
    @IBOutlet weak var countView: CountView!
    @IBOutlet weak var avLottie: AnimationView!
    
    // MARK: - Private Variable
    private var poseImageAnalyzer:PoseImageAnalyzer?
    private var infoText:String = ""
    private var infoSet: [(String, Bool)] = []
    private var disposeBag = DisposeBag()
    private var csvRepository = CsvRepository(requestNetworkSubject:nil)

    // MARK: - Override Method or Basic Method
    override func viewDidLoad() {
        super.viewDidLoad()
        
        initializeViews()
        initializeVariables()
        bindUI()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        UIApplication.shared.isIdleTimerDisabled = true
        exerciseName = exerciseType.getIdName()
        type = exerciseType.getExerciseType()
        poseImageAnalyzer = PoseImageAnalyzer(exerciseName: exerciseName, exerciseType: type)
        poseImageAnalyzer?.initialize(previewOverlayView: previewOverlayView, annotationOverlayView: annotationOverlayView)
        poseImageAnalyzer?.setUpCaptureSessionOutput()
        poseImageAnalyzer?.setUpCaptureSessionInput()
        
        poseImageAnalyzer?.startSession()
        
        BGMManager.shared.play(bgmEnum: .bgm1)
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        let _ = Timer.scheduledTimer(withTimeInterval: 180, repeats: false) { (Timer) in
            UIApplication.shared.isIdleTimerDisabled = false
        }
        poseImageAnalyzer?.stopSession()
//        ExerciseGlobal.shared.initGlobalObject()
        poseImageAnalyzer = nil
        BGMManager.shared.stop()
    }
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        
        poseImageAnalyzer?.previewLayer.frame = previewOverlayView.frame
    }
    
    // MARK: - Public Method
    // MARK: - Private Method
    private func initializeViews() {
        //previewOverlayView.contentMode = UIView.ContentMode.scaleAspectFit
        previewOverlayView.contentMode = UIView.ContentMode.scaleAspectFill

        lbTitle.text = titleText
        lbTitle.font = UIFont.typeA22
        lbTitle.textColor = UIColor.fontLightPrimary
        lbTitle.backgroundColor = UIColor.bgInactive
        lbTitle.snp.updateConstraints { m in
            m.top.equalToSuperview().offset(Coordinate.safeInsetTop + 15)
        }

        btnMovie.gradientStartColor = UIColor.bgThemePrimary
        btnMovie.gradientEndColor = UIColor.bgThemeSecondary
        btnMovie.rippleColor = UIColor.bgShadow

        btnGuide.gradientStartColor = UIColor.bgThemePrimary
        btnGuide.gradientEndColor = UIColor.bgThemeSecondary
        btnGuide.rippleColor = UIColor.bgShadow

        btnCamera.gradientStartColor = UIColor.bgThemePrimary
        btnCamera.gradientEndColor = UIColor.bgThemeSecondary
        btnCamera.rippleColor = UIColor.bgShadow
        
        btnBack.setTitle("", for: .normal)
        btnBack.snp.updateConstraints { m in
            m.top.equalToSuperview().offset(Coordinate.safeInsetTop + 10)
        }
        
        lbInfo.layer.cornerRadius = 10
        lbInfo.layer.masksToBounds = true
        lbInfo.textColor = UIColor.fontLightPrimary
        lbInfo.backgroundColor = UIColor.bgInactive
    }
    
    private func initializeVariables() {
        supportBackSwipe = false
    }
    
    private func bindUI() {
//        ExerciseGlobal.shared.rx
//            .observe(Int.self, "counting", options: [.new])
//            .distinctUntilChanged()
//            .observe(on: MainScheduler.instance)
//            .subscribe(onNext: { [weak self] result in
//                HHLog.d(.poseDetect, "result = \(String(describing: result))")
//                self?.countView.countNumber = ExerciseGlobal.shared.counting
//                self?.countView.setNeedsDisplay()
//            }, onError: { error in
//                HHLog.e(.poseDetect, .no0057, "error = \(error)")
//            }, onCompleted: {
//                HHLog.d(.poseDetect, "completed")
//            })
//            .disposed(by: disposeBag)
//
//        ExerciseGlobal.shared.rx
//            .observe(Int.self, "initExerciseReps", options: [.new])
//            .distinctUntilChanged()
//            .observe(on: MainScheduler.instance)
//            .subscribe(onNext: { [weak self] result in
//                HHLog.d(.poseDetect, "result = \(String(describing: result))")
//                self?.countView.totalNumber = ExerciseGlobal.shared.initExerciseReps
//                self?.countView.setNeedsDisplay()
//            }, onError: { error in
//                HHLog.e(.poseDetect, .no0058, "error = \(error)")
//            }, onCompleted: {
//                HHLog.d(.poseDetect, "completed")
//            })
//            .disposed(by: disposeBag)
        GlobalSubject.shared.countSubject
            .observe(on: MainScheduler.instance)
            .subscribe(onNext: { [weak self] result in
                HHLog.d(.poseDetect, "countSubject result = \(result)")
                self?.countView.countNumber = result.0
                self?.countView.totalNumber = result.1
                self?.countView.setNeedsDisplay()
            })
            .disposed(by: disposeBag)

        GlobalSubject.shared.lottieSubject
            .observe(on: MainScheduler.instance)
            .subscribe(onNext: { [weak self] result in
                HHLog.d(.poseDetect, "lottieSubject result = \(result)")
                self?.play(lottie:result)
            })
            .disposed(by: disposeBag)
        
        GlobalSubject.shared.csvSubject
            .observe(on: MainScheduler.instance)
            .flatMap { [weak self] result -> Observable<Void> in
                self?.csvRepository.uploadCsv(fileName: result.0, setNum:result.1).asObservable() ?? Observable.just(Void())
            }
            .subscribe(onNext: { result in
                HHLog.d(.network, "success")
            }, onError: { error in
                HHLog.e(.network, .no0059, "error = \(error)")
            })
            .disposed(by: disposeBag)
    }
    
    private func play(lottie:LottieEnum) {
        let animation:Animation?
        switch lottie {
        case .wait_10Sec:
            animation = Animation.named("timer_ten")
        case .congratulation:
            animation = Animation.named("haha_congratulation")
        case .none:
            animation = Animation.named("none")
        }
        avLottie.isHidden = false
        avLottie.animation = animation
        avLottie.contentMode = .scaleAspectFit
        avLottie.play(fromProgress: 0, toProgress: 1, loopMode: LottieLoopMode.playOnce, completion: { [weak self] finished in
            self?.avLottie.isHidden = true
            if lottie == .congratulation {
                self?.navigationController?.popViewController(animated: true)
            }
        })
    }
    
    // MARK: - IBAction
    @IBAction func onClickPlayMovie(_ sender: Any) {
        let vc = UIViewController.getViewController(viewControllerEnum: .moviePlayer) as! MoviePlayerViewController
        vc.videoUrl = URL(string: videoPath)
        vc.titleText = titleText
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    @IBAction func onClickChangeGuide(_ sender: Any) {
        //PoseDetectorViewController.buttonSet = !PoseDetectorViewController.buttonSet
    }
    
    @IBAction func onClickCamera(_ sender: Any) {
        if let poseImageAnalyzer = poseImageAnalyzer {
            GlobalVariable.shared.isUsingFrontCamera = !GlobalVariable.shared.isUsingFrontCamera
            poseImageAnalyzer.removeDetectionAnnotations()
            poseImageAnalyzer.setUpCaptureSessionInput()
        }
    }
    @IBAction func onClickBack(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
}
