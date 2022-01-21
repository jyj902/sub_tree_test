import UIKit
import Then


// MARK: - Public Outter Class, Struct, Enum, Protocol
protocol MainHeaderViewDelegate:AnyObject {
    func onClickDrawerMenu()
}

class MainHeaderView: UIView {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable
    weak var mainHeaderViewDelegate: MainHeaderViewDelegate?
    // MARK: - IBOutlet
    // MARK: - Private Variable

    // MARK: - Override Method or Basic Method
    override init(frame: CGRect) {
        super.init(frame: frame)
        setupUI()
    }
    
    convenience init(frame: CGRect, mainHeaderViewDelegate: MainHeaderViewDelegate?) {
        self.init(frame: frame)
        self.mainHeaderViewDelegate = mainHeaderViewDelegate
    }

    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setupUI()
    }
    
    // MARK: - Public Method
    
    // MARK: - Private Method
    private func setupUI() {
        backgroundColor = UIColor.bgTheme
        let btn = UIButton(frame: .zero).then {
            $0.addTarget(self, action: #selector(onClickDrawerMenu), for: .touchUpInside)
            $0.setImage(R.image.menu_btn_nor(), for: .normal)
            $0.setTitleColor(.white, for: .normal)
        }
        addSubview(btn)
        btn.snp.makeConstraints {
            $0.leading.bottom.equalToSuperview()
            $0.width.equalTo(50)
        }
        
        let titleLabel = UILabel(frame: .zero).then {
            $0.text = "FitGAI"
            $0.textColor = .white
        }
        addSubview(titleLabel)
        titleLabel.snp.makeConstraints {
            $0.leading.equalTo(btn.snp.trailing).offset(10)
            $0.centerY.equalTo(btn.snp.centerY)
        }
    }

    // MARK: - IBAction
    @objc private func onClickDrawerMenu() {
        mainHeaderViewDelegate?.onClickDrawerMenu()
    }
}

// MARK: - Delegate
