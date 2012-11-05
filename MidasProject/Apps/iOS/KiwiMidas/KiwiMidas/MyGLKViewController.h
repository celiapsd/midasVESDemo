//
//  MyGLKViewController.h
//  CloudAppGL
//
//  Created by Pat Marion on 9/29/12.
//  Copyright (c) 2012 Pat Marion. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <GLKit/GLKit.h>

@interface MyGLKViewController : GLKViewController <UIPopoverControllerDelegate> 


  -(void) handleArgs:(NSDictionary*) args;

  @property (nonatomic, retain) IBOutlet UIBarButtonItem *settingsButton;
  @property (nonatomic, retain) IBOutlet UIToolbar *toolbar;

  @property (nonatomic, retain) IBOutlet UILabel *leftLabel;
  @property (nonatomic, retain) IBOutlet UILabel *rightLabel;


@end
