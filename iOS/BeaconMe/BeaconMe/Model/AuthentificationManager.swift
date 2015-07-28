//
//  AuthentificationManager.swift
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/16/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

import UIKit

class AuthentificationManager {

    class var instance: AuthentificationManager
    {
        struct Singleton {
            static let privateInstance = AuthentificationManager()
        }
        return Singleton.privateInstance
    }
    
    private let tokenKey    = "accessToken"
    private let usernameKey = "username"
    
    init()
    {
        self.token = KeychainWrapper.stringForKey(tokenKey)
        self.username = KeychainWrapper.stringForKey(usernameKey)
        
//        println("token: \(token) username \(username)")
    }
    
    var token : String? = nil {
        didSet {
            self.setKeychainValue(token, forKey: tokenKey)
        }
    }
    
    var username : String? = nil {
        didSet {
            self.setKeychainValue(username, forKey: usernameKey)
        }
    }
    
    private func setKeychainValue(text: String?, forKey key: String)
    {
        if let value = text where count(value) > 0 {
            KeychainWrapper.setString(value, forKey: key)
        }
        else {
            KeychainWrapper.setString("", forKey: key)
        }
    }
    
    func logInWithUsername(username: String, password: String, completion: ((Bool) -> Void))
    {
        if self.isLoggedIn {
            completion(false)
            return
        }
        
        SynchronizationService.authentificateWithUserName(username, password: password) { (parsedToken: String?) -> (Void) in
            
            if let token = parsedToken
            {
                self.token = token
                self.username = username
                
                completion(true)
                return
            }
            completion(false)
        }
    }
    
    func logOut()
    {
        self.token = nil
        self.username = nil
    }
    
    var isLoggedIn : Bool
    {
        get {
            if let tokenValue = token {
                return count(tokenValue) > 0
            }
            return false
        }
    }
    
}
