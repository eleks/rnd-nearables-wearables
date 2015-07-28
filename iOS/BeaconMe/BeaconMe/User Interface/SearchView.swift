//
//  SearchView.swift
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/20/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

import UIKit

protocol SearchViewDelegate
{
    func cancelSearch()
    func search(text: String)
}

class SearchView: UIView, UITextFieldDelegate {
    
    static let defaultHeight: CGFloat = 86
    
    @IBOutlet weak var searchTextField: UITextField!
    @IBOutlet weak var cancelButton: UIButton!
    
    var delegate : SearchViewDelegate? = nil
    
    override func awakeFromNib()
    {
        self.searchTextField.tintColor = UIColor.whiteColor()
        self.searchTextField.delegate = self
        self.searchTextField.addTarget(self, action: "textFieldValueDidChange", forControlEvents: UIControlEvents.EditingChanged)
        
        let buttonSide = CGRectGetHeight(self.searchTextField.frame)
        let clearButton = UIButton(frame: CGRectMake(0, 0, buttonSide, buttonSide))
        var clearImage = UIImage(named: "clear")?.imageWithRenderingMode(.AlwaysTemplate)
        
        clearButton.setImage(clearImage, forState: .Normal)
        clearButton.addTarget(self, action: "onClear", forControlEvents: .TouchUpInside)

        self.searchTextField.rightViewMode = .Always
        self.searchTextField.rightView = clearButton
        
        self.backgroundColor = Style.tintColor()
    }
    
    func onClear()
    {
        self.searchTextField.text = ""
    }
    
    @IBAction func onCancel(sender: AnyObject)
    {
        self.delegate?.cancelSearch()
    }
    
    func textFieldValueDidChange()
    {
        self.delegate?.search(self.searchTextField.text)
    }
    
    func textFieldShouldReturn(textField: UITextField) -> Bool
    {
        textField.resignFirstResponder()
        self.delegate?.search(textField.text)
        return true
    }
    
}
