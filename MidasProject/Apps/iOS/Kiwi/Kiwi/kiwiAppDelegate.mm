/*========================================================================
  VES --- VTK OpenGL ES Rendering Toolkit

      http://www.kitware.com/ves

  Copyright 2011 Kitware, Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 ========================================================================*/

#import "kiwiAppDelegate.h"
#import "GLViewController.h"
#import "EAGLView.h"
#import "InfoView.h"
#import "TitleBarViewContainer.h"

#include "vesKiwiViewerApp.h"

@implementation kiwiAppDelegate

@synthesize window;
@synthesize glView;
@synthesize viewController;
@synthesize dataLoader = _dataLoader;
@synthesize loadDataPopover = _loadDataPopover;
@synthesize lastPVWebHost;
@synthesize lastPVWebSessionId;
@synthesize pvwebDialog;
@synthesize openUrlDialog;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  self->waitDialog = nil;
  self->myQueue = dispatch_queue_create("com.kitware.KiwiViewer.myqueue", 0);

  self.window.rootViewController = self.viewController;
  NSURL *url = (NSURL *)[launchOptions valueForKey:UIApplicationLaunchOptionsURLKey];
  [self handleUrl:url];
  return YES;
}

- (void)dealloc
{
  self.loadDataPopover = nil;
  self.pvwebDialog = nil;
  self.openUrlDialog = nil;

  [window release];
  [glView release];
  [viewController release];
  [_dataLoader release];
  [super dealloc];
}

- (void)applicationWillResignActive:(UIApplication *)application
{
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
}

- (void)applicationWillTerminate:(UIApplication *)application
{
}

-(IBAction)reset:(UIButton*)sender
{
  [glView resetView];
}

-(IBAction)information:(UIButton*)sender
{
  InfoView *infoView = [[[InfoView alloc] initWithFrame:CGRectMake(0,0,320,260)] autorelease];

  if(UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone)
    {
    TitleBarViewContainer* container = [TitleBarViewContainer new];
    infoView.contentView.backgroundColor = [UIColor colorWithWhite:1.0 alpha:0.0];
    [container addViewToContainer:infoView];
    container.previousViewController = self.window.rootViewController;
    [self.viewController presentModalViewController:container animated:YES];
    }
  else
    {
    UIViewController* newController = [UIViewController new];
    newController.view = infoView;
    UIPopoverController *popover = [[UIPopoverController alloc] initWithContentViewController:newController];
    [popover setPopoverContentSize:CGSizeMake(320,260) animated:NO];
    [popover presentPopoverFromRect:[(UIButton*)sender frame] inView:self.glView
           permittedArrowDirections:(UIPopoverArrowDirectionDown)
                           animated:NO];

    self.viewController.infoPopover = popover;
    }

  [infoView updateModelInfoLabelWithNumFacets:[self.glView getNumberOfFacetsForCurrentModel]
                                 withNumLines:[self.glView getNumberOfLinesForCurrentModel]
                              withNumVertices:[self.glView getNumberOfVerticesForCurrentModel]
                       withCurrentRefreshRate:[self.glView currentRefreshRate]];
}

- (BOOL)application:(UIApplication *)application handleOpenURL:(NSURL *)url
{
  return [self handleUrl:url];
}

-(NSString*) documentsDirectory
{
  NSArray* documentDirectories = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
  return [documentDirectories objectAtIndex:0];
}

-(NSString*) pathInDocumentsDirectoryForFileName:(NSString*) fileName
{
  return [[self documentsDirectory] stringByAppendingPathComponent:fileName];
}

-(void)showHeadImageDialog
{
  NSString* title = @"CT Image";
  NSString* message = @"About the CT image";
  [self showAlertDialogWithTitle:title message:message];
}

-(void)showBrainAtlasDialog
{
  NSString* title = @"SPL-PNL Brain Atlas";
  NSString* message = @"The brain atlas is distributed under the Slicer license.  For more information please visit http://www.slicer.org/publications/item/view/1265\n\nInteractions:\n\n-single tap a model to hide it\n\n-double tap a model to single it out\n\n-long press anywhere to show all models again\n\n-touch and drag the clip plane to move it\n\n-touch and drag the handle to rotate the clip plane\n\n-tap the top left corner of the screen to hide/show the clip plane\n\n";
  [self showAlertDialogWithTitle:title message:message];
}

-(void)showCanDialog
{
  NSString* title = @"Can";
  NSString* message = @"About the can simulation";
  [self showAlertDialogWithTitle:title message:message];
}

