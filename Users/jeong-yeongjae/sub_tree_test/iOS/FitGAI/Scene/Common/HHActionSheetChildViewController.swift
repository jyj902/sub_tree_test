//
//  ActionSheetInnerViewController.swift
//  SwiftSampleCollection
//
//  Created by 김기훈 on 2021/03/26.
//  Copyright © 2021 com.hnh. All rights reserved.
//
import UIKit
// MARK: - Public Outter Class, Struct, Enum, Protocol
class HHActionSheetChildViewController: UIViewController {

    // MARK: - Public Inner Class, Struct, Enum, Protocol
    private enum ActionSheetStatus {
        case openMax
        case openPreferred
        case close
    }

    // MARK: - Public Variable
    // MARK: - IBOutlet
    // MARK: - Private Variable
    private let viewCellFont     = UIFont.typeA18
    private let headerFont       = UIFont.typeA22
    private let headerFontWeight = UIFont.Weight.bold
    private let maxTopPadding:CGFloat = 50.0
    private let headerTopPadding:CGFloat = 20.0
    private let headerBottomPadding:CGFloat = 10.0
    private let preferredItemNum = 4

    private var vHeaderView: UIView
    private var ivHandle:UIImageView
    private var titleLabel:UILabel

    private var titleText: String?
    private var tbvTable: UITableView
    private var actions:[HHActionSheetAction]
    private var firstFlag:Bool
    private var actionSheetStatus: ActionSheetStatus
    
    private var headerHeight: CGFloat {
        //HHLog.d(.internal, "headerHeight 높이 : vHeaderView의 높이 =\(vHeaderView.sizeThatFits(self.view.bounds.size).height), font로 계산 =\(CGFloat(headerFont.getSize()) + headerTopPadding + headerBottomPadding + 4)")
        return vHeaderView.sizeThatFits(self.view.bounds.size).height
        //return CGFloat(headerFont.getSize()) + headerTopPadding + headerBottomPadding + 4
        //return 54
    }
    
    private var cellHeight: CGFloat {
        //HHLog.d(.internal, "cellHeight 높이 : viewCell의 높이 =\(tbvTable.visibleCells.first!.sizeThatFits(self.view.bounds.size).height), font로 계산 =\(CGFloat(viewCellFont.getSize()) + 36.0)")
        //return tbvTable.visibleCells.first!.sizeThatFits(self.view.bounds.size).height
        return CGFloat(viewCellFont.getFontSize()) + 38.0
        //return 53
    }
    
    private var maxTopY: CGFloat {
        return Coordinate.safeInsetTop + maxTopPadding
    }
    
    private var maxHeight: CGFloat {
        return Coordinate.height - maxTopY
    }

    private var openMaxTopY: CGFloat {
        let properHeight:CGFloat = headerHeight + CGFloat(actions.count) * cellHeight + Coordinate.safeInsetBottom
        if (Coordinate.height - Coordinate.safeInsetTop - maxTopPadding) < properHeight {
            return maxTopY
        } else {
            return Coordinate.height - properHeight
        }
    }
    
    private var openMaxHeight: CGFloat {
        return Coordinate.height - openMaxTopY
    }
    
    var preferredTopY: CGFloat {
        return Coordinate.height - preferredHeight
    }
    
    private var defaultHeight: CGFloat {
        let defaultHeight:CGFloat = headerHeight + CGFloat(4) * CGFloat(cellHeight) + Coordinate.safeInsetBottom
        return defaultHeight
    }

    private var preferredHeight: CGFloat {
        let count = actions.count >= preferredItemNum ? preferredItemNum : actions.count
        let preferredHeight:CGFloat = headerHeight + CGFloat(count) * CGFloat(cellHeight) + Coordinate.safeInsetBottom
        if preferredHeight > openMaxHeight {
            return openMaxHeight
        }
        return preferredHeight
    }
    
    // MARK: - Override Method or Basic Method
    init?(title: String?) {
        self.titleText = title
        actions = [HHActionSheetAction]()

        vHeaderView = UIView(frame: .zero)
        ivHandle = UIImageView(frame: CGRect(x: 0, y: 0, width: 40, height: 8))
        titleLabel = UILabel(frame: .zero)
        tbvTable = UITableView(frame: .zero, style: .plain)
        firstFlag = true
        actionSheetStatus = .close

        super.init(nibName: nil, bundle: nil)
    }
    
