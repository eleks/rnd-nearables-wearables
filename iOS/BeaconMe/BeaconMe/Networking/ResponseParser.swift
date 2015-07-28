//
//  JSONDeserializer.swift
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/16/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

import UIKit

class ResponseParser {
    
    class func parseAccessTokenData(data: NSData?) -> String?
    {
        if nil == data {
            return nil
        }
        
        var error: NSError?
        let result :AnyObject? = NSJSONSerialization.JSONObjectWithData(data!, options: nil, error:&error)
        
        if nil == result || nil != error {
            return nil
        }
        
        var dictionary = result as? Dictionary<String, String>
        if nil == dictionary {
            return nil
        }
        
        let token = dictionary!["accessToken"]
        
        return token
    }
    
    class func parseRecentData(data: NSData?) -> [RecentItem]?
    {
        if nil == data {
            return nil
        }
        
        var error: NSError?
        let result :AnyObject? = NSJSONSerialization.JSONObjectWithData(data!, options: nil, error:&error)
        
        if nil == result || nil != error {
            return nil
        }
        
        var array = result as? Array<Dictionary<String, AnyObject>>
        if nil == array {
            return nil
        }
        
        var items = [RecentItem]()
        
        for dictionary in array!
        {
            var item = self.parseRecentItem(dictionary)
            items.append(item)
        }
        
        return items
    }
    
    private class func parseRecentItem(item: Dictionary<String, AnyObject>) -> RecentItem
    {
        var name: String? = nil
        if let value = item["employeeName"] as? String {
            name = value
        }
        
        var location: String? = nil
        if let value = item["location"] as? String {
            location = value
        }
        
        var employeeId: String? = nil
        if let value = item["employeeId"] as? NSNumber {
            employeeId = value.stringValue
        }
        
        var timestamp: Double? = nil
        if let value = item["timestamp"] as? NSNumber {
            timestamp = value.doubleValue / 1000.0 // miliseconds
        }
//        println("\(name) id : \(employeeId)")
        
        return (employeeName: name, location: location, employeeId: employeeId, timestamp: timestamp)
    }
    
}
