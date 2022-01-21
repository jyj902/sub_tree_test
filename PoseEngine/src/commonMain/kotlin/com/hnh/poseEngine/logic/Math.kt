package com.hnh.poseengine.logic

import kotlin.math.sqrt

class Math {
    companion object {
        const val PI = 3.14159265358979323846

        fun min(a:Int, b:Int):Int {
            if (a > b) {
                return b
            } else {
                return a
            }
        }

        fun min(a:Float, b:Float):Float {
            if (a > b) {
                return b
            } else {
                return a
            }
        }

        fun abs(a:Double):Double {
            if ( 0 <= a ) {
                return a
            } else {
                return -a
            }
        }

        fun abs(a:Float):Float {
            if ( 0 <= a ) {
                return a
            } else {
                return -a
            }
        }

        fun hypot(a:Float, b:Float):Float {
            return sqrt(a * a + b * b)
        }

        fun toDegrees(a:Double):Double {
            return a * 180.0f / Math.PI
        }

        // 반올
        fun roundToInt(a:Float):Int {
            return ( a + 0.5 ).toInt()
        }
    }
}