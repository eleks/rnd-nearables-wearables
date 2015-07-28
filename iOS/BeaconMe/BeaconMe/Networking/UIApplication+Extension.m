//
//  UIApplication+Extension.m
//  RSSReader
//
//  Created by Bogdan Shubravyi on 6/25/15.
//  Copyright (c) 2015 Bogdan Shubravyi. All rights reserved.
//

#import "UIApplication+Extension.h"

@implementation UIApplication(Extension)

#pragma mark - Activities Counter

NSInteger _networkSyncAtivitiesCounter = 0;

- (NSInteger)getSyncAtivitiesCounterValue
{
    return _networkSyncAtivitiesCounter;
}

- (void)incrementSyncActivitiesCounterByValue:(NSInteger)value
{
    _networkSyncAtivitiesCounter += value;
}

#pragma mark - Show / Hide NetworkActivityIndicator

- (dispatch_queue_t)queue
{
    static dispatch_once_t once;
    static dispatch_queue_t sharedQueue;
    
    dispatch_once(&once, ^ {
        sharedQueue = dispatch_queue_create([NSStringFromClass([self class]) UTF8String], nil);
    });
    return sharedQueue;
}

- (void)showNetworkSyncActivityIndicator
{
    __weak __typeof(self) weakSelf = self;
    dispatch_sync([self queue],
                  ^{
                      [weakSelf incrementSyncActivitiesCounterByValue:1];
                      
                      if ([weakSelf getSyncAtivitiesCounterValue] > 1) {
                          return ;
                      }
                      
                      [[UIApplication sharedApplication] setIdleTimerDisabled:YES];
                      [[UIApplication sharedApplication] setNetworkActivityIndicatorVisible:YES];
    });
}

- (void)hideNetworkSyncActivityIndicator
{
    __weak __typeof(self) weakSelf = self;
    dispatch_sync([self queue],
                  ^{
                      [weakSelf incrementSyncActivitiesCounterByValue:-1];
                      
                      if (0 == [weakSelf getSyncAtivitiesCounterValue])
                      {
                          [[UIApplication sharedApplication] setIdleTimerDisabled:NO];
                          [[UIApplication sharedApplication] setNetworkActivityIndicatorVisible:NO];
                      }
                  });
}

@end
