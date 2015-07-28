//
//  Style.swift
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/15/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

import UIKit

class Style {
    
    class func setupAppearance()
    {
        UIApplication.sharedApplication().statusBarStyle = .LightContent
        UITextField.appearance().tintColor = self.tintColor()
        
        UIBarButtonItem.appearance().setTitleTextAttributes([NSForegroundColorAttributeName : self.textColor()], forState: .Normal)

        UINavigationBar.appearance().setBackgroundImage(UIImage(), forBarPosition: .Any, barMetrics: .Default)
        UINavigationBar.appearance().shadowImage = UIImage()
        UINavigationBar.appearance().barTintColor = self.backgroundColor()
        UINavigationBar.appearance().titleTextAttributes = [NSForegroundColorAttributeName : UIColor.whiteColor()]
    }
}

extension Style {
    
    private class func rgbaColor(#red: Int, green: Int, blue: Int, alpha: Int) -> UIColor
    {
        return UIColor(red: CGFloat(red) / 255.0, green: CGFloat(green) / 255.0,  blue: CGFloat(blue) / 255.0, alpha: CGFloat(alpha))
    }
    
    class func tintColor() -> UIColor
    {
        return rgbaColor(red: 73, green: 189, blue: 246, alpha: 1)
    }
    
    class func backgroundColor() -> UIColor
    {
        return rgbaColor(red: 29, green: 30, blue: 48, alpha: 1)
    }
    
    class func highlightedColor() -> UIColor
    {
        return rgbaColor(red: 252, green: 153, blue: 2, alpha: 1)
    }
    
    class func textColor() -> UIColor
    {
        return rgbaColor(red: 187, green: 188, blue: 190, alpha: 1)
    }

}

extension Style {
    
    class func headerFont() -> UIFont
    {
        return UIFont(name: "HelveticaNeue-Medium", size: 11)!
    }
    
    class func timeTextFont() -> UIFont
    {
        return UIFont(name: "HelveticaNeue-Medium", size: 12)!
    }
    
    class func nameTextFont() -> UIFont
    {
        return UIFont(name: "HelveticaNeue-Medium", size: 14)!
    }
    
    class func locationTextFont() -> UIFont
    {
        return UIFont(name: "HelveticaNeue-Medium", size: 13)!
    }
    
}
