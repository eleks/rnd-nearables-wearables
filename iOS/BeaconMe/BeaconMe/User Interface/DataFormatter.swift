//
//  DataFormatter.swift
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/22/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

import UIKit

class DataFormatter {
    
    class func humanReadableDateWithTimestamp(timestamp: Double?) -> String
    {
        if nil == timestamp {
            return ""
        }
        
        let date = NSDate(timeIntervalSince1970: NSTimeInterval(timestamp!))
        let now  = NSDate()

        let diffInSeconds = abs(date.timeIntervalSinceNow)
        if diffInSeconds <= 60 || (date.compare(now) == NSComparisonResult.OrderedDescending) {
            return "Just Now"
        }
        
        var result = ""
        
        let diffInHours = abs(date.numberOfHoursBetweenDate(now))
        let diffInMinutes = abs(date.numberOfMinutesBetweenDate(now))
        
        if diffInHours > 0 {
            result += "\(diffInHours)h"
        }
        
        if diffInMinutes > 0 {
            result += " \(diffInMinutes)m"
        }
        
        return result + " ago"
    }
    
}

extension NSDate {
    
    func numberOfMinutesBetweenDate(date: NSDate) -> NSInteger
    {
        let calendar = NSCalendar(identifier:  NSGregorianCalendar)!
        calendar.timeZone = NSTimeZone.systemTimeZone()
        
        let componets = calendar.components(NSCalendarUnit.MinuteCalendarUnit | NSCalendarUnit.HourCalendarUnit, fromDate: self, toDate: date, options: NSCalendarOptions.allZeros)
        
        return componets.minute
    }
    
    func numberOfHoursBetweenDate(date: NSDate) -> NSInteger
    {
        let calendar = NSCalendar(identifier:  NSGregorianCalendar)!
        calendar.timeZone = NSTimeZone.systemTimeZone()
        
        let componets = calendar.components(NSCalendarUnit.HourCalendarUnit | NSCalendarUnit.DayCalendarUnit, fromDate: self, toDate: date, options: NSCalendarOptions.allZeros)
        
        return componets.hour
    }
    
}
