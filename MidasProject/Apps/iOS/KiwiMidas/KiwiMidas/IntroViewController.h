//
//  IntroViewController.h
//  CloudAppTab
//
//  Created by Pat Marion on 9/29/12.
//  Copyright (c) 2012 Pat Marion. All rights reserved.
//

#import <UIKit/UIKit.h>

@class TPKeyboardAvoidingScrollView;


@interface IntroViewController : UIViewController <UITextFieldDelegate> {


}


-(IBAction) onLoginTouched: (id) sender;
-(IBAction) onSkipLogin: (id) sender;
-(IBAction) onCreateAccount: (id) sender;


@property (nonatomic, retain) IBOutlet UITextField *userText;
@property (nonatomic, retain) IBOutlet UITextField *passwordText;
@property (nonatomic, retain) IBOutlet TPKeyboardAvoidingScrollView *scrollView;


@end
