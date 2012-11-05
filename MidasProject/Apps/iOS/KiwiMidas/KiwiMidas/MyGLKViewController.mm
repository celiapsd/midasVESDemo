//
//  MyGLKViewController.m
//  CloudAppGL
//
//  Created by Pat Marion on 9/29/12.
//  Copyright (c) 2012 Pat Marion. All rights reserved.
//

#import "MyGLKViewController.h"

#include "kiwiCloudApp.h"


@interface kwGestureDelegate : NSObject <UIGestureRecognizerDelegate>{

}
@end

@implementation kwGestureDelegate

- (BOOL)gestureRecognizerShouldBegin:(UIGestureRecognizer *)gestureRecognizer
{
  return YES;
}

- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldRecognizeSimultaneouslyWithGestureRecognizer:(UIGestureRecognizer *)otherGestureRecognizer
{
  BOOL rotating2D =
  [gestureRecognizer isMemberOfClass:[UIRotationGestureRecognizer class]] ||
  [otherGestureRecognizer isMemberOfClass:[UIRotationGestureRecognizer class]];

  BOOL pinching =
  [gestureRecognizer isMemberOfClass:[UIPinchGestureRecognizer class]] ||
  [otherGestureRecognizer isMemberOfClass:[UIPinchGestureRecognizer class]];

  BOOL panning =
  [gestureRecognizer numberOfTouches] == 2 &&
  ([gestureRecognizer isMemberOfClass:[UIPanGestureRecognizer class]] ||
   [otherGestureRecognizer isMemberOfClass:[UIPanGestureRecognizer class]]);

  if ((pinching && panning) ||
      (pinching && rotating2D) ||
      (panning && rotating2D))
    {
    return YES;
    }
  return NO;
}
- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldReceiveTouch:(UITouch *)touch
{
  if ([touch.view isKindOfClass:[UIControl class]]
      || [touch.view isKindOfClass:[UIBarItem class]]
      || [touch.view isKindOfClass:[UIToolbar class]]) {
    return NO;
  }
  return YES;
}
@end



@interface MyGLKViewController () {


  kiwiApp::Ptr mKiwiApp;

  
  kwGestureDelegate* _gestureDelegate;
  
  __weak UIPopoverController *myPopover;

}

@property (strong, nonatomic) EAGLContext *context;

- (void)setupGL;
- (void)tearDownGL;

@end

@implementation MyGLKViewController

@synthesize settingsButton;
@synthesize toolbar;
@synthesize leftLabel;
@synthesize rightLabel;

- (void)viewDidLoad
{
  [super viewDidLoad];

  self.context = [[EAGLContext alloc] initWithAPI:kEAGLRenderingAPIOpenGLES2];

  if (!self.context) {
      NSLog(@"Failed to create ES context");
  }

  GLKView *view = (GLKView *)self.view;
  view.context = self.context;
  view.drawableDepthFormat = GLKViewDrawableDepthFormat24;
  [self onMultisamplingChanged];

  [self createDefaultApp];
  [self createGestureRecognizers];

  [self populateToolbar];

  [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(onMultisamplingChanged)
                                        name:@"EnableMultisamplingChanged" object:nil];
}

-(void) clearToolbar
{
  NSMutableArray *items = [NSMutableArray new];
  [self.toolbar setItems:[items copy] animated:NO];
}

