
package com.hnh.poseengine.logic;

class PointF3D(x:Float, y:Float, z:Float) {
    val x: Float = x
    val y: Float = y
    val z: Float = z

    companion object {
        fun from(x:Float, y:Float, z:Float):PointF3D {
            return PointF3D(x, y, z)
        }
    }
}
