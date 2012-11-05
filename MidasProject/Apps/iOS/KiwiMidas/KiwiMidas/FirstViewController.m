//
//  FirstViewController.m
//  CloudAppTab
//
//  Created by Pat Marion on 9/29/12.
//  Copyright (c) 2012 Pat Marion. All rights reserved.
//

#import "FirstViewController.h"

@interface FirstViewController ()

@end

@implementation FirstViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction) onReturn: (id) sender {

    NSLog(@"onReturn to: %@", (id)self.presentingViewController);
    
  // Do something here with the variable 'sender'
  [self dismissViewControllerAnimated:YES completion:NULL];

}


@end
