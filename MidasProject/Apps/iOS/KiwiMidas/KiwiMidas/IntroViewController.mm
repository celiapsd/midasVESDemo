//
//  IntroViewController.m
//  CloudAppTab
//
//  Created by Pat Marion on 9/29/12.
//  Copyright (c) 2012 Pat Marion. All rights reserved.
//

#import "IntroViewController.h"
#import "TPKeyboardAvoidingScrollView.h"
#import "MyGLKViewController.h"
#import "DocumentsTableViewController.h"


#include <vesMidasClient.h>

@interface IntroViewController () {

  vesMidasClient::Ptr client;
}

@end

@implementation IntroViewController

@synthesize userText;
@synthesize passwordText;
@synthesize scrollView;

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
    
  self.userText.text = [[NSUserDefaults standardUserDefaults] stringForKey:@"Username"];
  self.passwordText.text = [[NSUserDefaults standardUserDefaults] stringForKey:@"Password"];
}

-(BOOL)textFieldShouldReturn:(UITextField *)textField {
  if (textField == userText) {
    [passwordText becomeFirstResponder];
  }
  else if (textField == passwordText) {
    [textField resignFirstResponder];
  }
  else{
    [textField resignFirstResponder];
  }
  return YES;
}

-(void)textFieldDidBeginEditing:(UITextField *)textField
{
  [scrollView adjustOffsetToIdealIfNeeded];
}

- (void)showAlertDialogWithTitle:(NSString *)alertTitle message:(NSString *)alertMessage;
{

  UIAlertView *alert = [[UIAlertView alloc]
                        initWithTitle:alertTitle
                        message:alertMessage
                        delegate:self
                        cancelButtonTitle:@"Ok"
                        otherButtonTitles: nil, nil];
  [alert show];
}

-(IBAction)onCreateAccount:(id)sender
{
  const std::string midasHost = "http://midas3.kitware.com/midas";
  [[UIApplication sharedApplication] openURL:[NSURL URLWithString:[NSString stringWithUTF8String:midasHost.c_str()]]];
}

-(IBAction)onSkipLogin:(id)sender
{
  const std::string midasHost = "http://midas3.kitware.com/midas";

  client = vesMidasClient::Ptr(new vesMidasClient);
  client->setHost(midasHost);
  [self performSegueWithIdentifier: @"gotoTabbed" sender: self];
}

-(IBAction) onLoginTouched: (id) sender {

  NSString* username = self.userText.text;
  NSString* password = self.passwordText.text;
  
  if (!username.length) {
    [self showAlertDialogWithTitle:@"Missing email" message:@"Please enter your email."];
    return;
  }
  if (!password.length) {
    [self showAlertDialogWithTitle:@"Missing password" message:@"Please enter your password."];
    return;
  }

  const std::string midasHost = "http://midas3.kitware.com/midas";

  client = vesMidasClient::Ptr(new vesMidasClient);
  client->setHost(midasHost);
  
  bool loginResult = client->login([username UTF8String], [password UTF8String]);

  if (loginResult) {

    [[NSUserDefaults standardUserDefaults] setObject:username forKey:@"Username"];
    [[NSUserDefaults standardUserDefaults] setObject:password forKey:@"Password"];
    [[NSUserDefaults standardUserDefaults] synchronize];
  
    [self performSegueWithIdentifier: @"gotoTabbed" sender: self];
  }
  else {

    NSString* title = @"Login Failed";
    NSString* message = @"The login to Midas failed.";
    [self showAlertDialogWithTitle:title message:message];
    client.reset();
  }

}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
  UITabBarController* tabController = (UITabBarController*)segue.destinationViewController;

  UINavigationController* navController = (UINavigationController*)[tabController.viewControllers objectAtIndex:0];

  DocumentsTableViewController* documentsController = (DocumentsTableViewController*)navController.topViewController;

  //MyGLKViewController* glkViewController = (MyGLKViewController*)[tabController.viewControllers objectAtIndex:2];
  
  documentsController.client = self->client;
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
