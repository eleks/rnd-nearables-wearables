//
//  ViewController.swift
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/15/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var containerView: UIView!
    @IBOutlet weak var logoImageView: UIImageView!
    @IBOutlet weak var usernameTextField: UnderlinedTextField!
    @IBOutlet weak var passwordTextField: UnderlinedTextField!
    @IBOutlet weak var loginButton: UIButton!
    
    @IBOutlet weak var containerBottom: NSLayoutConstraint!
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        
        if AuthentificationManager.instance.isLoggedIn {
            signIn()
        }
        
        self.setupUserInterface()
    }
    
    override func awakeFromNib()
    {
        super.awakeFromNib()
        
        self.view.backgroundColor = Style.backgroundColor()
        self.containerView.backgroundColor = UIColor.clearColor()
    }
    
    func startAnimating()
    {
        var animation = CABasicAnimation(keyPath: "transform.rotation.z")
        animation.toValue = CGFloat(M_PI * 2.0)
        animation.duration = 1.2
        animation.repeatCount = 99999
        animation.cumulative = true
        
        self.logoImageView.layer.addAnimation(animation, forKey: "rotationAnimation")
    }
    
    @IBAction func login()
    {
        if 0 == count(self.usernameTextField.text) || 0 == count(self.passwordTextField.text) {
            UIAlertView(title: "Error", message:"Please, enter credentials", delegate: nil, cancelButtonTitle: "OK").show()
            return
        }
        
        self.loginButton.enabled = false
        
        AuthentificationManager.instance.logInWithUsername(self.usernameTextField.text, password: self.passwordTextField.text) { (success: Bool) -> Void in
                    dispatch_async(dispatch_get_main_queue(), { () -> Void in
                        
                        self.loginButton.enabled = true
                        
                        if success {
                            self.signIn()
                        }
                        else {
                            UIAlertView(title: "Error", message:"Something went wrong", delegate: nil, cancelButtonTitle: "OK").show()
                        }
                    })
        }

    }
    
    func signIn()
    {
        if let peopleTableViewController = self.storyboard?.instantiateViewControllerWithIdentifier("PeopleViewController") as? UIViewController {
            self.navigationController?.pushViewController(peopleTableViewController, animated: true)
        }
    }
    
    @IBAction func endEditing(sender: AnyObject)
    {
        usernameTextField.resignFirstResponder()
        passwordTextField.resignFirstResponder()
    }
    
    func setupUserInterface()
    {
        self.configureTextField(usernameTextField, withImage: UIImage(named: "user_icon"), text: "username")
        self.configureTextField(passwordTextField, withImage: UIImage(named: "pass_icon"), text: "password")
        
        self.loginButton.setTitleColor(Style.tintColor(), forState: .Normal)
        let layer = self.loginButton.layer
        layer.borderWidth = 1
        layer.borderColor = Style.tintColor().CGColor
        layer.cornerRadius = 20
    }
    
    func configureTextField(textField: UnderlinedTextField, withImage image: UIImage?, text : String)
    {
        textField.underlineImageView.image = UIImage(named: "divider")
        
        textField.attributedPlaceholder = NSAttributedString(string: text, attributes: [NSForegroundColorAttributeName : Style.textColor()])
        
        let margin = 10 as CGFloat
        textField.delegate = self;
        textField.autocorrectionType = .No
        textField.autocapitalizationType = .None
        
        var imageView = UIImageView(image: image)
        imageView.contentMode = .Center
        let side = CGRectGetHeight(textField.frame)
        imageView.frame = CGRectMake(margin, 0, side + 2 * margin , side)
        textField.leftViewMode = .Always
        textField.leftView = imageView
        
    }

    func textFieldShouldReturn(textField: UITextField) -> Bool
    {
        textField.resignFirstResponder()
        
        if textField == usernameTextField {
            passwordTextField.becomeFirstResponder()
        }
        else if textField == passwordTextField {
            self.login()
        }
        
        return true
    }

}

// MARK: - Keyboard Handling Extension

extension LoginViewController
{
    override func viewWillAppear(animated: Bool)
    {
        self.navigationController?.navigationBarHidden = true
        
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "keyboardWillShow:", name: UIKeyboardWillShowNotification, object: nil)
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "keyboardWillHide:", name: UIKeyboardWillHideNotification, object: nil)
    }
    
    override func viewWillDisappear(animated: Bool)
    {
        NSNotificationCenter.defaultCenter().removeObserver(self)
    }
    
    func keyboardWillShow(notification : NSNotification)
    {
        if let userInfo = notification.userInfo
        {
            if let keyboardSize = (userInfo[UIKeyboardFrameEndUserInfoKey] as? NSValue)?.CGRectValue()
            {
                self.containerBottom.constant = keyboardSize.size.height
                
                let duration = (userInfo[UIKeyboardAnimationDurationUserInfoKey] as? NSNumber)!.doubleValue
                UIView.animateWithDuration(duration, delay: 0.0, usingSpringWithDamping: 0.75,
                    initialSpringVelocity: 0.75, options: nil, animations: {
                        self.view.layoutIfNeeded()
                    }, completion: nil)
            }
        }
    }
    
    func keyboardWillHide(notification : NSNotification)
    {
        if let userInfo = notification.userInfo
        {
            if let keyboardSize = (userInfo[UIKeyboardFrameEndUserInfoKey] as? NSValue)?.CGRectValue()
            {
                self.containerBottom.constant = 0
                
                let duration = (userInfo[UIKeyboardAnimationDurationUserInfoKey] as? NSNumber)!.doubleValue
                UIView.animateWithDuration(duration, delay: 0.0, usingSpringWithDamping: 0.8,
                    initialSpringVelocity: 0.8, options: nil, animations: {
                        self.view.layoutIfNeeded()
                    }, completion: nil)
            }
        }
    }
    
}

