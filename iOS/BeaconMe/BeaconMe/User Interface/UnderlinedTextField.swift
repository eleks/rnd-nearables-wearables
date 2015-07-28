//
//  UnderlinedTextField.swift
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/17/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

import UIKit

class UnderlinedTextField: UITextField {
    
    lazy var underlineImageView: UIImageView =
    {
        let view = UIImageView()
        view.contentMode = .ScaleAspectFit
        self.addSubview(view)
        
        return view
    }()
    
    override func layoutSubviews()
    {
        super.layoutSubviews()
        
        let height: CGFloat = 2
        self.underlineImageView.frame = CGRectMake(0, CGRectGetHeight(self.frame) - height, CGRectGetWidth(self.frame), height)
    }
    
}
