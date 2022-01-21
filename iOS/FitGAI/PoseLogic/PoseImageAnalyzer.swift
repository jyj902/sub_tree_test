//
//  PoseImageAnalyzer.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/10.
//
import AVFoundation
import CoreVideo
import MLImage
import MLKit
import PoseEngine

//enum ExerciseState {
//    case stop
//    case start
//    case rest
//}

// MARK: - Constants
private enum Constant {
    static let videoDataOutputQueueLabel = "com.google.mlkit.visiondetector.VideoDataOutputQueue"
    static let sessionQueueLabel = "com.google.mlkit.visiondetector.SessionQueue"
    static let smallDotRadius: CGFloat = 4.0
    static let lineWidth: CGFloat = 3.0
}

class PoseImageAnalyzer : NSObject {

    var previewLayer: AVCaptureVideoPreviewLayer!
    private var previewOverlayView: UIImageView?
    private var annotationOverlayView: UIView?
    private var captureSession = AVCaptureSession()
    private lazy var sessionQueue = DispatchQueue(label: Constant.sessionQueueLabel)
    private var lastFrame: CMSampleBuffer?
    
    private var poseDetector:PoseDetector
    
    private var count = 0
    private var fileCount = 0
    private var startTime:Double

    var isStart:Bool
    var exerciseState: Int32
    var poseLogic: PoseLogic? = nil
    var poseInfoScreen: PoseInfoScreen
    var exerciseGlobal:ExerciseGlobal
    var osal:OSAL
    var exerciseName = ""
    var exerciseType = 0
//    var id:Int = 0                  // 전신운동, 상체운동, 하체운동, 가슴운동, 어깨운동, 팔운동...
//    var exerciseType:ExerciseType?  // 숄더프레스, 렛플다운, 런지, 스쿼트...
    var isRecordingCsv: Bool
    var csvTimeTable: Int64
    var exercise : String = ""
    var classifier : PoseClassifierProcessor
    func initialize(previewOverlayView:UIImageView, annotationOverlayView:UIView) {
        self.previewOverlayView = previewOverlayView
        self.annotationOverlayView = annotationOverlayView
    }
        
    init(exerciseName:String, exerciseType:Int) {
//        self.id = id
        self.exerciseName = exerciseName
        self.exerciseType = exerciseType
        previewLayer = AVCaptureVideoPreviewLayer(session: captureSession)
        poseDetector = PoseDetector.poseDetector(options: AccuratePoseDetectorOptions())
        startTime = TimeInterval(CACurrentMediaTime())
        
        isStart = false

        exerciseState = EXERCISE_INFORMATION.STATE_EXERCISE_STOP
        self.isRecordingCsv = false
        self.csvTimeTable = Int64(Date().timeIntervalSince1970 * 1000)
        osal = IOSAL()
        exerciseGlobal = ExerciseGlobal(osal:osal)
        poseInfoScreen = IOSPoseInfoScreen()
        poseLogic = PoseLogic(osal:osal, exerciseGlobal:exerciseGlobal, infoScreen:poseInfoScreen)
        classifier = PoseClassifierProcessor(true, (exerciseName, exerciseType))
        super.init()
    }
    
