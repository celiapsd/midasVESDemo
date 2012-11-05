//
//  DocumentsTableViewController.m
//  CloudAppTab
//
//  Created by Pat Marion on 10/4/12.
//  Copyright (c) 2012 Pat Marion. All rights reserved.
//

#import "DocumentsTableViewController.h"

#include <vesKiwiCurlDownloader.h>
#include <vtkNew.h>
#include <vtkDirectory.h>

namespace {
  class MyProgressDelegate : public vesKiwiCurlDownloader::ProgressDelegate {
    public:

      MyProgressDelegate() : shouldAbort(false), totalBytes(0), progressView(nil)
      {
      }

      virtual int downloadProgress(double totalToDownload, double nowDownloaded)
      {

        double progress = nowDownloaded/this->totalBytes;

        dispatch_async(dispatch_get_main_queue(), ^{
          progressView.progress = progress;
        });

        if (shouldAbort) {
          return 1;
        }
        else {
          return 0;
        }
      }

    bool shouldAbort;
    double totalBytes;
    UIProgressView* progressView;
  };
}

@interface DocumentsTableViewController () {

  int foldersSection;
  int communityFoldersSection;
  int communitiesSection;
  int localFilesSection;
  int demoAppSection;
  int numberOfSections;

  std::vector<std::string> folderNames;
  std::vector<std::string> folderIds;
  std::vector<std::string> itemNames;
  std::vector<std::string> itemIds;
  std::vector<size_t> itemBytes;
  std::vector<std::string> communityNames;
  std::vector<std::string> communityIds;
  std::vector<std::string> localFiles;
  std::vector<std::string> localFolders;
  std::vector<std::string> demoAppNames;

  UIButton* mCancelButton;
  UIProgressView* mProgressView;
  UIActivityIndicatorView* mSpinner;
  NSIndexPath* mActiveDownloadItem;
  vesSharedPtr<MyProgressDelegate> mProgressDelegate;
  vesSharedPtr<vesKiwiCurlDownloader> mDownloader;
}

@end

@implementation DocumentsTableViewController

@synthesize client;
@synthesize folderId;
@synthesize communityId;
@synthesize localFolder;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {

    }
    return self;
}

-(void)listLocalFiles:(NSString*) baseDir
{
  NSLog(@"listing: %@\n", baseDir);

  vtkNew<vtkDirectory> dirList;
  dirList->Open([baseDir UTF8String]);

  for (int i = 0; i < dirList->GetNumberOfFiles(); ++i) {
    std::string entryName = dirList->GetFile(i);

    if (!entryName.size()) {
      continue;
    }
    if (entryName.substr(0,1) == ".") {
      continue;
    }
    if (entryName == "__MACOSX") {
      continue;
    }


    if (dirList->FileIsDirectory(entryName.c_str())) {
      printf("dir: %s\n", entryName.c_str());
      self->localFolders.push_back(entryName);
    }
    else {
      printf("file: %s\n", entryName.c_str());
      self->localFiles.push_back(entryName);
    }
  }
}

-(int) populateView
{

  int nSections = 0;

  if (self.folderId) {

    self->foldersSection = nSections;
    ++nSections;

    self->client->listFolderChildren([self.folderId UTF8String]);
    self->folderNames = self->client->folderNames();
    self->folderIds = self->client->folderIds();
    self->itemNames = self->client->itemNames();
    self->itemIds = self->client->itemIds();
    self->itemBytes = self->client->itemBytes();
  }
  else if (self.communityId) {

    self->communityFoldersSection = nSections;
    ++nSections;

    self->client->listCommunityChildren([self.communityId UTF8String]);
    self->folderNames = self->client->folderNames();
    self->folderIds = self->client->folderIds();
  }
  else if (self.localFolder) {
    self->localFilesSection = nSections;
    ++nSections;

    [self listLocalFiles:self.localFolder];
  }
  else {

    // top level navigation

    if (self->client->token().size()) {

      self->foldersSection = nSections;
      ++nSections;

      self->client->listUserFolders();
      self->folderNames = self->client->folderNames();
      self->folderIds = self->client->folderIds();
    }

    self->communitiesSection = nSections;
    ++nSections;

    self->client->listCommunities();
    self->communityNames = self->client->folderNames();
    self->communityIds = self->client->folderIds();

    self->localFilesSection = nSections;
    ++nSections;
    [self listLocalFiles:[self documentsDirectory]];

    const bool enableApps = true;
    if (enableApps) {
      self->demoAppSection = nSections;
      ++nSections;
      self->demoAppNames.push_back("ParaView Web");
      self->demoAppNames.push_back("ParaView Remote Control");
      self->demoAppNames.push_back("Point Cloud Streaming Demo");
    }
  }

  return nSections;
}


