//
//  MyTabBarController.m
//  CloudAppTab
//
//  Created by Pat Marion on 10/8/12.
//  Copyright (c) 2012 Pat Marion. All rights reserved.
//

#import "MyTabBarController.h"
#import "MyGLKViewController.h"

@interface MyTabBarController ()

@end

@implementation MyTabBarController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
  [super viewDidLoad];
  
  [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(switchToRenderView:) name:@"switchToRenderView" object:nil];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)switchToRenderView:(NSNotification*)notification
{
  NSDictionary* userInfo = notification.userInfo;

  const int glViewIndex = 1;

  self.selectedIndex = glViewIndex;
  MyGLKViewController* glkView = (MyGLKViewController*)self.selectedViewController;
  [glkView handleArgs:userInfo];
}

@end
