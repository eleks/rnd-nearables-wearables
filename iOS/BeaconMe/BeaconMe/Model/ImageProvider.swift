//
//  ImageProvider.swift
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/22/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

import UIKit

class ImageProvider
{
    class var instance: ImageProvider
    {
        struct Singleton {
            static let privateInstance = ImageProvider()
        }
        return Singleton.privateInstance
    }
    
    private var cache: Dictionary<String, UIImage> = [:]
    private var queue = dispatch_queue_create("cacheQueue", DISPATCH_QUEUE_CONCURRENT);
    
    func getImageWithUserId(id: String, completion: (UIImage?) -> (Void))
    {
        var cachedImage = self.getImageForKey(id)
        
        if nil != cachedImage {
            completion(cachedImage)
            return
        }
        
        SynchronizationService.getUserImageWithId(id, completion: { (receivedImage: UIImage?) -> (Void) in
            dispatch_async(dispatch_get_main_queue(), { () -> Void in
                
                if let image = receivedImage {
                    let side = min(image.size.width, image.size.height) * UIScreen.mainScreen().scale
                    let croppedImage = image.cropToSize(CGSizeMake(side, side))
                    let corneredImage = croppedImage.makeRoundCornersWithRadius(side / (UIScreen.mainScreen().scale))
                    
                    self.setImage(corneredImage, forKey: id)
                    
                    completion(corneredImage)
                }
                else {
                    completion(nil)
                }
            })
        })
    }
    
    private func setImage(image: UIImage, forKey key: String)
    {
        dispatch_barrier_async(queue) { [weak self] in
            self?.cache[key] = image
        }
    }
    
    private func getImageForKey(key: String) -> UIImage?
    {
        var result: UIImage? = nil
        dispatch_sync(queue) { [weak self]  in
            result = self?.cache[key]
        }
        return result
    }

    
}