    // MARK: On-Device Detections
    private func detectPose(in image: MLImage, width: CGFloat, height: CGFloat) {
        var poses: [Pose]
        var iOSPose : IOSPose
        var graphicFlag = true
        do {
            poses = try poseDetector.results(in: image)
            if !poses.isEmpty{
                iOSPose = IOSPose(poses[0])
            } else {
                iOSPose = IOSPose()
                graphicFlag = false
            }
        } catch let error {
            HHLog.e(.poseDetect, .no0017, "Failed to detect poses with error: \(error.localizedDescription).")
            updatePreviewOverlayViewWithLastFrame()
            return
        }
        updatePreviewOverlayViewWithLastFrame()
        if poses.isEmpty {
#if false
            HHLog.d(.poseDetect, "Pose detector returned no results.")
#endif
        }

        //printLog(poses)
        // 여기서 분석을 하시면 됩니다.
        DispatchQueue.main.async { [weak self] in
            guard let self = self else {
                HHLog.e(.poseDetect, .noDecide, "self is nil")
                return
            }
            
            guard let poseLogic = self.poseLogic else {
                HHLog.e(.poseDetect, .no0018, "poseLogic is nil")
                return
            }
            
            do{
                 if poseLogic.getStateProcess() == EXERCISE_INFORMATION.STATE_REST {
                     poseLogic.getViewRestTime(exerciseState: self.exerciseState )
                }
            } catch {
                return
            }
            //Graphic Overlay Setting
            if graphicFlag == true {
                let isStart = self.exerciseGlobal.isStart
                let poseOverlayView = SkeletonView(frame: self.previewOverlayView!.bounds ?? CGRect.init())
                poseOverlayView.iOSPose = iOSPose
                poseOverlayView.previewResolution = CGSize(width:height ,height:width)
                poseOverlayView.initializeVariables()
                poseOverlayView.backgroundColor = UIColor.init(rgb: 0, a: 0)
                self.annotationOverlayView!.addSubview(poseOverlayView)
                poseOverlayView.isFront = GlobalVariable.shared.isUsingFrontCamera
                poseOverlayView.exerciseType = self.exerciseType ?? 0
                poseOverlayView.isStart = isStart
            }

            var convertPose : [PoseLandmarkPoseEngine] = []
            for body in 0 ... 32{
                let position3D = PoseEngine.PointF3D.init(x: Float(iOSPose.poseLandmark.landmark(body).position.x),
                                               y: Float(iOSPose.poseLandmark.landmark(body).position.y),
                                               z: Float(iOSPose.poseLandmark.landmark(body).position.z))
                let position = PointF.init(x: Float(iOSPose.poseLandmark.landmark(body).position.x),
                                           y: Float(iOSPose.poseLandmark.landmark(body).position.y))
                let inFrameLikelihood = iOSPose.poseLandmark.landmark(body).inFrameLikelihood
                convertPose.append(PoseLandmarkPoseEngine(landmarkType: Int32(body),
                                                          position3D: position3D,
                                                          position: position,
                                                          inFrameLikelihood: inFrameLikelihood))
            }
            let hhPose = HHPose(landmarkPoseEngines: convertPose)
            //classifier Porting
            HHLog.d(.poseDetect, "before getPoseResult")
            self.classifier.getPoseResult(pose: iOSPose.poseLandmark)
            HHLog.d(.poseDetect, "after getPoseResult")
            self.exerciseGlobal.poseState = PoseClassifierProcessor.poseState
            self.exerciseGlobal.repsBefore = Int32(PoseClassifierProcessor.repsBefore)
            self.exerciseGlobal.repsAfter = Int32(PoseClassifierProcessor.repsAfter)
            HHLog.d(.poseDetect, "before Logic.run")
            poseLogic.run(hhPose: hhPose, exercise: self.exercise)
            HHLog.d(.poseDetect, "after Logic.run")
        }
    }

    func printLogLandmarks(_ poses:[Pose]) {
        let landmarks = poses[0].landmarks
        var strLog = ""
        for item in landmarks {
            strLog += "\(item.type) = (\(item.position))\n"
            HHLog.d(.poseDetect, "\(item.type) = (\(item.position))\n")
        }
        let fileName = String(format:"landmarks_%.3d",fileCount)
        do {
            var path = FileManager.default.urls(for: .applicationSupportDirectory, in: .userDomainMask).map(\.path).first ?? ""
            path += "/debug"
            try FileUtil.writeString(toFile: strLog, fileName: fileName, path: path)
        } catch {
            HHLog.d(.poseDetect, "fail saveFile")
        }
        fileCount += 1
    }
    
    // MARK: - Private
    func setUpCaptureSessionOutput() {
        weak var weakSelf = self
        sessionQueue.async {
            guard let strongSelf = weakSelf else {
                HHLog.e(.poseDetect, .no0019, "Self is nil!")
                return
            }
            strongSelf.captureSession.beginConfiguration()
            // When performing latency tests to determine ideal capture settings,
            // run the app in 'release' mode to get accurate performance metrics
            strongSelf.captureSession.sessionPreset = AVCaptureSession.Preset.high
            
            let output = AVCaptureVideoDataOutput()
            output.videoSettings = [
                (kCVPixelBufferPixelFormatTypeKey as String): kCVPixelFormatType_32BGRA
            ]
            output.alwaysDiscardsLateVideoFrames = true
            let outputQueue = DispatchQueue(label: Constant.videoDataOutputQueueLabel)
            output.setSampleBufferDelegate(strongSelf, queue: outputQueue)
            guard strongSelf.captureSession.canAddOutput(output) else {
                HHLog.e(.poseDetect, .no0020, "Failed to add capture session output.")
                return
            }
            strongSelf.captureSession.addOutput(output)
            strongSelf.captureSession.commitConfiguration()
        }
    }
    