-(void)showPVWebDialog
{
  self.pvwebDialog = [[UIAlertView alloc] initWithTitle:@"Join ParaView Web session"
                                             message:@"Enter host and session id:"
                                             delegate:self
                                             cancelButtonTitle:@"Cancel"
                                             otherButtonTitles:@"Join",nil];
  [self.pvwebDialog release];

  self.pvwebDialog.alertViewStyle = UIAlertViewStyleLoginAndPasswordInput;
  UITextField* hostTextField = [self.pvwebDialog textFieldAtIndex:0];
  UITextField* sessionIdTextField = [self.pvwebDialog textFieldAtIndex:1];

  hostTextField.keyboardType = UIKeyboardTypeURL;
  hostTextField.placeholder = @"paraviewweb.kitware.com";
  hostTextField.text = @"paraviewweb.kitware.com";
  if (self.lastPVWebHost != nil && [self.lastPVWebHost length])
    {
    hostTextField.placeholder = self.lastPVWebHost;
    hostTextField.text = self.lastPVWebHost;
    }

  sessionIdTextField.keyboardType = UIKeyboardTypeNumberPad;
  sessionIdTextField.secureTextEntry = FALSE;
  sessionIdTextField.placeholder = @"session id";
  if (self.lastPVWebSessionId != nil && [self.lastPVWebSessionId length])
    {
    sessionIdTextField.placeholder = self.lastPVWebSessionId;
    sessionIdTextField.text = self.lastPVWebSessionId;
    }

  [self.pvwebDialog show];
}

-(void)showOpenUrlDialog:(NSString*)defaultUrl
{
  self.openUrlDialog = [[UIAlertView alloc] initWithTitle:@"Open URL"
                                             message:@"Enter http url:"
                                             delegate:self
                                             cancelButtonTitle:@"Cancel"
                                             otherButtonTitles:@"Ok",nil];
  [self.openUrlDialog release];

  self.openUrlDialog.alertViewStyle = UIAlertViewStylePlainTextInput;
  UITextField * alertTextField = [self.openUrlDialog textFieldAtIndex:0];
  alertTextField.keyboardType = UIKeyboardTypeURL;
  if (defaultUrl == nil)
    {
    defaultUrl = @"http://";
    }

  alertTextField.text = defaultUrl;

  [self.openUrlDialog show];
}

-(void) showErrorDialog
{
  vesKiwiViewerApp* app = [glView getApp];
  NSString* errorTitle = [NSString stringWithUTF8String:app->loadDatasetErrorTitle().c_str()];
  NSString* errorMessage = [NSString stringWithUTF8String:app->loadDatasetErrorMessage().c_str()];
  [self showAlertDialogWithTitle:errorTitle message:errorMessage];
}

-(void) showWaitDialogWithMessage:(NSString*) message
{
  if (self->waitDialog != nil) {
    self->waitDialog.title = message;
    return;
  }

  self->waitDialog = [[UIAlertView alloc]
    initWithTitle:message message:nil
    delegate:self cancelButtonTitle:nil otherButtonTitles: nil];
  [self->waitDialog show];
}

-(void) showWaitDialog
{
  [self showWaitDialogWithMessage:@"Opening data..."];
}

-(void) dismissWaitDialog
{
  if (self->waitDialog == nil) {
    return;
  }
  [self->waitDialog dismissWithClickedButtonIndex:0 animated:YES];
  [self->waitDialog release];
  self->waitDialog = nil;
}

-(void) postLoadDataset:(NSString*)filename result:(BOOL)result
{
  [self dismissWaitDialog];
  if (!result) {
    [self showErrorDialog];
    return;
  }

  if ([filename hasSuffix:@"model_info.txt"]) {
    [self showBrainAtlasDialog];
  }
  else if ([filename hasSuffix:@"can0000.vtp"]) {
    [self showCanDialog];
  }
  else if ([filename hasSuffix:@"head.vti"]) {
    [self showHeadImageDialog];
  }
}

-(BOOL) loadDatasetWithPath:(NSString*)path builtinIndex:(int) index
{
  NSLog(@"load dataset: %@", path);

  self.glView->builtinDatasetIndex = index;

  [self showWaitDialog];

  dispatch_async(self->myQueue, ^{

    [self->glView disableRendering];
    bool result = [glView getApp]->loadDataset([path UTF8String]);
    [self->glView resetView];
    [self->glView enableRendering];

    dispatch_async(dispatch_get_main_queue(), ^{
      [self postLoadDataset:path result:result];
    });
  });

  return YES;
}

-(BOOL) loadDatasetWithPath:(NSString*) path
{
  return [self loadDatasetWithPath:path builtinIndex:-1];
}

-(void) loadBuiltinDatasetWithIndex:(int)index
{
  vesKiwiViewerApp* app = [self.glView getApp];
  NSString* datasetName = [NSString stringWithUTF8String:app->builtinDatasetFilename(index).c_str()];

  if ([datasetName isEqualToString:@"pvweb"])
    {
    [self showPVWebDialog];
    return;
    }

  NSString* absolutePath = [[NSBundle mainBundle] pathForResource:datasetName ofType:nil];
  if (absolutePath == nil)
    {
    absolutePath = [self pathInDocumentsDirectoryForFileName:datasetName];
    }

  [self loadDatasetWithPath:absolutePath builtinIndex:index];
}

- (void)willPresentAlertView:(UIAlertView *)alertView
{
  if (alertView == self->waitDialog) {
   UIActivityIndicatorView *indicator = [[[UIActivityIndicatorView alloc]
    initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge] autorelease];
  indicator.center = CGPointMake(alertView.bounds.size.width / 2, alertView.bounds.size.height - 50);
  [alertView addSubview:indicator];
  [indicator startAnimating];
  }
}