- (void) populateToolbar
{
  std::vector<std::string> actions = self->mKiwiApp->actions();

  NSMutableArray *items = [NSMutableArray arrayWithCapacity:actions.size()];

  for (size_t i = 0; i < actions.size(); ++i) {

    printf("adding action: %s\n", actions[i].c_str());
    UIBarButtonItem * actionButton = [[UIBarButtonItem alloc]
      initWithTitle:[NSString stringWithUTF8String:actions[i].c_str()]
      style:UIBarButtonItemStyleBordered
      target:self
      action:@selector(onAction:)];

    [items addObject:actionButton];
  }


  [items addObject:[[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace
                                            target:nil action:nil]];
  [items addObject:settingsButton];
  [self.toolbar setItems:[items copy] animated:NO];
}

-(void) onAction:(UIBarButtonItem*)button
{
  std::string action = [button.title UTF8String];
  if (self->mKiwiApp) {
    self->mKiwiApp->onAction(action);
  }
}

-(void) onMultisamplingChanged
{
  GLKView *view = (GLKView *)self.view;
  if ([[NSUserDefaults standardUserDefaults] boolForKey:@"EnableMultisampling"]) {
    view.drawableMultisample = GLKViewDrawableMultisample4X;
  }
  else {
    view.drawableMultisample = GLKViewDrawableMultisampleNone;
  }
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
  myPopover = [(UIStoryboardPopoverSegue *)segue popoverController];
  myPopover.delegate = self;
}

- (BOOL)popoverControllerShouldDismissPopover:(UIPopoverController *)popoverController
{
  return YES;
}

- (BOOL)shouldPerformSegueWithIdentifier:(NSString *)identifier sender:(id)sender {

  if (myPopover) {
    [myPopover dismissPopoverAnimated:YES];
    return NO;
  }
  else {
    return YES;
  }
}


-(void) doPVWebDemo
{
  std::string host = "paraviewweb.kitware.com";
  std::string sessionId = "91364400c0b431fa35d7dbf0bbd84d27-844";

  self->mKiwiApp->doPVWebTest(host, sessionId);
}

-(void) doPVRemoteControl
{
  std::string host = "trisol";
  int port = 40000;
  self->mKiwiApp->doPVRemote(host, port);
}

-(void) doPointCloudStreamingDemo
{
  std::string host = "trisol.local";
  int port = 11111;

  self->mKiwiApp.reset();

  vesKiwiPointCloudApp::Ptr streamingApp = vesKiwiPointCloudApp::Ptr(new vesKiwiPointCloudApp);
  streamingApp->setHost(host);
  streamingApp->setPort(port);

  self->mKiwiApp = streamingApp;
  [self setupGL];
}

-(void) createDefaultApp
{
  self->mKiwiApp.reset();
  self->mKiwiApp = kiwiCloudApp::Ptr(new kiwiCloudApp);
  [self setupGL];
}

-(void) handleArgs:(NSDictionary*) args
{

  NSString* dataset = [args objectForKey:@"dataset"];  
  if (dataset) {

    [self clearToolbar];
  
    if ([dataset isEqualToString:@"ParaView Web"]) {
      [self doPVWebDemo];
      return;
    }
    else if ([dataset isEqualToString:@"ParaView Remote Control"]) {
      [self doPVRemoteControl];
      return;
    }
    else if ([dataset isEqualToString:@"Point Cloud Streaming Demo"]) {
      [self doPointCloudStreamingDemo];
      return;
    }


    [self createDefaultApp];
    self->mKiwiApp->loadDataset([dataset UTF8String]);
    self->mKiwiApp->resetView();
    [self populateToolbar];
  }

}

- (void)createGestureRecognizers
{  
  UIPanGestureRecognizer *singleFingerPanGesture = [[UIPanGestureRecognizer alloc]
                                                    initWithTarget:self action:@selector(handleSingleFingerPanGesture:)];
  [singleFingerPanGesture setMinimumNumberOfTouches:1];
  [singleFingerPanGesture setMaximumNumberOfTouches:1];
  [self.view addGestureRecognizer:singleFingerPanGesture];

  UIPanGestureRecognizer *doubleFingerPanGesture = [[UIPanGestureRecognizer alloc]
                                                    initWithTarget:self action:@selector(handleDoubleFingerPanGesture:)];
  [doubleFingerPanGesture setMinimumNumberOfTouches:2];
  [doubleFingerPanGesture setMaximumNumberOfTouches:2];
  [self.view addGestureRecognizer:doubleFingerPanGesture];


  UIPinchGestureRecognizer *pinchGesture = [[UIPinchGestureRecognizer alloc]
                                            initWithTarget:self action:@selector(handlePinchGesture:)];
  [self.view addGestureRecognizer:pinchGesture];


  UIRotationGestureRecognizer *rotate2DGesture = [[UIRotationGestureRecognizer alloc]
                                                  initWithTarget:self action:@selector(handle2DRotationGesture:)];
  [self.view addGestureRecognizer:rotate2DGesture];


  UITapGestureRecognizer *tapGesture = [[UITapGestureRecognizer alloc]
                                             initWithTarget:self action:@selector(handleTapGesture:)];
  tapGesture.numberOfTapsRequired = 1;
  [self.view addGestureRecognizer:tapGesture];


  UITapGestureRecognizer *doubleTapGesture = [[UITapGestureRecognizer alloc]
                                             initWithTarget:self action:@selector(handleDoubleTapGesture:)];
  doubleTapGesture.numberOfTapsRequired = 2;
  [self.view addGestureRecognizer:doubleTapGesture];
  [tapGesture requireGestureRecognizerToFail:doubleTapGesture];


  UILongPressGestureRecognizer* longPress = [[UILongPressGestureRecognizer alloc]
                                              initWithTarget:self action:@selector(handleLongPress:)];
  [self.view addGestureRecognizer:longPress];


  //
  // allow two-finger gestures to work simultaneously
  kwGestureDelegate* gestureDelegate = [[kwGestureDelegate alloc] init];
  [rotate2DGesture setDelegate:gestureDelegate];
  [pinchGesture setDelegate:gestureDelegate];
  [doubleFingerPanGesture setDelegate:gestureDelegate];

  [singleFingerPanGesture setDelegate:gestureDelegate];
  [tapGesture setDelegate:gestureDelegate];
  [doubleTapGesture setDelegate:gestureDelegate];

  self->_gestureDelegate = gestureDelegate;

  [tapGesture setDelegate:gestureDelegate];
  [doubleTapGesture setDelegate:gestureDelegate];
}

- (IBAction)handleSingleFingerPanGesture:(UIPanGestureRecognizer *)sender
{    
  if (sender.state == UIGestureRecognizerStateEnded ||
      sender.state == UIGestureRecognizerStateCancelled)
    {
    self->mKiwiApp->handleSingleTouchUp();
    return;
    }

  //
  // get current translation and (then zero it out so it won't accumulate)
  CGPoint currentTranslation = [sender translationInView:self.view];
  CGPoint currentLocation = [sender locationInView:self.view];
  [sender setTranslation:CGPointZero inView:self.view];

  if (sender.state == UIGestureRecognizerStateBegan)
    {
    self->mKiwiApp->handleSingleTouchDown(currentLocation.x, currentLocation.y);
    return;
    }

  self->mKiwiApp->handleSingleTouchPanGesture(currentTranslation.x, currentTranslation.y);
}

- (IBAction)handleDoubleFingerPanGesture:(UIPanGestureRecognizer *)sender
{
  if (sender.state == UIGestureRecognizerStateEnded ||
      sender.state == UIGestureRecognizerStateCancelled)
    {
    return;
    }

  //
  // get current translation and (then zero it out so it won't accumulate)
  CGPoint currentLocation = [sender locationInView:self.view];
  CGPoint currentTranslation = [sender translationInView:self.view];
  [sender setTranslation:CGPointZero inView:self.view];

  //
  // compute the previous location (have to flip y)
  CGPoint previousLocation;
  previousLocation.x = currentLocation.x - currentTranslation.x;
  previousLocation.y = currentLocation.y + currentTranslation.y;

  self->mKiwiApp->handleTwoTouchPanGesture(previousLocation.x, previousLocation.y, currentLocation.x, currentLocation.y);
}

- (IBAction)handlePinchGesture:(UIPinchGestureRecognizer *)sender
{
  if (sender.state == UIGestureRecognizerStateEnded ||
      sender.state == UIGestureRecognizerStateCancelled)
    {
    return;
    }

  self->mKiwiApp->handleTwoTouchPinchGesture(sender.scale);

  //
  // reset scale so it won't accumulate
  sender.scale = 1.0;
}

- (IBAction)handle2DRotationGesture:(UIRotationGestureRecognizer *)sender
{
  if (sender.state == UIGestureRecognizerStateEnded ||
      sender.state == UIGestureRecognizerStateCancelled)
    {
    return;
    }

  self->mKiwiApp->handleTwoTouchRotationGesture(sender.rotation);

  //
  // reset rotation so it won't accumulate
  [sender setRotation:0.0];
}

- (IBAction)handleDoubleTapGesture:(UITapGestureRecognizer *)sender
{
  CGPoint currentLocation = [sender locationInView:self.view];
  self->mKiwiApp->handleDoubleTap(currentLocation.x, currentLocation.y);
}

- (IBAction)handleTapGesture:(UITapGestureRecognizer *)sender
{
  [myPopover dismissPopoverAnimated:YES];

  CGPoint currentLocation = [sender locationInView:self.view];
  self->mKiwiApp->handleSingleTouchTap(currentLocation.x, currentLocation.y);
}

- (IBAction)handleLongPress:(UITapGestureRecognizer *)sender
{
  CGPoint currentLocation = [sender locationInView:self.view];
  self->mKiwiApp->handleLongPress(currentLocation.x, currentLocation.y);
}




- (void)dealloc
{    
  [self tearDownGL];
  
  if ([EAGLContext currentContext] == self.context) {
      [EAGLContext setCurrentContext:nil];
  }
}

- (void)didReceiveMemoryWarning
{
  [super didReceiveMemoryWarning];

  if ([self isViewLoaded] && ([[self view] window] == nil)) {
    self.view = nil;
    
    [self tearDownGL];
    
    if ([EAGLContext currentContext] == self.context) {
        [EAGLContext setCurrentContext:nil];
    }
    self.context = nil;
  }

  // Dispose of any resources that can be recreated.
}

- (void)setupGL
{
  [EAGLContext setCurrentContext:self.context];  
  self->mKiwiApp->initGL();
  self->mKiwiApp->resizeView(self.view.bounds.size.width, self.view.bounds.size.height);
  self->mKiwiApp->setDefaultBackgroundColor();
}

- (void)tearDownGL
{
  [EAGLContext setCurrentContext:self.context];

  // free GL resources
  // ...
}


- (void)viewWillLayoutSubviews
{
  self->mKiwiApp->resizeView(self.view.bounds.size.width, self.view.bounds.size.height);
}

#pragma mark - GLKView and GLKViewController delegate methods


- (void)update
{
  //double elapsedTime = self.timeSinceLastUpdate;

  if (self->mKiwiApp) {
    std::string leftText = self->mKiwiApp->leftText();
    std::string rightText = self->mKiwiApp->rightText();
    self.leftLabel.text = [NSString stringWithUTF8String:leftText.c_str()];
    self.rightLabel.text = [NSString stringWithUTF8String:rightText.c_str()];
  }
}

- (void)glkView:(GLKView *)view drawInRect:(CGRect)rect
{
  if (self->mKiwiApp) {
    self->mKiwiApp->render();
  }
}

@end