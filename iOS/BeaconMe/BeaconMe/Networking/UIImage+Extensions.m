//
//  UIImage+Extensions.m
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/22/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

#import "UIImage+Extensions.h"

@implementation UIImage(Extension)

- (instancetype)cropToSize:(CGSize)size
{
    // not equivalent to image.size (which depends on the imageOrientation)!
    double refWidth = CGImageGetWidth(self.CGImage);
    double refHeight = CGImageGetHeight(self.CGImage);
    
    double x = (refWidth - size.width) / 2.0;
    double y = (refHeight - size.height) / 2.0;
    
    CGRect cropRect = CGRectMake(x, y, size.height, size.width);
    CGImageRef imageRef = CGImageCreateWithImageInRect([self CGImage], cropRect);
    
    UIImage *cropped = [UIImage imageWithCGImage:imageRef scale:0.0 orientation:self.imageOrientation];
    CGImageRelease(imageRef);
    
    return cropped;
}

- (instancetype)makeRoundCornersWithRadius:(const CGFloat)radious
{
    UIImage *image = self;
    
    // Begin a new image that will be the new image with the rounded corners
    // (here with the size of an UIImageView)
    UIGraphicsBeginImageContextWithOptions(image.size, NO, image.scale);
    
    const CGRect RECT = CGRectMake(0, 0, image.size.width, image.size.height);
    // Add a clip before drawing anything, in the shape of an rounded rect
    [[UIBezierPath bezierPathWithRoundedRect:RECT cornerRadius:radious] addClip];
    // Draw your image
    [image drawInRect:RECT];
    
    // Get the image, here setting the UIImageView image
    //imageView.image
    UIImage* imageNew = UIGraphicsGetImageFromCurrentImageContext();
    
    // Lets forget about that we were drawing
    UIGraphicsEndImageContext();
    
    return imageNew;
}

@end
