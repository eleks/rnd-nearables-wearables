//
//  Favorites.swift
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/27/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

import UIKit

class Favorites {
    
    private let favoritesKey = "Favorites"
    private var cache: Array<String> = []
    private var queue = dispatch_queue_create("favoritesQueue", DISPATCH_QUEUE_CONCURRENT);
    
    init()
    {
        dispatch_barrier_sync(queue) { () in
            if let storedFavorites = NSUserDefaults.standardUserDefaults().objectForKey(self.favoritesKey) as? [String] {
                self.cache = storedFavorites
            }
            else {
                self.cache = []
            }
        }
    }
    
    func clear()
    {
        dispatch_barrier_async(queue) { () in
            self.cache.removeAll(keepCapacity: false)
            self.saveFavorites()
        }
    }
    
    func getFavorites() -> [String]
    {
        var result: [String]!
        dispatch_sync(queue) { () in
            result = self.cache
        }
        return result
    }
    
    func addFavorite(favorite: String)
    {
        dispatch_barrier_async(queue) { () in
            if !contains(self.cache, favorite) {
                self.cache.append(favorite)
                self.saveFavorites()
            }
        }
    }
    
    func removeFavorite(favorite: String)
    {
        dispatch_barrier_async(queue) { () in
            if let index = find(self.cache, favorite)
            {
                self.cache.removeAtIndex(index)
                self.saveFavorites()
            }
        }
    }
    
    private func saveFavorites()
    {
        NSUserDefaults.standardUserDefaults().setObject(self.cache, forKey: self.favoritesKey)
        NSUserDefaults.standardUserDefaults().synchronize()
    }
    
}