- (void)viewDidLoad
{
  [super viewDidLoad];

  if (!self->client) {
    return;
  }

  // add a spinner
  UIActivityIndicatorView* activityIndicator = [[UIActivityIndicatorView alloc] initWithFrame:CGRectMake(0, 0, 20, 20)];
  [activityIndicator hidesWhenStopped];
  UIBarButtonItem * barButton = [[UIBarButtonItem alloc] initWithCustomView:activityIndicator];
  [self navigationItem].rightBarButtonItem = barButton;
  self->mSpinner = activityIndicator;


  self->foldersSection = -1;
  self->communityFoldersSection = -1;
  self->communitiesSection = -1;
  self->localFilesSection = -1;
  self->demoAppSection = -1;
  self->numberOfSections = 0;

  [self startSpinner];

  dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{

    int nSections = [self populateView];

    dispatch_async(dispatch_get_main_queue(), ^{
      self->numberOfSections = nSections;
      [self.tableView reloadData];
      [self stopSpinner];
    });
  });


}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(NSString*) documentsDirectory
{
  NSArray* documentDirectories = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
  return [documentDirectories objectAtIndex:0];
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


- (void)viewWillAppear:(BOOL)animated
{
  [super viewWillAppear:animated];

  //printf("viewDidAppear, calling reloadData\n");
  //[self.tableView reloadData];
}

- (void)viewDidDisappear:(BOOL)animated
{
  [super viewDidDisappear:animated];
}

-(void)startSpinner
{
  [self->mSpinner startAnimating];
}

-(void)stopSpinner
{
  [self->mSpinner stopAnimating];
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
  return self->numberOfSections;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
{  
  if (section == self->foldersSection) {
    return @"Midas Documents";
  }
  else if (section == self->communityFoldersSection) {
    return @"Community Folders";
  }
  else if (section == self->communitiesSection) {
    return @"Midas Communities";
  }
  else if (section == self->localFilesSection) {
    return @"Local Documents";
  }
  else if (section == self->demoAppSection) {
    return @"Apps";
  }

  return nil;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
  if (section == self->foldersSection) {
    return self->folderNames.size() + self->itemNames.size();
  }
  else if (section == self->communityFoldersSection) {
    return self->folderNames.size();
  }
  else if (section == self->communitiesSection) {
    return self->communityNames.size();
  }
  else if (section == self->localFilesSection) {
    return self->localFolders.size() + self->localFiles.size();
  }
  else if (section == self->demoAppSection) {
    return self->demoAppNames.size();
  }
  return 0;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{

  NSString *cellIdentifier = @"DocumentsTableCell";

  UITableViewCell *cell;
    //= [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
  //if (cell == nil) {
  //  printf("making new cell\n");
  //  cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
  //}


    
  if (indexPath.section == self->foldersSection)
  {    
    int row = indexPath.row;
    int numberOfFolders = self->folderNames.size();
  
    if (row < numberOfFolders) {
      std::string cellLabel = self->folderNames[row];
      cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
      cell.textLabel.text = [NSString stringWithUTF8String:cellLabel.c_str()];
      cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    }
    else {
      int itemIndex = row - numberOfFolders;
      std::string cellLabel = self->itemNames[itemIndex];
      cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:cellIdentifier];
      cell.textLabel.text = [NSString stringWithUTF8String:cellLabel.c_str()];
      cell.accessoryType = UITableViewCellAccessoryNone;

      double itemKilobytes = self->itemBytes[itemIndex]/1024.0;
      double itemMegabytes = itemKilobytes/1024.0;
      if (itemMegabytes < 1.0) {
        cell.detailTextLabel.text = [NSString stringWithFormat:@"%.02f KB", itemKilobytes];
      }
      else {
        cell.detailTextLabel.text = [NSString stringWithFormat:@"%.02f MB", itemMegabytes];
      }
    }
    
  }
  else if (indexPath.section == self->communityFoldersSection) {
    int row = indexPath.row;
    std::string cellLabel = self->folderNames[row];
    cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    cell.textLabel.text = [NSString stringWithUTF8String:cellLabel.c_str()];
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
  }
  else if (indexPath.section == self->communitiesSection) {
    int row = indexPath.row;
    std::string cellLabel = self->communityNames[row];
    cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    cell.textLabel.text = [NSString stringWithUTF8String:cellLabel.c_str()];
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
  }
  else if (indexPath.section == self->localFilesSection) {

    int row = indexPath.row;
    int numberOfFolders = self->localFolders.size();
  
    if (row < numberOfFolders) {
      std::string cellLabel = self->localFolders[row];
      cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
      cell.textLabel.text = [NSString stringWithUTF8String:cellLabel.c_str()];
      cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    }
    else {
      int itemIndex = row - numberOfFolders;
      std::string cellLabel = self->localFiles[itemIndex];
      cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
      cell.textLabel.text = [NSString stringWithUTF8String:cellLabel.c_str()];
      cell.accessoryType = UITableViewCellAccessoryNone;
    }

  }
  else if (indexPath.section == self->demoAppSection) {
    int row = indexPath.row;
    std::string cellLabel = self->demoAppNames[row];
    cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    cell.textLabel.text = [NSString stringWithUTF8String:cellLabel.c_str()];
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
  }
  
  return cell;
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

-(BOOL)tableView:(UITableView *)tableView shouldHighlightRowAtIndexPath:(NSIndexPath *)indexPath
{
  if (self->mActiveDownloadItem) {
    return NO;
  }
  return YES;
}

#pragma mark - Table view delegate

-(void)openFile:(NSString*)filename
{
  NSLog(@"Opening file: %@", filename);
  NSDictionary* args = [NSDictionary dictionaryWithObjectsAndKeys:filename, @"dataset", nil];
  [[NSNotificationCenter defaultCenter] postNotificationName:@"switchToRenderView" object:nil userInfo:args];
}

-(void)handleDownloadedFile:(NSString*)filename
{
  UIProgressView *progressView = self->mProgressDelegate->progressView;
  progressView.progress = 1.0;
  [progressView removeFromSuperview];
  [self->mCancelButton removeFromSuperview];
  bool didAbort = self->mProgressDelegate->shouldAbort;
  self->mActiveDownloadItem = nil;
  self->mProgressDelegate.reset();

  if (didAbort) {
    return;
  }
  
  if (!filename.length) {
    [self showAlertDialogWithTitle:[NSString stringWithUTF8String:self->mDownloader->errorTitle().c_str()]
                           message:[NSString stringWithUTF8String:self->mDownloader->errorMessage().c_str()]];
    return;
  }

  [self openFile:filename];
}

-(void)onAbortDownload
{
  if (self->mProgressDelegate) {
    self->mProgressDelegate->shouldAbort = true;
  }
}

-(DocumentsTableViewController*) newDocumentsViewController
{
  UIStoryboard*  sb =  self.storyboard;
  DocumentsTableViewController* tableViewController = [sb instantiateViewControllerWithIdentifier:@"DocumentsTableViewController"];
  tableViewController.folderId = nil;
  tableViewController.communityId = nil;
  tableViewController.localFolder = nil;
  return tableViewController;
}

-(void)handleFolderSelected:(int)folderIndex
{
  std::string selectedFolderName = self->folderNames[folderIndex];
  std::string selectedFolderId = self->folderIds[folderIndex];

  DocumentsTableViewController* tableViewController = [self newDocumentsViewController];
  tableViewController.folderId = [NSString stringWithUTF8String:selectedFolderId.c_str()];
  tableViewController.title = [NSString stringWithUTF8String:selectedFolderName.c_str()];
  tableViewController.client = self.client;
  [self.navigationController pushViewController:tableViewController animated:YES];
}

-(void)handleCommunitySelected:(int)communityIndex
{
  std::string selectedCommunityName = self->communityNames[communityIndex];
  std::string selectedCommunityId = self->communityIds[communityIndex];

  DocumentsTableViewController* tableViewController = [self newDocumentsViewController];
  tableViewController.communityId = [NSString stringWithUTF8String:selectedCommunityId.c_str()];
  tableViewController.title = [NSString stringWithUTF8String:selectedCommunityName.c_str()];
  tableViewController.client = self.client;
  [self.navigationController pushViewController:tableViewController animated:YES];
}

-(void)handleLocalDocumentSelected:(NSIndexPath*)indexPath
{
  int row = indexPath.row;
  int numberOfFolders = self->localFolders.size();

  std::string baseFolder;
  if (self.localFolder) {
    baseFolder = [self.localFolder UTF8String];
  }
  else {
    baseFolder = [[self documentsDirectory] UTF8String];
  }

  if (row < numberOfFolders) {
    std::string selectedFolderName = self->localFolders[row];
    std::string destFolder = baseFolder + "/" + selectedFolderName;

    DocumentsTableViewController* tableViewController = [self newDocumentsViewController];
    tableViewController.localFolder = [NSString stringWithUTF8String:destFolder.c_str()];
    tableViewController.title = [NSString stringWithUTF8String:selectedFolderName.c_str()];
    tableViewController.client = self.client;
    [self.navigationController pushViewController:tableViewController animated:YES];
  }
  else {
    int fileIndex = row - numberOfFolders;
    std::string selectedFileName = self->localFiles[fileIndex];
    std::string destFile = baseFolder + "/" + selectedFileName;
    [self.tableView deselectRowAtIndexPath:indexPath animated:YES];
    [self openFile:[NSString stringWithUTF8String:destFile.c_str()]];
  }
}

-(void)handleDemoAppSelected:(NSIndexPath*)indexPath
{
  std::string selectedDemoApp = self->demoAppNames[indexPath.row];
  printf("selected demo app: %s\n", selectedDemoApp.c_str());

  [self.tableView deselectRowAtIndexPath:indexPath animated:YES];
  [self openFile:[NSString stringWithUTF8String:selectedDemoApp.c_str()]];
}

-(void)handleItemSelected:(int) itemIndex indexPath:(NSIndexPath*)indexPath
{
  [self.tableView deselectRowAtIndexPath:indexPath animated:YES];
  UITableViewCell* selectedCell = [self.tableView cellForRowAtIndexPath:indexPath];

  UIButton *cancelButton = [UIButton buttonWithType:UIButtonTypeRoundedRect];
  [cancelButton addTarget:self
                action:@selector(onAbortDownload)
                forControlEvents:UIControlEventTouchUpInside];
  
  [cancelButton setTitle:@"Cancel" forState:UIControlStateNormal];
  [cancelButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
  cancelButton.titleLabel.font = [UIFont boldSystemFontOfSize:12];
  cancelButton.titleLabel.shadowColor = [UIColor lightGrayColor];
  cancelButton.titleLabel.shadowOffset = CGSizeMake(0, -1);
  [cancelButton setBackgroundImage:[[UIImage imageNamed:@"delete_button.png"]
                                             stretchableImageWithLeftCapWidth:8.0f
                                             topCapHeight:0.0f]
                                             forState:UIControlStateNormal];

  UIProgressView *progressView = [UIProgressView new];
  progressView.progress = 0.0;

  CGRect cellFrame = selectedCell.frame;
  CGRect progressFrame = progressView.frame;
  CGRect buttonFrame = cancelButton.frame;

  buttonFrame = CGRectMake(cellFrame.size.width-(60 + 60),
                          (cellFrame.size.height/2)-30/2.0, 60, 30);

  cancelButton.frame = buttonFrame;
  progressView.frame = CGRectMake(buttonFrame.origin.x-220,
                                  (cellFrame.size.height/2)-progressFrame.size.height/2.0,
                                  200.0f, progressFrame.size.height);

  [selectedCell addSubview:progressView];
  [selectedCell addSubview:cancelButton];
  

  
  std::string destDir = [[self documentsDirectory] UTF8String];
  destDir += "/Downloads";

  if (self->client->token().size()) {
    self->client->renewToken();

    // todo token handle error here
  }

  double totalBytes = self->itemBytes[itemIndex];
  std::string selectedItemId = self->itemIds[itemIndex];
  std::string downloadUrl = self->client->itemDownloadUrl(selectedItemId);

  vesSharedPtr<vesKiwiCurlDownloader> downloader = vesSharedPtr<vesKiwiCurlDownloader>(new vesKiwiCurlDownloader);
  vesSharedPtr<MyProgressDelegate> progressDelegate = vesSharedPtr<MyProgressDelegate>(new MyProgressDelegate);
  progressDelegate->totalBytes = totalBytes;
  progressDelegate->progressView = progressView;
  downloader->setProgressDelegate(progressDelegate);

  self->mActiveDownloadItem = indexPath;
  self->mProgressDelegate = progressDelegate;
  self->mDownloader = downloader;
  self->mCancelButton = cancelButton;


  dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{

    std::string downloadedFile = downloader->downloadUrlToDirectory(downloadUrl, destDir);
    printf("got downloaded file: %s\n", downloadedFile.c_str());

    dispatch_async(dispatch_get_main_queue(), ^{
      [self handleDownloadedFile:[NSString stringWithUTF8String:downloadedFile.c_str()]];
    });
  });

  
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
  if (self->mActiveDownloadItem) {
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
    return;
  }


  if (indexPath.section == self->foldersSection) {

    int row = indexPath.row;
    int numberOfFolders = self->folderNames.size();

    if (row < numberOfFolders) {
      [self handleFolderSelected:row];
    }
    else {
      int itemIndex = row - numberOfFolders;
      [self handleItemSelected:itemIndex indexPath:indexPath];
    }
  }
  if (indexPath.section == self->communityFoldersSection) {
    [self handleFolderSelected:indexPath.row];
  }
  else if (indexPath.section == self->communitiesSection) {
    [self handleCommunitySelected:indexPath.row];
  }
  else if (indexPath.section == self->localFilesSection) {
    [self handleLocalDocumentSelected:indexPath];
  }
  else if (indexPath.section == self->demoAppSection) {
    [self handleDemoAppSelected:indexPath];
  }

}

- (void)tableView:(UITableView *)tableView accessoryButtonTappedForRowWithIndexPath:(NSIndexPath *)indexPath
{

  printf("show details for %d\n", indexPath.row);

}

@end
