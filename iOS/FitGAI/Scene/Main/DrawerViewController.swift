import UIKit
import SnapKit
import Toast_Swift

struct DrawerMenuItem {
    var name:String
}

protocol DrawerExpandable {
    func expand(animated:Bool)
    func collapse(animated:Bool)
}

class DrawerViewController: UIViewController {
    private let damping: CGFloat = 500
    private let velocity: CGFloat = 1

    @IBOutlet weak var tbvMenu: UITableView!
    @IBOutlet weak var lbEmail: UILabel!
    @IBOutlet weak var btnLogout: UIButton!

    private var isExpaned:Bool = false
    private var openSize:CGFloat = 0
    
    public var allDrawerMenuArray:[DrawerMenuItem] = [
        DrawerMenuItem(name:TODO("마이페이지")),
        DrawerMenuItem(name:TODO("설정")),
        DrawerMenuItem(name:TODO("문의하기")),
    ]
    override func viewDidLoad() {
        super.viewDidLoad()
        self.isExpaned = false
        self.tbvMenu.tableFooterView = UIView(frame:CGRect.zero)
        self.lbEmail.text = AccountManager.shared.userEmail
    }
    
    func touchMove(_ sender: UIPanGestureRecognizer?) {
        let movePoint = sender!.translation(in: view.superview)
        var needToClose:Bool
        var velocityX:CGFloat

        let movePos = getOpenSize() + movePoint.x
        sender!.setTranslation(CGPoint.zero, in: view)
        velocityX = sender!.velocity(in: view).x
        
        needToClose = (movePos) < (view.frame.size.width / 2)
        needToClose = (velocityX < -500 ? true : false) || needToClose

        expanding(size:movePos)
        
        if sender!.state == .ended ||
            sender!.state == .cancelled {
            if ( needToClose ) {
                self.isExpaned = true
                collapse(animated: true)
            } else {
                self.isExpaned = false
                expand(animated: true)
            }
        }
    }
    
    @IBAction func onClickClose(_ sender: Any) {
        collapse(animated: true)
    }
    
    @IBAction func onClickLogout(_ sender: Any) {
        collapse(animated: false)
        HHDialog(style:HHStyle(HHDialogStyle.none), titleText: TODO("로그아웃"), contentsText: TODO("정말로 로그아웃하시겠습니까?"), okText: TODO("확인"), okHandler: { [weak self] in
            AccountManager.shared.processLogout()
        }, cancelText: TODO("취소"), cancelHandler: nil)
    }
}

extension DrawerViewController: DrawerExpandable {
    func expand(animated:Bool) {
        guard let parent = self.parent else { return }
        if self.isExpaned { return }
        openSize = UIScreen.main.bounds.width
        view.snp.updateConstraints { $0.trailing.equalTo(parent.view.snp.leading).offset(openSize) }
        if animated {
            UIView.animate(withDuration: 0.5, delay: 0, usingSpringWithDamping: damping, initialSpringVelocity: velocity, options: .curveLinear, animations: {
                parent.view.layoutIfNeeded()
            }, completion: { _ in
            })
        }
        self.isExpaned = true
    }
    
    func collapse(animated:Bool) {
        guard let parent = self.parent else { return }
        if !self.isExpaned { return }
        openSize = 0
        view.snp.updateConstraints { $0.trailing.equalTo(parent.view.snp.leading) }
        if animated {
            UIView.animate(withDuration: 0.5, delay: 0, usingSpringWithDamping: damping, initialSpringVelocity: velocity, options: .curveLinear, animations: {
                parent.view.layoutIfNeeded()
            }, completion: { _ in
            })
        }
        self.isExpaned = false
    }
    
    func expanding(size:CGFloat) {
        guard let parent = self.parent else { return }
        openSize = size
        if openSize > UIScreen.main.bounds.width {
            openSize = UIScreen.main.bounds.width
        }
        view.snp.updateConstraints { $0.trailing.equalTo(parent.view.snp.leading).offset(openSize) }
    }
    
    func getOpenSize() -> CGFloat {
        return openSize
    }
}
extension DrawerViewController : UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return allDrawerMenuArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell(style: .subtitle, reuseIdentifier: "MyCell")
        cell.textLabel?.text = allDrawerMenuArray[indexPath.row].name
        return cell
    }
}

extension DrawerViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        collapse(animated: false)
        UIViewController.top().view.makeToast(allDrawerMenuArray[indexPath.row].name)
    }
}
