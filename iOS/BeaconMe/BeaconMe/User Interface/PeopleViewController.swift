//
//  PeopleViewController.swift
//  BeaconMe
//
//  Created by Bogdan Shubravyi on 7/25/15.
//  Copyright (c) 2015 Eleks. All rights reserved.
//

import UIKit

typealias SectionData = (title: String?, items: [RecentItem])

class PeopleViewController : UIViewController, SearchViewDelegate {
    
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var searchHeight: NSLayoutConstraint!
    @IBOutlet weak var searchButtonBottom: NSLayoutConstraint!
    @IBOutlet weak var searchButton: UIButton!
    @IBOutlet weak var tableViewBottom: NSLayoutConstraint!
    
    var initialSetupToken: dispatch_once_t = 0
    let refreshControl = UIRefreshControl()
    let favorites = Favorites()
    
    var sourceItems: [RecentItem] = []
    var items: [SectionData] = []
    
    var searchText: String? = nil

    func personDataForIndexPath(path: NSIndexPath) -> RecentItem
    {
        return self.items[path.section].items[path.row]
    }
    
    func isFavoriteItemAtIndexPath(path: NSIndexPath) -> Bool
    {
        let person = self.personDataForIndexPath(path)
        
        let favoriteIds = self.favorites.getFavorites()
        
        if let id = person.employeeId {
            return contains(favoriteIds, id)
        }
        return false
    }
    
    func indexPathForId(id: String?) -> NSIndexPath?
    {
        if let employeeId = id
        {
            for (sectionIndex, section) in enumerate(self.items)
            {
                for (rowIndex, row) in enumerate(section.items)
                {
                    if row.employeeId == employeeId {
                        return NSIndexPath(forRow: rowIndex, inSection: sectionIndex)
                    }
                }
            }

            return nil
        }
        
        return nil
    }
    
    var isSearchModeEnabled = false {
        didSet {
            self.searchText = nil
            self.calculateItemsArray()
            self.animateSearchButton()
            self.animateSearchView()
            self.updateInsets()
            self.tableView.reloadDataAnimated()
        }
    }
    
    var searchView: SearchView? = nil
    var editedCell: PersonTableViewCell? = nil
    let searchInset: CGFloat = 15
    let statusBarHeight: CGFloat = 22
}

extension PeopleViewController {
    
