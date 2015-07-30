//
//  PersonTableViewCell.swift
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/17/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

import UIKit

class PersonTableViewCell: SWTableViewCell {
    
    @IBOutlet weak var photoImageView: UIImageView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var placeLabel: UILabel!
    @IBOutlet weak var timeLabel: UILabel!
    
    @IBOutlet weak var timeWidth: NSLayoutConstraint!
    static let cellID = "PersonTableViewCellIdentifier"
    class func defaultHeight() -> CGFloat
    {
        if DeviceType.IS_IPHONE_6 {
            return 64
        }
        else if DeviceType.IS_IPHONE_6P {
            return 68
        }
        return 60
    }
    
    override func awakeFromNib()
    {
        super.awakeFromNib()
        
        self.selectionStyle = .None
        self.backgroundColor = UIColor.clearColor()
        
        self.titleLabel.font = Style.nameTextFont()
        self.placeLabel.font = Style.locationTextFont()
        self.timeLabel.font = Style.timeTextFont()
        
        self.placeLabel.textColor = Style.tintColor()
        self.timeLabel.textColor = Style.highlightedColor()
        
        if DeviceType.IS_IPHONE_6 {
            self.timeWidth.constant = 84
        }
        else if DeviceType.IS_IPHONE_6P {
            self.timeWidth.constant = 90
        }
    }

    func configure(item: RecentItem, isHighlighted: Bool, delegate: SWTableViewCellDelegate?, isFavorite: Bool)
    {
        self.delegate = delegate
        
        self.titleLabel.textColor = isHighlighted ? UIColor.whiteColor() : Style.textColor()
        
        self.titleLabel.text = item.employeeName
        self.placeLabel.text = item.location
        
        self.timeLabel.text = DataFormatter.humanReadableDateWithTimestamp(item.timestamp)
        self.timeLabel.adjustsFontSizeToFitWidth = true

        self.photoImageView.image = UIImage(named: "user_placeholder")
        
        if let employeeId = item.employeeId {
            ImageProvider.instance.getImageWithUserId(employeeId, completion: { (imageValue: UIImage?) -> (Void) in
                if let image = imageValue {
                    self.photoImageView.image = image
                }
            })
        }
        
        self.setRightUtilityButtons(self.createRightButtonsForFavoriteMode(isFavorite), withButtonWidth: isFavorite ? 90 : 78)
    }
    
    func createRightButtonsForFavoriteMode(isFavorite: Bool) -> [UIButton]
    {
        var array: [UIButton] = []
        
        var button = UIButton.buttonWithType(.Custom) as! UIButton
        button.backgroundColor = Style.tintColor()
        button.setTitle(isFavorite ? "Unfavorite" : "Favorite", forState: .Normal)
        button.titleLabel?.font = Style.nameTextFont()
        button.setTitleColor(UIColor.whiteColor(), forState: .Normal)
        button.titleLabel?.adjustsFontSizeToFitWidth = true
        
        array.append(button)
        
        return array
    }
    
}
