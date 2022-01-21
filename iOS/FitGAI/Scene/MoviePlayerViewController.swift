//
//  MoviePlayerViewController.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/21.
//

import UIKit
import BMPlayer
import AVFoundation
import NVActivityIndicatorView

// MARK: - Public Outter Class, Struct, Enum, Protocol


class MoviePlayerViewController: BaseViewController {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    var titleText = ""
    var videoUrl:URL?
    
    // MARK: - IBOutlet
    // MARK: - Private Variable
    private var player: BMPlayer!
    
    // MARK: - Override Method or Basic Method
    override var supportedInterfaceOrientations: UIInterfaceOrientationMask {
        return [.all]
    }
    
    deinit {
        // If use the slide to back, remember to call this method
        player.prepareToDealloc()
        HHLog.d(.moviePlay, "VideoPlayViewController Deinit")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        preparePlayer()
        setupPlayerResource()
        
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(applicationDidEnterBackground),
                                               name: UIApplication.didEnterBackgroundNotification,
                                               object: nil)
        
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(applicationWillEnterForeground),
                                               name: UIApplication.willEnterForegroundNotification,
                                               object: nil)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        UIApplication.shared.setStatusBarStyle(UIStatusBarStyle.default, animated: false)
        // If use the slide to back, remember to call this method
        player.pause(allowAutoPlay: true)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        UIApplication.shared.setStatusBarStyle(UIStatusBarStyle.lightContent, animated: false)
        // If use the slide to back, remember to call this method
        player.autoPlay()
    }
    
    // MARK: - Public Method
    // MARK: - Private Method
    private func preparePlayer() {
        player = BMPlayer(customControlView: nil)
        view.addSubview(player)
        
        player.snp.makeConstraints { m in
            m.top.equalTo(view.snp.top)
            m.left.equalTo(view.snp.left)
            m.right.equalTo(view.snp.right)
            m.height.equalTo(view.snp.width).multipliedBy(9.0/16.0).priority(500)
        }
        
        player.delegate = self
        player.backBlock = { [unowned self] (isFullScreen) in
            if isFullScreen {
                return
            } else {
                let _ = self.navigationController?.popViewController(animated: true)
            }
        }
        
        self.view.layoutIfNeeded()
    }
    
    private func setupPlayerResource() {
        guard let videoUrl = videoUrl else {
            HHLog.e(.moviePlay, .no0056, "videoUrl is nil")
            return
        }
        
        let asset = BMPlayerResource(name: titleText,
                                     definitions: [BMPlayerResourceDefinition(url: videoUrl, definition: "480p")],
                                     cover: nil,
                                     subtitles: nil)
        
        player.seek(30)
        player.setVideo(resource: asset)
    }
    
    // MARK: - IBAction
    @objc func applicationWillEnterForeground() {
        
    }
    
    @objc func applicationDidEnterBackground() {
        player.pause(allowAutoPlay: false)
    }
}

// MARK:- BMPlayerDelegate example
extension MoviePlayerViewController: BMPlayerDelegate {
    // Call when player orinet changed
    func bmPlayer(player: BMPlayer, playerOrientChanged isFullscreen: Bool) {
        player.snp.remakeConstraints { m in
            m.top.equalTo(view.snp.top)
            m.left.equalTo(view.snp.left)
            m.right.equalTo(view.snp.right)
            if isFullscreen {
                m.bottom.equalTo(view.snp.bottom)
            } else {
                m.height.equalTo(view.snp.width).multipliedBy(9.0/16.0).priority(500)
            }
        }
    }
    
    // Call back when playing state changed, use to detect is playing or not
    func bmPlayer(player: BMPlayer, playerIsPlaying playing: Bool) {
        HHLog.d(.moviePlay, "| BMPlayerDelegate | playerIsPlaying | playing - \(playing)")
    }
    
    // Call back when playing state changed, use to detect specefic state like buffering, bufferfinished
    func bmPlayer(player: BMPlayer, playerStateDidChange state: BMPlayerState) {
        HHLog.d(.moviePlay, "| BMPlayerDelegate | playerStateDidChange | state - \(state)")
    }
    
    // Call back when play time change
    func bmPlayer(player: BMPlayer, playTimeDidChange currentTime: TimeInterval, totalTime: TimeInterval) {
        HHLog.d(.moviePlay, "| BMPlayerDelegate | playTimeDidChange | \(currentTime) of \(totalTime)")
    }
    
    // Call back when the video loaded duration changed
    func bmPlayer(player: BMPlayer, loadedTimeDidChange loadedDuration: TimeInterval, totalDuration: TimeInterval) {
        HHLog.d(.moviePlay, "| BMPlayerDelegate | loadedTimeDidChange | \(loadedDuration) of \(totalDuration)")
    }
}
