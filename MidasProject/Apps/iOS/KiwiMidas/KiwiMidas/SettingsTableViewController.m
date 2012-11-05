//
//  SettingsTableViewController.m
//  KiwiViewerMidas
//
//  Created by Pat Marion on 10/10/12.
//  Copyright (c) 2012 Pat Marion. All rights reserved.
//

#import "SettingsTableViewController.h"

@interface SettingsTableViewController ()

@end

@implementation SettingsTableViewController

@synthesize multisamplingSwitch;


- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
  [super viewDidLoad];

  [self.multisamplingSwitch setOn:[[NSUserDefaults standardUserDefaults] boolForKey:@"EnableMultisampling"] animated:NO];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
  [tableView deselectRowAtIndexPath:indexPath animated:YES];

  if (indexPath.section == 1 || indexPath.section == 2) {

    UITableViewCell* tableCell = [self.tableView cellForRowAtIndexPath:indexPath];
    NSString* detailText = tableCell.detailTextLabel.text;

    if ([detailText hasPrefix:@"kiwiviewer.org"]
        || [detailText hasPrefix:@"vtk.org"]) {
      detailText = [NSString stringWithFormat:@"http://%@", detailText];
     [[UIApplication sharedApplication] openURL:[NSURL URLWithString:detailText]];
    }
  }

}

-(IBAction) onToggleMultisampling:(id) sender
{
  [[NSUserDefaults standardUserDefaults] setBool:self.multisamplingSwitch.on forKey:@"EnableMultisampling"];
  [[NSUserDefaults standardUserDefaults] synchronize];
  [[NSNotificationCenter defaultCenter] postNotificationName:@"EnableMultisamplingChanged" object:nil];
}

@end
