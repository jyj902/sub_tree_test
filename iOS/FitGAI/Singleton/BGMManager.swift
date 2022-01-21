//
//  TTS.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/07.
//

import UIKit
import AVKit
import AVFAudio
import AVFoundation

// MARK: - Public Outter Class, Struct, Enum, Protocol
enum BGMEnum {
    case bgm1
    func getFileName() -> String {
        switch self {
        case .bgm1 : return "background_music_or_break_time"
        }
    }
}

class BGMManager :NSObject {
    // MARK: - Public Variable
    public static let shared = BGMManager()
    var player: AVAudioPlayer?
    
    // MARK: - Private Variable
    
    // MARK: - Override Method or Basic Method
    private override init() {
        super.init()
        do {
            try AVAudioSession.sharedInstance().setCategory(.playback, options: .mixWithOthers)
            try AVAudioSession.sharedInstance().setActive(true)
        } catch {
            HHLog.e(.audio, .no0011, "error")
        }
    }
    
    // MARK: - Public Method
    func isPlaying() -> Bool {
        if let player = player {
            return player.isPlaying
        }
        return false
    }

    func play(bgmEnum:BGMEnum) {
        do {
            let urlString  = Bundle.main.path(forResource: bgmEnum.getFileName(), ofType: "wav")
            guard let urlString = urlString else {
                HHLog.e(.audio, .no0012, "[ERROR] urlString is nil, bgmEnum = \(bgmEnum)")
                return
            }
            HHLog.d(.audio, "urlString = \(urlString)")
            player = try AVAudioPlayer(contentsOf: URL(string: urlString)!)
            
            guard let player = player else {
                HHLog.e(.audio, .no0013, "[ERROR] player is nil")
                return
            }
            HHLog.d(.audio, "duration = \(player.duration)")
            player.volume = 0.05
            player.delegate = self
            player.play()
            
        } catch {
            HHLog.e(.audio, .no0014, "play error")
        }
    }
    
    func stop() {
        guard let player = player else {
            return
        }
        if player.isPlaying {
            player.stop()
        }
    }
    // MARK: - Private Method
}

// MARK: - Delegate
extension BGMManager : AVAudioPlayerDelegate {
    func audioPlayerDidFinishPlaying(_ player: AVAudioPlayer, successfully flag: Bool) {
        HHLog.d(.audio, "audioPlayerDidFinishPlaying()")
        player.play()
    }
    
    func audioPlayerDecodeErrorDidOccur(_ player: AVAudioPlayer, error: Error?) {
        HHLog.d(.audio, "audioPlayerDecodeErrorDidOccur()")
    }

    func audioPlayerBeginInterruption(_ player: AVAudioPlayer) {
        HHLog.d(.audio, "audioPlayerBeginInterruption()")
    }
    
    func audioPlayerEndInterruption(_ player: AVAudioPlayer, withOptions flags: Int) {
        HHLog.d(.audio, "audioPlayerEndInterruption()")
    }
}


