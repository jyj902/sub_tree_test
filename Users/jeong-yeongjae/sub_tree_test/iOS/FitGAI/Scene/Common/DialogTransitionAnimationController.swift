//
//  DialogTransitionAnimationController.swift
//  FitGAI
//
//  Created by 김기훈 on 2021/12/14.
//

import UIKit

class DialogTransitionAnimationController : NSObject {
    var presenting:Bool = false
}

// MARK: - Delegate
extension DialogTransitionAnimationController: UIViewControllerAnimatedTransitioning {
    func animateTransition(using transitionContext: UIViewControllerContextTransitioning) {
        if presenting {
            let toViewController = transitionContext.viewController(forKey: .to)
            
            if let dialogViewController = toViewController as? HHDialogViewController {
                dialogViewController.vBackground.alpha = 0
                dialogViewController.vDialog.alpha = 0.5
                dialogViewController.vDialog.transform = CGAffineTransform(scaleX: 1.05, y: 1.05)
                let containerView = transitionContext.containerView
                if let view = dialogViewController.view {
                    containerView.addSubview(view)
                    let duration = transitionDuration(using: transitionContext)

                    UIView.animate(withDuration: duration, delay: 0, options: .curveEaseInOut, animations: { [weak dialogViewController] in
                        dialogViewController?.vBackground.alpha = 1
                    }) { finished in
                        transitionContext.completeTransition(finished)
                    }
                    UIView.animate(withDuration: duration * 0.6, delay: 0, options: .curveEaseOut, animations: { [weak dialogViewController] in
                        dialogViewController?.vDialog.alpha = 1
                        dialogViewController?.vDialog.transform = CGAffineTransform.identity
                    })
                }
            } else if let dialogViewController = toViewController as? ExerciseSettingViewController {
                dialogViewController.vBackground.alpha = 0
                dialogViewController.vDialog.alpha = 0.5
                dialogViewController.vDialog.transform = CGAffineTransform(scaleX: 1.05, y: 1.05)
                let containerView = transitionContext.containerView
                if let view = dialogViewController.view {
                    containerView.addSubview(view)
                    let duration = transitionDuration(using: transitionContext)

                    UIView.animate(withDuration: duration, delay: 0, options: .curveEaseInOut, animations: { [weak dialogViewController] in
                        dialogViewController?.vBackground.alpha = 1
                    }) { finished in
                        transitionContext.completeTransition(finished)
                    }
                    UIView.animate(withDuration: duration * 0.6, delay: 0, options: .curveEaseOut, animations: { [weak dialogViewController] in
                        dialogViewController?.vDialog.alpha = 1
                        dialogViewController?.vDialog.transform = CGAffineTransform.identity
                    })
                }
            }
        } else {
            let fromViewController = transitionContext.viewController(forKey: .from)
            if let dialogViewController = fromViewController as? HHDialogViewController {
                let duration = transitionDuration(using: transitionContext)
                UIView.animate(withDuration: duration, delay: 0, options: .curveEaseInOut, animations: { [weak dialogViewController] in
                    dialogViewController?.vDialog.alpha = 0
                    dialogViewController?.vBackground.alpha = 0
                }) { finished in
                    transitionContext.completeTransition(finished)
                }
            } else if let dialogViewController = fromViewController as? ExerciseSettingViewController {
                let duration = transitionDuration(using: transitionContext)
                UIView.animate(withDuration: duration, delay: 0, options: .curveEaseInOut, animations: { [weak dialogViewController] in
                    dialogViewController?.vDialog.alpha = 0
                    dialogViewController?.vBackground.alpha = 0
                }) { finished in
                    transitionContext.completeTransition(finished)
                }
            }
        }
    }
    
    func animationEnded(_ transitionCompleted: Bool) {

    }

    func transitionDuration(using transitionContext: UIViewControllerContextTransitioning?) -> TimeInterval {
        return 0.25
    }
}
