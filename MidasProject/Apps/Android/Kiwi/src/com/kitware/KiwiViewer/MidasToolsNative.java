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

package com.kitware.KiwiViewer;


public class MidasToolsNative {

     static {
         System.loadLibrary("MidasToolsNative");
     }

     public static native synchronized int init(String url,String email,String password);
     public static native synchronized MidasResource[] findCommunities();
     public static native synchronized String[] findCommunityChildren(String nameCommunity);
     public static native synchronized String[] findFolderChildren(String nameChildren);
     public static native synchronized String downloadItem(String nameItem,String pathItem);
     public static native synchronized Integer getProgressDownload();
      
}
