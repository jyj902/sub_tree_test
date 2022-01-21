//
//  TTSManager.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/22.
//

import Foundation
import AVKit
import AVFAudio
import AVFoundation

class TTSManager :NSObject {
    
    // MARK: - Public Variable
    public static let shared = TTSManager()

    let speech = AVSpeechSynthesizer()
    var utterance: AVSpeechUtterance?

    // MARK: - Override Method or Basic Method
    private override init() {
        super.init()
        speech.delegate = self
        do {
            try AVAudioSession.sharedInstance().setCategory(.playback, options: .mixWithOthers)
            try AVAudioSession.sharedInstance().setActive(true)
        } catch {
            HHLog.e(.audio, .no0010, "error")
        }
    }
    
    // MARK: - Public Method
    func speak(audioEnum:AudioEnum) {
        let strText = audioEnum.getSentense()
        speak(strText: strText)
    }
    
    func speakCount(count:Int) {
        speak(strText: "\(count)개")
    }

    // 실제 출력 함수(1)
    func speak(strText: String) {
        utterance = AVSpeechUtterance(string: strText)
        if let utterance = utterance {
            utterance.voice = AVSpeechSynthesisVoice(language: "ko-KR")
            HHLog.d(.audio, "speak() strText = \(strText)")
            speech.speak(utterance)
        }
    }
}

// MARK: - Delegate
extension TTSManager : AVSpeechSynthesizerDelegate {
    func speechSynthesizer(_ synthesizer: AVSpeechSynthesizer, didStart utterance: AVSpeechUtterance) {
        HHLog.d(.audio, "didStart()")
    }

    func speechSynthesizer(_ synthesizer: AVSpeechSynthesizer, didFinish utterance: AVSpeechUtterance) {
        HHLog.d(.audio, "didFinish()")
    }

    func speechSynthesizer(_ synthesizer: AVSpeechSynthesizer, didPause utterance: AVSpeechUtterance) {
        HHLog.d(.audio, "didPause()")
    }

    func speechSynthesizer(_ synthesizer: AVSpeechSynthesizer, didContinue utterance: AVSpeechUtterance) {
        HHLog.d(.audio, "didContinue()")
    }

    func speechSynthesizer(_ synthesizer: AVSpeechSynthesizer, didCancel utterance: AVSpeechUtterance) {
        HHLog.d(.audio, "didCancel()")
    }
    
    func speechSynthesizer(_ synthesizer: AVSpeechSynthesizer, willSpeakRangeOfSpeechString characterRange: NSRange, utterance: AVSpeechUtterance) {
        HHLog.d(.audio, "willSpeakRangeOfSpeechString()")
    }
}
