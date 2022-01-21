//
//  Utils.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/13.
//

import Foundation
import MLKit

class Utils {
    class func add(_ a: PointF3D, _ b: PointF3D) -> PointF3D {
        return PointF3D(x: a.x + b.x, y: a.y + b.y, z: a.z + b.z)
    }
    
    class func subtract(_ b: PointF3D, _ a: PointF3D) -> PointF3D {
        return PointF3D(x: a.x - b.x, y: a.y - b.y, z: a.z - b.z)
    }

    class func multiply(_ a: PointF3D, _ multiple: Float) -> PointF3D {
        return PointF3D(x: a.x * Double(multiple), y: a.y * Double(multiple), z: a.z * Double(multiple))
    }

    class func multiply(_ a: PointF3D, _ multiple: PointF3D) -> PointF3D {
        return PointF3D(x: a.x * multiple.x, y: a.y * multiple.y, z: a.z * multiple.z)
    }

    class func average(_ a: PointF3D, _ b: PointF3D) -> PointF3D {
        return PointF3D(x: (a.x + b.x) * 0.5, y: (a.y + b.y) * 0.5, z: (a.z + b.z) * 0.5)
    }

    class func l2Norm2D(_ point: PointF3D) -> Float {
        return Float(hypot(point.x, point.y))
    }

    class func maxAbs(_ point: PointF3D) -> Float {
        return Float(max(abs(point.x), abs(point.y), abs(point.z)))
    }

    class func sumAbs(_ point: PointF3D) -> Float {
        return Float(abs(point.x) + abs(point.y) + abs(point.z))
    }

    class func addAll(_ pointsList: [PointF3D], _ p: PointF3D) -> [PointF3D]{
        return pointsList.map { point in
            Utils.add(point, p)
        }
    }

    class func subtractAll(_ pointsList: [PointF3D], _ p: PointF3D) -> [PointF3D] {
        return pointsList.map { point in
            Utils.subtract(p, point)
        }
    }

    class func multiplyAll(_ pointsList: [PointF3D], _ multiple: Float) -> [PointF3D] {
        return pointsList.map { point in
            Utils.multiply(point, multiple)
        }
    }

    class func multiplyAll(_ pointsList: [PointF3D], _ multiple: PointF3D) -> [PointF3D] {
        return pointsList.map { point in
            Utils.multiply(point, multiple)
        }
    }
}