    override func viewWillAppear(animated: Bool)
    {
        self.performInitialSetup()
        
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "keyboardWillShow:", name: UIKeyboardWillShowNotification, object: nil)
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "keyboardWillHide:", name: UIKeyboardWillHideNotification, object: nil)
        
        self.navigationItem.hidesBackButton = true
        self.navigationItem.title = "My Mates"
        
        self.navigationController?.navigationBarHidden = false
        
        self.navigationItem.leftBarButtonItem = UIBarButtonItem(title: "Logout", style: .Plain, target: self, action: "logOut")
        
        self.updateInsets()
    }
    
    func bottomInset() -> CGFloat {
        return searchHeight.constant + searchInset
    }
    
    func updateInsets()
    {
        let top: CGFloat = self.isSearchModeEnabled ? self.headerHeight() - 10 : 0
        self.tableView.contentInset = UIEdgeInsetsMake(top, 0, self.bottomInset()  , 0)
        self.tableView.scrollIndicatorInsets = UIEdgeInsetsMake(top, 0, 0, 0)
        self.tableView.setContentOffset(CGPointMake(0, -top), animated: false)
    }
    
    func refreshData()
    {
        if refreshControl.refreshing  {
            refreshControl.endRefreshing()
        }
        
        SynchronizationService.getRecentTrackedData { (items: [RecentItem]?) -> (Void) in
            dispatch_async(dispatch_get_main_queue(), { () -> Void in
                if let result = items {
                    self.sourceItems = result
                }
                else {
                    self.sourceItems = []
                }
                
                self.calculateItemsArray()
                
                self.updateInsets()
                self.tableView.reloadData()
            })
        }
    }
    
    func filterItemsWithSearchText(text: String?, inArray array: [SectionData]) -> [SectionData]
    {
        if let searchText = text where count(searchText) > 0
        {
            var filtered: [SectionData] = []
            
            for (sectionIndex, section) in enumerate(array)
            {
                var sectionItems: [RecentItem] = []
                for (rowIndex, row) in enumerate(section.items)
                {
                    if nil != row.employeeName?.rangeOfString(searchText, options: .CaseInsensitiveSearch, range: nil, locale: nil) {
                        sectionItems.append(row)
                    }
                }
                filtered.append((title: section.title, items: sectionItems))
            }
            return filtered
        }
        else {
            return array
        }
    }
    
    func calculateItemsArray()
    {
        if 0 == self.sourceItems.count {
            self.items = []
            return
        }
        
        let favoriteIds = self.favorites.getFavorites()
        
        let recentTitle = "Recent Mates"
        let favorTitle = "Favorites"
        
        var filteredFavorites = self.sourceItems.filter({ (element: RecentItem) -> Bool in
            if let id = element.employeeId {
                return contains(favoriteIds, id)
            }
            return false
        })
        
        var filteredRecent = self.sourceItems.filter({ (element: RecentItem) -> Bool in
            if let id = element.employeeId {
                return !contains(favoriteIds, id)
            }
            return false
        })
        
        var allItems: [SectionData] = [(title: favorTitle,  items: filteredFavorites), (title: recentTitle, items: filteredRecent)]
        
        if self.isSearchModeEnabled {
            self.items = self.filterItemsWithSearchText(self.searchText, inArray: allItems)
        }
        else {
            self.items = allItems
        }
    }

    func logOut()
    {
        self.favorites.clear()
        AuthentificationManager.instance.logOut()
        self.navigationController?.popViewControllerAnimated(true)
    }
    
    func performInitialSetup()
    {
        dispatch_once(&initialSetupToken)
        {
            if let navigationBar = self.navigationController?.navigationBar
            {
                let frame = CGRectMake(0, -self.statusBarHeight, CGRectGetWidth(navigationBar.frame), CGRectGetHeight(navigationBar.frame) + self.statusBarHeight)
                
                let backgroundView = UIView(frame: frame)
                backgroundView.backgroundColor = Style.backgroundColor()
                navigationBar.addSubview(backgroundView)
                
                if let sView = NSBundle.mainBundle().loadNibNamed("SearchView", owner: self, options: nil).first as? SearchView
                {
                    sView.delegate = self
                    self.searchView = sView
                    
                    var frame = sView.frame
                    frame.size.width = self.view.frame.size.width
                    frame.origin.y = -frame.size.height - self.statusBarHeight
                    sView.frame = frame
                    
                    navigationBar.addSubview(sView)
                }
                
                self.refreshData()
            }
        }
    }
    
    override func viewDidLoad()
    {
        self.view.backgroundColor = Style.backgroundColor()
        self.tableView.backgroundColor = self.view.backgroundColor
        
        self.tableView.delegate = self
        self.tableView.dataSource = self

        self.tableView.registerNib(UINib(nibName: "PersonTableViewCell", bundle: nil), forCellReuseIdentifier: PersonTableViewCell.cellID)
        self.tableView.separatorColor = UIColor(white: 1, alpha: 0.1)
        
        self.refreshControl.addTarget(self, action: "refreshData", forControlEvents: .ValueChanged)
        self.tableView.insertSubview(self.refreshControl, atIndex: 0)
        
        self.searchButton.backgroundColor = Style.tintColor()
        self.searchButton.layer.masksToBounds = true
        self.searchButton.layer.cornerRadius = self.searchHeight.constant / 2.0
        self.searchButton.setImage(UIImage(named: "Search_icon"), forState: .Normal)
        
        super.viewDidLoad()
    }
    
}


extension PeopleViewController : UITableViewDataSource, UITableViewDelegate {
    
    func headerHeight() -> CGFloat {
        return 30
    }
    
    func tableView(tableView: UITableView, viewForHeaderInSection section: Int) -> UIView?
    {
        let array = self.items[section]
        if 0 == array.items.count {
            return nil
        }
        
        var frame = CGRectMake(0, 0, CGRectGetWidth(tableView.frame), self.headerHeight())
        let view = UIView(frame: frame)
        let leftMargin: CGFloat = 15
        view.backgroundColor = self.tableView.backgroundColor
        frame.origin.x = leftMargin
        let label = UILabel(frame: frame)
        
        label.font = Style.timeTextFont()
        label.textColor = Style.textColor()
        label.text = array.title?.uppercaseString
        
        view.addSubview(label)
        
        return view
    }

