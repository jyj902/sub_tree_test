package com.hnh.poseengine

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}