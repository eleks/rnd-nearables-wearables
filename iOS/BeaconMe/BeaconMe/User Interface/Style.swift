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

private struct ScreenSize
{
    static let SCREEN_WIDTH = UIScreen.mainScreen().bounds.size.width
    static let SCREEN_HEIGHT = UIScreen.mainScreen().bounds.size.height
    static let SCREEN_MAX_LENGTH = max(ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT)
    static let SCREEN_MIN_LENGTH = min(ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT)
}

struct DeviceType
{
    static let IS_IPHONE_4_OR_LESS =  UIDevice.currentDevice().userInterfaceIdiom == .Phone && ScreenSize.SCREEN_MAX_LENGTH < 568.0
    static let IS_IPHONE_5 = UIDevice.currentDevice().userInterfaceIdiom == .Phone && ScreenSize.SCREEN_MAX_LENGTH == 568.0
    static let IS_IPHONE_6 = UIDevice.currentDevice().userInterfaceIdiom == .Phone && ScreenSize.SCREEN_MAX_LENGTH == 667.0
    static let IS_IPHONE_6P = UIDevice.currentDevice().userInterfaceIdiom == .Phone && ScreenSize.SCREEN_MAX_LENGTH == 736.0
}

extension Style {
    
    private class func sizeForCurrentDevice(size: CGFloat) -> CGFloat
    {
        if DeviceType.IS_IPHONE_4_OR_LESS || DeviceType.IS_IPHONE_5 {
            return size
        }
        else if DeviceType.IS_IPHONE_6 {
            return size + 1
        }
        else {
            return size + 2
        }
    }
    
    class func headerFont() -> UIFont
    {
        return UIFont(name: "HelveticaNeue-Medium", size: self.sizeForCurrentDevice(11))!
    }
    
    class func timeTextFont() -> UIFont
    {
        return UIFont(name: "HelveticaNeue-Medium", size: self.sizeForCurrentDevice(12))!
    }
    
    class func nameTextFont() -> UIFont
    {
        return UIFont(name: "HelveticaNeue-Medium", size: self.sizeForCurrentDevice(14))!
    }
    
    class func locationTextFont() -> UIFont
    {
        return UIFont(name: "HelveticaNeue-Medium", size: self.sizeForCurrentDevice(13))!
    }
    
}
