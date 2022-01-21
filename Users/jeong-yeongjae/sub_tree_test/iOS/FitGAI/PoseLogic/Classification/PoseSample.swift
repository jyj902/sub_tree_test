//
//  PoseSample.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/13.
//

import Foundation

class PoseSample {
    static let NUM_LANDMARKS = 33
    static let NUM_DIMS = 3;

    private var name:String
    private var className:String
    private var landmarks: [PointF3D]
    private var embedding = PoseEmbedding()
    init(name:String, className:String, landmarks:[PointF3D]) {
        self.name = name
        self.className = className
        self.landmarks = landmarks
    }
    
    public func getName() -> String {
        return name
    }
    
    public func getClassName() -> String {
        return className
    }
    
    public func getEmbedding() -> [PointF3D] {
        return embedding.getPoseEmbedding(landmarks: landmarks, type: PoseClassifierProcessor.type ?? 1)
    }
    
    public class func getPoseSample(csvLine:String,  separator:String) -> PoseSample? {
        var tokens:[String] = csvLine.components(separatedBy:",")
        // Format is expected to be Name,Class,X1,Y1,Z1,X2,Y2,Z2...
        // + 2 is for Name & Class.
        if tokens.count != (NUM_LANDMARKS * NUM_DIMS) + 2 {
//            HHLog.e(.poseDetect, .no0001, "Invalid number of tokens for PoseSample")
            return nil;
        }
        var name:String = tokens[0]
        var className:String = tokens[1]
        var landmarks:[PointF3D] = [PointF3D]()
        // Read from the third token, first 2 tokens are name and class.
        for i in stride(from: 2, to: tokens.count, by:3) {
            landmarks.append(PointF3D(x:((tokens[i] as NSString).doubleValue), y:((tokens[i+1] as NSString).doubleValue), z:((tokens[i+2] as NSString).doubleValue)))
        }
        return PoseSample(name:name, className:className, landmarks:landmarks)
    }
}