    func setUpCaptureSessionInput() {
        weak var weakSelf = self
        sessionQueue.async {
            guard let strongSelf = weakSelf else {
                HHLog.e(.poseDetect, .no0021, "Self is nil!")
                return
            }
            let cameraPosition: AVCaptureDevice.Position = GlobalVariable.shared.isUsingFrontCamera ? .front : .back
            guard let device = strongSelf.captureDevice(forPosition: cameraPosition) else {
                HHLog.e(.poseDetect, .no0022, "Failed to get capture device for camera position: \(cameraPosition)")
                return
            }
            do {
                strongSelf.captureSession.beginConfiguration()
                let currentInputs = strongSelf.captureSession.inputs
                for input in currentInputs {
                    strongSelf.captureSession.removeInput(input)
                }
                
                let input = try AVCaptureDeviceInput(device: device)
                guard strongSelf.captureSession.canAddInput(input) else {
                    HHLog.e(.poseDetect, .no0023, "Failed to add capture session input.")
                    return
                }
                strongSelf.captureSession.addInput(input)
                strongSelf.captureSession.commitConfiguration()
            } catch {
                HHLog.e(.poseDetect, .no0024, "Failed to create capture device input: \(error.localizedDescription)")
            }
        }
    }
    
    func startSession() {
        weak var weakSelf = self
        sessionQueue.async {
            guard let strongSelf = weakSelf else {
                HHLog.e(.poseDetect, .no0025, "Self is nil!")
                return
            }
            strongSelf.captureSession.startRunning()
        }
    }
    
    func stopSession() {
        weak var weakSelf = self
        sessionQueue.async {
            guard let strongSelf = weakSelf else {
                HHLog.e(.poseDetect, .no0026, "Self is nil!")
                return
            }
            strongSelf.captureSession.stopRunning()
        }
    }
    
    private func captureDevice(forPosition position: AVCaptureDevice.Position) -> AVCaptureDevice? {
        if #available(iOS 10.0, *) {
            let discoverySession = AVCaptureDevice.DiscoverySession(
                deviceTypes: [.builtInWideAngleCamera],
                mediaType: .video,
                position: .unspecified
            )
            return discoverySession.devices.first { $0.position == position }
        }
        return nil
    }
    
    func removeDetectionAnnotations() {
        for annotationView in annotationOverlayView!.subviews {
            annotationView.removeFromSuperview()
        }
    }
    
    private func updatePreviewOverlayViewWithLastFrame() {
        weak var weakSelf = self
        DispatchQueue.main.sync {
            guard let strongSelf = weakSelf else {
                HHLog.e(.poseDetect, .no0027, "Self is nil!")
                return
            }
            
            guard let lastFrame = lastFrame,
                  let imageBuffer = CMSampleBufferGetImageBuffer(lastFrame)
            else {
                return
            }
            strongSelf.updatePreviewOverlayViewWithImageBuffer(imageBuffer)
            strongSelf.removeDetectionAnnotations()
        }
    }
    
    private func updatePreviewOverlayViewWithImageBuffer(_ imageBuffer: CVImageBuffer?) {
        guard let imageBuffer = imageBuffer else {
            return
        }
        let orientation: UIImage.Orientation = GlobalVariable.shared.isUsingFrontCamera ? .leftMirrored : .right
        let image = UIUtilities.createUIImage(from: imageBuffer, orientation: orientation)
        previewOverlayView!.image = image
    }
    
    private func convertedPoints(from points: [NSValue]?, width: CGFloat, height: CGFloat) -> [NSValue]? {
        return points?.map { [weak self] in
            let cgPointValue = $0.cgPointValue
            let normalizedPoint = CGPoint(x: cgPointValue.x / width, y: cgPointValue.y / height)
            let cgPoint = self?.previewLayer.layerPointConverted(fromCaptureDevicePoint: normalizedPoint) ?? CGPoint(x: 0, y: 0)
            let value = NSValue(cgPoint: cgPoint)
            return value
        }
    }
    
    private func normalizedPoint(fromVisionPoint point: VisionPoint, width: CGFloat, height: CGFloat) -> CGPoint {
        let cgPoint = CGPoint(x: point.x, y: point.y)
        var normalizedPoint = CGPoint(x: cgPoint.x / width, y: cgPoint.y / height)
        normalizedPoint = previewLayer.layerPointConverted(fromCaptureDevicePoint: normalizedPoint)
        return normalizedPoint
    }
    
    private func rotate(_ view: UIView, orientation: UIImage.Orientation) {
        var degree: CGFloat = 0.0
        switch orientation {
        case .up, .upMirrored:
            degree = 90.0
        case .rightMirrored, .left:
            degree = 180.0
        case .down, .downMirrored:
            degree = 270.0
        case .leftMirrored, .right:
            degree = 0.0
        }
        view.transform = CGAffineTransform.init(rotationAngle: degree * 3.141592654 / 180)
    }
}