    func tableView(tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat
    {
            let array = self.items[section]
            if 0 == array.items.count {
                return 0
            }
            return self.headerHeight()
    }
    
    func numberOfSectionsInTableView(tableView: UITableView) -> Int
    {
        return self.items.count
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int
    {
        return self.items[section].items.count
    }
    
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat
    {
        return PersonTableViewCell.defaultHeight
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell
    {
        let cell = tableView.dequeueReusableCellWithIdentifier(PersonTableViewCell.cellID, forIndexPath: indexPath) as! PersonTableViewCell;
        
        let item = self.personDataForIndexPath(indexPath)
        cell.configure(item,
            isHighlighted: isSearchModeEnabled,
            delegate: self,
            isFavorite: self.isFavoriteItemAtIndexPath(indexPath))
        
        return cell
    }
    
}

extension PeopleViewController : SWTableViewCellDelegate {
    
    func swipeableTableViewCell(cell: SWTableViewCell!, didTriggerRightUtilityButtonWithIndex index: Int)
    {
        let indexPath = self.tableView.indexPathForCell(cell)
        
        if let path = indexPath
        {
            if let id = self.personDataForIndexPath(path).employeeId
            {
                if self.isFavoriteItemAtIndexPath(path) {
                    self.favorites.removeFavorite(id)
                }
                else {
                    self.favorites.addFavorite(id)
                }
                
                self.calculateItemsArray()
                
                if let newIndexPath = self.indexPathForId(id) {
                    
                    self.tableView.beginUpdates()
                    self.tableView.moveRowAtIndexPath(path, toIndexPath: newIndexPath)
                    self.tableView.endUpdates()
            
                    let delayTime = dispatch_time(DISPATCH_TIME_NOW, Int64(0.35 * Double(NSEC_PER_SEC)))
                    dispatch_after(delayTime, dispatch_get_main_queue()) {
                        self.tableView.reloadDataAnimated()
                    }
                }
                else {
                    self.tableView.reloadDataAnimated()
                }
            }
        }
        
        self.closeOpenedCell()
    }
    
    func swipeableTableViewCell(cell: SWTableViewCell!, canSwipeToState state: SWCellState) -> Bool
    {
        if state == SWCellState.CellStateRight && cell != self.editedCell {
            self.closeOpenedCell()
        }
        
        self.editedCell = cell as? PersonTableViewCell
        
        return true
    }
    
    func scrollViewWillBeginDragging(scrollView: UIScrollView)
    {
        if scrollView == self.tableView {
            self.closeOpenedCell()
        }
    }
    
    func closeOpenedCell()
    {
        if let cell = self.editedCell {
            UIView.animateWithDuration(0.2, animations: { () -> Void in
                editedCell?.hideUtilityButtonsAnimated(false)
            })
            self.editedCell = nil
        }
    }
    
}

extension PeopleViewController {
    
    func animateSearchButton(delay: NSTimeInterval = 0)
    {
        var bottom = self.isSearchModeEnabled ? (self.searchHeight.constant + searchInset) : -searchInset
        
        self.searchButtonBottom.constant = bottom
        self.searchButton.setNeedsUpdateConstraints()
     
        UIView.animateWithDuration(0.7, delay: delay, usingSpringWithDamping: 0.5, initialSpringVelocity: 0.5, options: nil, animations: { () -> Void in
            self.searchButton.layoutIfNeeded()
            }, completion: nil)
    }
    
    func animateSearchView()
    {
        let sView = self.searchView!
        sView.superview?.bringSubviewToFront(sView)
        
        var frame = sView.frame
        let margin: CGFloat = 20
        frame.origin.y = self.isSearchModeEnabled ? (-self.statusBarHeight - margin) : (-frame.size.height - self.statusBarHeight)
        
        frame.size.height = self.isSearchModeEnabled ? (SearchView.defaultHeight + margin) : SearchView.defaultHeight
        
        UIView.animateWithDuration(0.7, delay: 0.0, usingSpringWithDamping: 0.5, initialSpringVelocity: 0.5, options: nil, animations: { () -> Void in
            sView.frame = frame
            }, completion: nil)
        
        if self.isSearchModeEnabled {
            sView.searchTextField.becomeFirstResponder()
        }
        else {
            sView.searchTextField.resignFirstResponder()
        }
    }
    
    @IBAction func searchButtonPressed(sender: AnyObject)
    {
        self.searchView?.searchTextField.text = nil
        self.isSearchModeEnabled = true
    }
    
    func cancelSearch()
    {
        self.isSearchModeEnabled = false
    }
    
    func search(text: String)
    {
        self.searchText = text
        self.calculateItemsArray()
        self.tableView.reloadDataAnimated()
    }
    
}

extension PeopleViewController {
    
    override func viewWillDisappear(animated: Bool)
    {
        NSNotificationCenter.defaultCenter().removeObserver(self)
    }
    
    func keyboardWillShow(notification : NSNotification)
    {
        if let userInfo = notification.userInfo
        {
            if let keyboardSize = (userInfo[UIKeyboardFrameEndUserInfoKey] as? NSValue)?.CGRectValue()
            {
                self.tableViewBottom.constant -= keyboardSize.size.height - self.bottomInset()
            }
        }
    }
    
    func keyboardWillHide(notification : NSNotification)
    {
        if let userInfo = notification.userInfo
        {
            if let keyboardSize = (userInfo[UIKeyboardFrameEndUserInfoKey] as? NSValue)?.CGRectValue()
            {
                self.tableViewBottom.constant += keyboardSize.size.height - self.bottomInset()
            }
        }
    }

}
