//
//  RequestsService.swift
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/21/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

import UIKit

typealias RecentItem = (employeeName: String?, location: String?, employeeId: String?, timestamp: Double?)

class SynchronizationService {
    
    private class func createAndResumeDataTaskWithRequest(request: NSURLRequest, completionHandler: ((NSData!, NSURLResponse!, NSError!) -> Void)) -> NSURLSessionDataTask
    {
        let task = NSURLSession.sharedSession().dataTaskWithRequest(request, completionHandler:
            { (data: NSData!, response: NSURLResponse!, error: NSError!) -> Void in
                UIApplication.sharedApplication().hideNetworkSyncActivityIndicator()
                completionHandler(data, response, error)
        })
        
        UIApplication.sharedApplication().showNetworkSyncActivityIndicator()
        task.resume()
        
        return task
    }
    
    
    class func authentificateWithUserName(username: String, password: String, completion: ((String?) -> (Void)))
    {
        let request = NSURLRequest.authentificationRequestWithUsername(username, password: password)
        self.createAndResumeDataTaskWithRequest(request, completionHandler:
            { (data: NSData!, response: NSURLResponse!, error: NSError!) -> Void in
                
                if nil != error {
                    completion(nil)
                    return
                }
                
                let parsedToken = ResponseParser.parseAccessTokenData(data)
                
                completion(parsedToken)
        })
    }
    
    class func getRecentTrackedData(completion: (([RecentItem]?) -> (Void)))
    {
        let token = AuthentificationManager.instance.token
        let username = AuthentificationManager.instance.username
        
        if nil == token || nil == username {
            completion(nil)
            return
        }
        
        let request = NSURLRequest.getRecentRequestWithToken(token!, username: username!)
        self.createAndResumeDataTaskWithRequest(request, completionHandler:
            { (data: NSData!, response: NSURLResponse!, error: NSError!) -> Void in
                
                if nil != error {
                    completion(nil)
                    return
                }
                
                var items = ResponseParser.parseRecentData(data)
                
                items?.sort({ (item1: RecentItem, item2: RecentItem) -> Bool in
                    if (item1.employeeName != nil) && (item2.employeeName != nil) {
                        return item1.employeeName! < item2.employeeName!
                    }
                    return true
                })

                completion(items)
        })
    }
    
    class func getUserImageWithId(id: String, completion: (UIImage?) -> (Void))
    {
        if 0 == count(id) {
            completion(nil)
            return
        }
        
        let request = NSURLRequest.getPhotoRequestWithId(id)
        self.createAndResumeDataTaskWithRequest(request, completionHandler:
            { (data: NSData!, response: NSURLResponse!, error: NSError!) -> Void in
                
            if nil != error {
                completion(nil)
                return
            }
                
            let image = UIImage(data: data, scale: UIScreen.mainScreen().scale)
                
            completion(image)
        })
    }
    
    class func syncCurrentLocationWithId(id: String)
    {
        let token = AuthentificationManager.instance.token
        let username = AuthentificationManager.instance.username
        
        if nil == token || nil == username {
            return
        }
        
        let request = NSURLRequest.getSaveLocationRequestWithNearableId(id, token: token!, username: username!)
        self.createAndResumeDataTaskWithRequest(request, completionHandler:
            { (data: NSData!, response: NSURLResponse!, error: NSError!) -> Void in
                
                if nil != error {
//                    println("error: \(error)")
                    return
                }
        })
    }
}