    convenience init() {
        self.init(title:nil)!
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initializeViews()
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        //ivHandle.isHidden = true
        if firstFlag {
            UIView.animate(withDuration: 0.3) { [weak self] in
                guard let self = self else {
                    return
                }
                self.setFrame(status : ActionSheetStatus.openPreferred)
            }
            firstFlag = false
        } else {
            setFrame(status : ActionSheetStatus.openPreferred)
        }

        // 모서리 Round 처리
        let maskPath = UIBezierPath(roundedRect: view.bounds, byRoundingCorners: [.topLeft, .topRight], cornerRadii: CGSize(width: 24, height: 24))
        let maskLayer = CAShapeLayer()
        maskLayer.path  = maskPath.cgPath
        view.layer.mask = maskLayer;
    }
    
    // MARK: - Public Method
    func addAction(_ action:HHActionSheetAction) {
        actions.append(action)
    }
            
    // MARK: - Private Method
    private func initializeViews() {
        // Views 초기화
        view.preservesSuperviewLayoutMargins = true
        view.insetsLayoutMarginsFromSafeArea = true

        vHeaderView.backgroundColor = .white
        vHeaderView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(vHeaderView)

        ivHandle.contentMode = .scaleAspectFit
        ivHandle.image = R.image.actionsheet_handle()
        ivHandle.translatesAutoresizingMaskIntoConstraints = false
        vHeaderView.addSubview(ivHandle)

        //titleLabel.textStyleFont = headerFont
        //titleLabel.weight = UIFont.Weight.bold
        titleLabel.font = headerFont.weight(UIFont.Weight.bold)
        titleLabel.textColor = .black
        titleLabel.text = titleText ?? ""
        titleLabel.numberOfLines = 1;
        titleLabel.adjustsFontForContentSizeCategory = true
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        vHeaderView.addSubview(titleLabel)
        
        tbvTable.dataSource = self
        tbvTable.delegate = self
        tbvTable.backgroundColor = .white
        tbvTable.tableFooterView = UIView.init(frame: .zero)
        tbvTable.alwaysBounceVertical = false
        tbvTable.bounces = false
        tbvTable.separatorStyle = .singleLine
        tbvTable.frame = view.bounds
        tbvTable.cellLayoutMarginsFollowReadableWidth = false
        tbvTable.insetsLayoutMarginsFromSafeArea = true
        tbvTable.translatesAutoresizingMaskIntoConstraints = false
        tbvTable.isScrollEnabled = false
        tbvTable.separatorInset.right = 25
        tbvTable.separatorInset.left = 25

        view.addSubview(tbvTable)
        
        initializeConstraints()
        
        //Gesture Register
        let gesture = UIPanGestureRecognizer.init(target: self, action: #selector(panGesture(recognizer:)))
        gesture.delegate = self
        view.addGestureRecognizer(gesture)

        NotificationCenter.default.addObserver(forName: UIContentSizeCategory.didChangeNotification,  object: nil, queue: OperationQueue.main) { (Notification) in
            UIView.animate(withDuration: 0.1, delay: 0.0, options: [.allowUserInteraction]){ [weak self] in
                guard let self = self else {
                    return
                }
                
                self.setFrame(status:ActionSheetStatus.openPreferred)
            }
        }
    }
    
    private func initializeConstraints() {
        //Auto layout
        vHeaderView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        vHeaderView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        vHeaderView.topAnchor.constraint(equalTo: view.topAnchor).isActive = true
        vHeaderView.bottomAnchor.constraint(equalTo: tbvTable.topAnchor).isActive = true

        ivHandle.centerXAnchor.constraint(equalTo: vHeaderView.centerXAnchor).isActive = true
        ivHandle.topAnchor.constraint(equalTo:vHeaderView.topAnchor, constant: 10).isActive = true

        titleLabel.leadingAnchor.constraint(equalTo: vHeaderView.leadingAnchor, constant: 16).isActive = true
        titleLabel.trailingAnchor.constraint(equalTo: vHeaderView.trailingAnchor, constant: -16).isActive = true
        titleLabel.topAnchor.constraint(equalTo: vHeaderView.topAnchor, constant: headerTopPadding).isActive = true
        titleLabel.bottomAnchor.constraint(equalTo: vHeaderView.bottomAnchor, constant:  -headerBottomPadding).isActive = true

        tbvTable.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        tbvTable.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        tbvTable.topAnchor.constraint(equalTo: vHeaderView.bottomAnchor).isActive = true
        tbvTable.bottomAnchor.constraint(equalTo: view.bottomAnchor, constant: 0).isActive = true
    }
    
    private func setFrame(status: ActionSheetStatus) {
        let left = Coordinate.safeInsetLeft
        let width = Coordinate.width - Coordinate.safeInsetRight - Coordinate.safeInsetLeft
        switch status {
        case .openMax:
            self.view.frame = CGRect(x: left, y: openMaxTopY, width: width, height: openMaxHeight)
            self.ivHandle.isHidden = false
        case .openPreferred:
            // height 값을 preferredHeight 로 주면, 스크롤할때 밑부분이 짤려서 maxHeight 를 줍니다.
            self.view.frame = CGRect(x: left, y: preferredTopY, width: width, height: openMaxHeight)
            //self.ivHandle.isHidden = true
        case .close:
            self.view.frame = CGRect(x: left, y: Coordinate.height, width: width, height: openMaxHeight)
        }
        if status == .openPreferred && openMaxTopY == preferredTopY {
            actionSheetStatus = .openMax
        } else {
            actionSheetStatus = status
        }
        HHLog.d(.core, "setFrame() self.view.frame = \(self.view.frame)")
        HHLog.d(.core, "setFrame() actionSheetStatus = \(actionSheetStatus)")
    }
    
    // MARK: - IBAction
    @objc private func panGesture(recognizer: UIPanGestureRecognizer) {
        let translation = recognizer.translation(in: view)
        let velocity = recognizer.velocity(in: view)
        
        let y = view.frame.minY
        
        //HHLog.d(.internal, "translation = \(translation), velocity = \(velocity)")
        //HHLog.d(.internal, "preferredHeight = \(preferredHeight)")

        if y + translation.y >= openMaxTopY {
            view.frame = CGRect(x: Coordinate.safeInsetLeft, y: y + translation.y, width: view.frame.width, height: view.frame.height)
            recognizer.setTranslation(CGPoint.zero, in: view)
        }
        
        if recognizer.state == .ended {
            let duration = 0.3
            UIView.animate(withDuration: duration, delay: 0.0, options: [.allowUserInteraction], animations: { [weak self] in
                guard let self = self else {
                    return
                }
                if  velocity.y >= 0 {
                    if y + translation.y >= self.preferredTopY {
                        self.setFrame(status: ActionSheetStatus.close)
                    } else {
                        self.setFrame(status: ActionSheetStatus.openPreferred)
                    }
                } else {
                    self.setFrame(status: ActionSheetStatus.openMax)
                }
            }, completion: { [weak self] _ in
                guard let self = self else {
                    return
                }
                //HHLog.d(.internal, "velocity.y = \(velocity.y), self.view.frame.minY = \(self.view.frame.minY), self.preferredTopY = \(self.preferredTopY)")
                if ( velocity.y < 0 && (abs(self.view.frame.minY - self.preferredTopY) > 1) ) {
                    self.tbvTable.isScrollEnabled = true
                }
                
                if self.view.frame.origin.y > Coordinate.height - 2,
                   let parent = self.parent as? HHActionSheetViewController {
                    parent.dismissActionSheet(animation: false, handler: nil)
                }
            })
        }
    }
}

// MARK: - Delegate
extension HHActionSheetChildViewController: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return actions.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell(style: .subtitle, reuseIdentifier: "MyCell")
        cell.textLabel?.text = actions[indexPath.row].title
        cell.textLabel?.font = viewCellFont
        return cell
    }
        
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        if let parent = self.parent as? HHActionSheetViewController {
            parent.dismissActionSheet(animation:true, handler: actions[indexPath.row].handler)
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return UITableView.automaticDimension
    }
}

extension HHActionSheetChildViewController: UIGestureRecognizerDelegate {
    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldRecognizeSimultaneouslyWith otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        let gesture = (gestureRecognizer as! UIPanGestureRecognizer)
        let direction = gesture.velocity(in: view).y
        
        let y = view.frame.minY
        HHLog.d(.core, "tbvTable.contentOffset.y = \(tbvTable.contentOffset.y), direction = \(direction), y = \(y), preferredTopY = \(preferredTopY), openMaxTopY = \(openMaxTopY)")

        if direction > 0 {              // 밑으로 내릴때
            if tbvTable.contentOffset.y == 0 {
                tbvTable.isScrollEnabled = false
            } else {
                tbvTable.isScrollEnabled = true
            }
        } else if direction < 0 {       // 위로 올릴때
            if actionSheetStatus == .close || actionSheetStatus == .openPreferred {
                tbvTable.isScrollEnabled = false
            } else {
                if openMaxHeight < maxHeight {
                    tbvTable.isScrollEnabled = false
                } else {
                    tbvTable.isScrollEnabled = true
                }
            }
        }
        return false
    }
}
