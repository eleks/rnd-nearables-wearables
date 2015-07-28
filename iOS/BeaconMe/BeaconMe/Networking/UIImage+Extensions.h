//
//  UIImage+Extensions.h
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/22/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIImage(Extension)

- (instancetype)cropToSize:(CGSize)size;
- (instancetype)makeRoundCornersWithRadius:(const CGFloat)radious;

@end
