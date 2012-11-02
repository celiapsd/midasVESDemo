//
//  SettingsTableViewController.h
//  KiwiViewerMidas
//
//  Created by Pat Marion on 10/10/12.
//  Copyright (c) 2012 Pat Marion. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SettingsTableViewController : UITableViewController


  -(IBAction) onToggleMultisampling:(id) sender;


  @property (nonatomic, retain) IBOutlet UISwitch *multisamplingSwitch;


@end