// MARK: AVCaptureVideoDataOutputSampleBufferDelegate

extension PoseImageAnalyzer: AVCaptureVideoDataOutputSampleBufferDelegate {
    
    func captureOutput(_ output: AVCaptureOutput, didOutput sampleBuffer: CMSampleBuffer, from connection: AVCaptureConnection) {
        guard let imageBuffer = sampleBuffer.imageBuffer else {
            HHLog.e(.poseDetect, .no0028, "Failed to get image buffer from sample buffer.")
            return
        }
        let sampleWidth = CGFloat(CVPixelBufferGetWidth(imageBuffer))
        let sampleHeight = CGFloat(CVPixelBufferGetHeight(imageBuffer))
                
        // 샘플이 90도 뒤집혀 있어서 아래와 같이 비교함.
        let coordiateRatio = Coordinate.width/Coordinate.height
        let sampleRatio = sampleHeight/sampleWidth
        let croppedSampleBuffer:CMSampleBuffer?
        if coordiateRatio == sampleRatio {
            // crop 할 필요 없음.
            croppedSampleBuffer = sampleBuffer
        } else if coordiateRatio > sampleRatio {
            // 잘 동작하는지 확인 못함.
            let croppedSampleWidth = sampleHeight * coordiateRatio
            croppedSampleBuffer = ImageUtilities.croppedSampleBuffer(sampleBuffer, with:CGRect(x:Int((sampleWidth - croppedSampleWidth) / 2), y:0, width: Int(croppedSampleWidth), height: Int(sampleHeight)))
        } else {
            // 잘 동작함
            let croppedSampleHeight = sampleWidth * coordiateRatio
            croppedSampleBuffer = ImageUtilities.croppedSampleBuffer(sampleBuffer, with:CGRect(x:0, y:Int((sampleHeight - croppedSampleHeight) / 2), width: Int(sampleWidth), height:Int(croppedSampleHeight)))
            //croppedSampleBuffer = ImageUtilities.croppedSampleBuffer(sampleBuffer, with:CGRect(x:0, y:96, width: 1920, height:887))
        }

        guard let croppedSampleBuffer = croppedSampleBuffer else {
            HHLog.e(.poseDetect, .no0029, "Failed to get image buffer from sample buffer.")
            return
        }
        guard let imageBuffer = CMSampleBufferGetImageBuffer(croppedSampleBuffer) else {
            HHLog.e(.poseDetect, .no0030, "Failed to get image buffer from sample buffer.")
            return
        }
#if true
        HHLog.d(.poseDetect, "time : \(CACurrentMediaTime() - startTime), frame count = \(count)")
        count += 1
#endif
        
        lastFrame = croppedSampleBuffer
        let visionImage = VisionImage(buffer: croppedSampleBuffer)
        let orientation = UIUtilities.imageOrientation(fromDevicePosition: GlobalVariable.shared.isUsingFrontCamera ? .front : .back)
        visionImage.orientation = orientation
        
        guard let inputImage = MLImage(sampleBuffer: croppedSampleBuffer) else {
            HHLog.e(.poseDetect, .no0031, "Failed to create MLImage from sample buffer.")
            return
        }
        inputImage.orientation = orientation
        
        let imageWidth = CGFloat(CVPixelBufferGetWidth(imageBuffer))
        let imageHeight = CGFloat(CVPixelBufferGetHeight(imageBuffer))
        detectPose(in: inputImage, width: imageWidth, height: imageHeight)
    }
}
