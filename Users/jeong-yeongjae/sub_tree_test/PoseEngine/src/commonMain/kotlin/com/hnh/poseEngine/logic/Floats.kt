package com.hnh.poseengine.logic

class Floats {
    companion object {
        fun max(a:Double, b:Double, c:Double):Float {
            var max:Double
            max = a
            if (max < b) {
                max = b
            }
            if (max < c) {
                max = c
            }
            return max.toFloat()
        }

        fun max(a:Float, b:Float, c:Float):Float {
            var max:Float
            max = a
            if (max < b) {
                max = b
            }
            if (max < c) {
                max = c
            }
            return max
        }
    }
}