//
//  DocumentsTableViewController.h
//  CloudAppTab
//
//  Created by Pat Marion on 10/4/12.
//  Copyright (c) 2012 Pat Marion. All rights reserved.
//

#import <UIKit/UIKit.h>

#include <vesMidasClient.h>

@interface DocumentsTableViewController : UITableViewController {


}

@property (assign) vesMidasClient::Ptr client;
@property (strong) NSString* folderId;
@property (strong) NSString* communityId;
@property (strong) NSString* localFolder;


@end