-(void)handleDownloadedFile:(NSString*) filename
{
  if (![filename length]) {
    [self dismissWaitDialog];
    [self showErrorDialog];
  }
  else {
    [self loadDatasetWithPath:filename];
  }
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{

  if (alertView == self.pvwebDialog && buttonIndex == 1)
    {
    self.lastPVWebHost = [[alertView textFieldAtIndex:0] text];
    self.lastPVWebSessionId = [[alertView textFieldAtIndex:1] text];
    self.pvwebDialog = nil;

    std::string host;
    std::string sessionId;

    if ([self.lastPVWebHost length])
      {
      host = [self.lastPVWebHost UTF8String];
      }

    if ([self.lastPVWebSessionId length])
      {
      sessionId = [self.lastPVWebSessionId UTF8String];
      }

    [self showWaitDialogWithMessage:@"Contacting ParaView Web..."];

    vesKiwiViewerApp* app = [self.glView getApp];

    dispatch_async(self->myQueue, ^{

      [EAGLContext setCurrentContext:glView.context];
      bool result = app->doPVWebTest(host, sessionId);
      [self.glView resetView];

      dispatch_async(dispatch_get_main_queue(), ^{
        [self postLoadDataset:@"pvweb" result:result];
      });
    });



    }
  else if (alertView == self.openUrlDialog && buttonIndex == 1)
    {
    NSString* downloadUrl = [[alertView textFieldAtIndex:0] text];
    self.openUrlDialog = nil;

    if (![downloadUrl hasPrefix:@"http://"])
      {
      [self showAlertDialogWithTitle:@"URL Error" message:@"The url must begin with http://"];
      return;
      }

    NSLog(@"downloading url: %@", downloadUrl);
    NSLog(@"to directory: %@", [self documentsDirectory]);

    [self showWaitDialogWithMessage:@"Downloading file..."];

    vesKiwiViewerApp* app = [self.glView getApp];

    dispatch_async(self->myQueue, ^{

      [EAGLContext setCurrentContext:glView.context];
      std::string newFile = app->downloadFile([downloadUrl UTF8String], [[self documentsDirectory] UTF8String]);
      printf("got downloaded file: %s\n", newFile.c_str());

      dispatch_async(dispatch_get_main_queue(), ^{
        [self handleDownloadedFile:[NSString stringWithUTF8String:newFile.c_str()]];
      });
    });

    }
}

-(void)openUrl
{
  [self dismissLoadDataView];
  [self showOpenUrlDialog:nil];
}

- (BOOL)handleUrl:(NSURL *)url;
{
  // no url; go with the default dataset
  if (!url)
    {
    vesKiwiViewerApp* app = [self.glView getApp];
    [self loadBuiltinDatasetWithIndex:app->defaultBuiltinDatasetIndex()];
    return YES;
    }

  if ([url isFileURL])
    {
    return [self loadDatasetWithPath:[url path]];
    }

  return NO;
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
  [alert release];
}

-(void)dismissLoadDataView
{
  if(UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone)
    {
    [self.viewController dismissModalViewControllerAnimated:NO];
    }
  else
    {
    [self.loadDataPopover dismissPopoverAnimated:YES];
    }
}

-(void)dataSelected:(int)index
{
  [self dismissLoadDataView];
  [self loadBuiltinDatasetWithIndex:index];
}

-(IBAction)setLoadDataButtonTapped:(id)sender
{
  if (_dataLoader == nil)
    {
    _dataLoader = [[LoadDataController alloc]
                    initWithStyle:UITableViewStyleGrouped];
    _dataLoader.delegate = self;

    vesKiwiViewerApp* app = [self.glView getApp];
    NSMutableArray* exampleData = [NSMutableArray array];
    for (int i = 0; i < app->numberOfBuiltinDatasets(); ++i)
    {
      NSString* datasetName = [NSString stringWithUTF8String:app->builtinDatasetName(i).c_str()];
      [exampleData addObject:datasetName];
    }
    _dataLoader.exampleData = exampleData;


    if(UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone)
      {
      _dataLoader.modalPresentationStyle = UIModalPresentationFormSheet;
      }
    else
      {
      self.loadDataPopover = [[[UIPopoverController alloc]
                               initWithContentViewController:_dataLoader] autorelease];
      }
    }

  if(UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone)
    {
    TitleBarViewContainer* container = [TitleBarViewContainer new];
    [container addViewToContainer:_dataLoader.view];
    [container setTitle:@"Load Data"];
    container.previousViewController = self.window.rootViewController;
    [self.viewController presentModalViewController:container animated:YES];
    }
  else
    {
    [self.loadDataPopover presentPopoverFromRect:[(UIButton*)sender frame]
                                          inView:self.glView permittedArrowDirections:UIPopoverArrowDirectionDown animated:YES];
    self.viewController.loadPopover = self.loadDataPopover;
    }
}

@end
