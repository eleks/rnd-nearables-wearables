//
//  Connection.m
//  Test
//
//  Created by Bogdan on 12/16/14.
//  Copyright (c) 2015 Bogdan. All rights reserved.
//

#import "Connection.h"
#import <sys/socket.h>
#import <SystemConfiguration/SCNetworkReachability.h>
#include <netinet/in.h>

@implementation Connection

+ (BOOL)isConnectedToNetwork
{
    struct sockaddr_in zero_addr;
    bzero(&zero_addr, sizeof(zero_addr));
    zero_addr.sin_len = sizeof(zero_addr);
    zero_addr.sin_family = AF_INET;
    
    SCNetworkReachabilityRef defaultRouteReachability = SCNetworkReachabilityCreateWithAddress(NULL, (struct sockaddr *) &zero_addr);
    SCNetworkReachabilityFlags flags;
    
    BOOL didRetrieveFlags = SCNetworkReachabilityGetFlags(defaultRouteReachability, &flags);
    CFRelease(defaultRouteReachability);
    
    if (NO == didRetrieveFlags)
    {
        return NO;
    }
    
    BOOL isReachable = flags & kSCNetworkFlagsReachable;
    BOOL isConnectionNeeded = flags & kSCNetworkFlagsConnectionRequired;
    
    return (isReachable && !isConnectionNeeded) ? YES : NO;
}

@end
