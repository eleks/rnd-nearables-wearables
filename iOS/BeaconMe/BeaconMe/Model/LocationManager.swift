//
//  LocationManager.swift
//  Rooms
//
//  Created by Bogdan on 5/22/15.
//  Copyright (c) 2015 shubravyi.b@gmail.com. All rights reserved.
//

import UIKit

class LocationManager : NSObject, ESTNearableManagerDelegate
{
    class var instance: LocationManager
    {
        struct Singleton {
            static let privateInstance = LocationManager()
        }
        return Singleton.privateInstance
    }
    
    func startMonitoring()
    {
        ESTCloudManager.setupAppID("BeaconMe", andAppToken: "7b3b19a9a419942d8464bf66b1103d6f")
        
        var knownLocations = [String]()
        knownLocations.append("b4aaa62e03d1dcef")
        knownLocations.append("eeb9df1ba9158f63")
        knownLocations.append("f65bb59e07eb23ab")
        knownLocations.append("0832bc783a80b289")
        knownLocations.append("e59b0d69306fab9f")
        knownLocations.append("b46800606cb58677")
        knownLocations.append("fe38f1d872498ad0")
        knownLocations.append("0e2c0ec93085ce6a")
    
        for id in knownLocations {
            self.nearableManager.startMonitoringForIdentifier(id)
        }
        
        // doesn't work in background
//        self.nearableManager.startRangingForType(.All)
//        self.nearableManager.startMonitoringForType(.All)
    }
    
    lazy var nearableManager : ESTNearableManager = {
        let manager = ESTNearableManager()
        manager.delegate = self
        return manager
    }()
    
    // MARK: - ESTNearableManagerDelegate
    func nearableManager(manager: ESTNearableManager!, didRangeNearables nearables: [AnyObject]!, withType type: ESTNearableType)
    {

    }
    
    func nearableManager(manager: ESTNearableManager!, didRangeNearable nearable: ESTNearable!)
    {

    }
    
    func nearableManager(manager: ESTNearableManager!, didEnterIdentifierRegion identifier: String!)
    {
//        println("didEnterIdentifierRegion \(identifier)")
        
        /*
        var notification:UILocalNotification = UILocalNotification();
        notification.alertBody = "Enter region: \(identifier)"
        UIApplication.sharedApplication().presentLocalNotificationNow(notification)
        */
        
        SynchronizationService.syncCurrentLocationWithId(identifier)
    }
    
    func nearableManager(manager: ESTNearableManager!, didExitIdentifierRegion identifier: String!)
    {
        /*
        var notification:UILocalNotification = UILocalNotification();
        notification.alertBody = "Exit region: \(identifier)"
        UIApplication.sharedApplication().presentLocalNotificationNow(notification)
        */
    }
    
}
