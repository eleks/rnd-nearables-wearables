//
//  UITableView+Extensions.m
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/24/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

#import "UITableView+Extensions.h"

@implementation UITableView(ReloadDataAnimated)

- (void)reloadDataAnimated
{
    [self reloadData];
    
    CATransition *animation = [CATransition animation];
    [animation setType:kCATransitionFade];
    [animation setSubtype:kCATransitionFromTop];
    [animation setTimingFunction:[CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionEaseInEaseOut]];
    [animation setFillMode:kCAFillModeBoth];
    [animation setDuration:.2];
    [[self layer] addAnimation:animation forKey:@"UITableViewReloadDataAnimationKey"];
}

@end
