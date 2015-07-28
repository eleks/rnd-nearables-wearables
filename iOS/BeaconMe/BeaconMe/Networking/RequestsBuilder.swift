//
//  Requests.swift
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/21/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

import UIKit

extension NSURLRequest {
    
    enum HTTPMethods : String {
        case Get = "GET"
        case Post = "POST"
    }

    private static let serverURL = "http://172.25.3.68:8080/"
    private static let authentificationCommand = "authenticate"
    private static let recentCommand = "track/recent"
    private static let saveCommmand = "track/save"

    private static let accessTokenHeaderField = "access_token"
    
    private static let accessTokenField    = "accessToken"
    
    private class func commandURLWithName(name: String) -> NSURL
    {
        return NSURL(string: serverURL + name)!
    }
    
    private class func photoCmmandURLWithId(id: String) -> NSURL
    {
        return NSURL(string: serverURL + "employee/\(id)/photo")!
    }
    
    class func authentificationRequestWithUsername(username: String, password: String) -> NSURLRequest
    {
        let request = NSMutableURLRequest()
        
        request.URL = self.commandURLWithName(authentificationCommand)
        request.HTTPMethod = HTTPMethods.Post.rawValue
        
        let usernameHeaderField = "username"
        let passwordHeaderField = "password"
        let body = "\(usernameHeaderField)=\(username)&\(passwordHeaderField)=\(password)"
        
        request.HTTPBody = body.dataUsingEncoding(NSUTF8StringEncoding, allowLossyConversion: false)
        
        return request
    }
    
    class func getRecentRequestWithToken(token: String, username: String) -> NSURLRequest
    {
        let request = NSMutableURLRequest()
        
        request.URL = self.commandURLWithName(recentCommand)
        request.HTTPMethod = HTTPMethods.Get.rawValue
        
        let usernameHeaderField = "user_name"
        let contentTypeHeaderField = "Content-Type"
        let contentTypeValue = "application/json"
        
        request.setValue(token, forHTTPHeaderField: accessTokenHeaderField)
        request.setValue(username, forHTTPHeaderField: usernameHeaderField)
        request.setValue(contentTypeValue, forHTTPHeaderField: contentTypeHeaderField)
        
//        println(request.allHTTPHeaderFields)
        
        return request
    }
    
    class func getPhotoRequestWithId(id: String) -> NSURLRequest
    {
        let request = NSMutableURLRequest()
        
        request.URL = self.photoCmmandURLWithId(id)
        request.HTTPMethod = HTTPMethods.Get.rawValue
        
        return request
    }
    
    class func getSaveLocationRequestWithNearableId(id: String, token: String, username: String) -> NSURLRequest
    {
        let request = NSMutableURLRequest()
        
        request.URL = self.commandURLWithName(saveCommmand)
        request.HTTPMethod = HTTPMethods.Post.rawValue
        
        let usernameHeaderField = "user_name"
        let contentTypeHeaderField = "Content-Type"
        let contentTypeValue = "application/json"
        
        request.setValue(token, forHTTPHeaderField: accessTokenHeaderField)
        request.setValue(username, forHTTPHeaderField: usernameHeaderField)
        request.setValue(contentTypeValue, forHTTPHeaderField: contentTypeHeaderField)
        
//        println(request.allHTTPHeaderFields)
        
        let timestamp = "timestamp"
        let timestampValue = NSDate().timeIntervalSince1970 * 1000 // miliseconds
        let type = "type"
        let typeValue = "IN_RANGE"
        let stikerId = "stikerId"
        let body = "{ \"\(timestamp)\" : \(timestampValue), \"\(type)\" : \"\(typeValue)\", \"\(stikerId)\" : \"\(id)\"}"
        
        request.HTTPBody = body.dataUsingEncoding(NSUTF8StringEncoding, allowLossyConversion: false)
        
//        println("body \(body)")
        
        return request
    }
    
    
}