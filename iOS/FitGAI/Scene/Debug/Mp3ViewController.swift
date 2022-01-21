//
//  Mp3ViewController.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/27.
//

import Foundation
import UIKit

class Mp3ViewController: SampleBaseTableViewController {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    private enum ListItem : String, CaseIterable {
        case start
        case end
        case rating2
        case isPlaying
    }
    
    // MARK: - Private Variable
    private var listArray:[ListItem] = ListItem.allCases

    // MARK: - Override Method or Basic Method
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return listArray.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell(style: .subtitle, reuseIdentifier: NSStringFromClass(Self.self))
                
        cell.textLabel?.text = "\(listArray[indexPath.row])"
        cell.detailTextLabel?.text = "\(listArray[indexPath.row].rawValue)"
        
        return cell
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        switch listArray[indexPath.row] {
        case .start:
            AudioManager.shared.play(audioEnum: .start, useQueue: false)
        case .end:
            AudioManager.shared.play(audioEnum: .end, useQueue: false)
        case .rating2:
            AudioManager.shared.play(audioEnum: .rating2, useQueue: false)
        case .isPlaying:
            HHLog.d(.audio, "isPlaying = \(AudioManager.shared.isPlaying())")
        }
    }
}
