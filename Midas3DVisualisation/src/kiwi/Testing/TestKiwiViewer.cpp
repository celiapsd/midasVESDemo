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

#include <iostream>
#include <sstream>
#include <fstream>
#include <cassert>
#include <cstdlib>
#include <cstdio>
#include <cstring>

#include <vesKiwiViewerApp.h>
#include <vesKiwiBaselineImageTester.h>
#include <vesBuiltinShaders.h>
#include <vesSetGet.h>

#include <X11/Xlib.h>
#include <X11/Xutil.h>
#include <X11/keysym.h>

#include <GLES2/gl2.h>
#include <EGL/egl.h>


//----------------------------------------------------------------------------
namespace {

class vesTestHelper {
public:

  vesKiwiViewerApp* app() {
    return &this->App;
  }

  std::string sourceDirectory() {
    return this->SourceDirectory;
  }

  void setSourceDirectory(std::string dir) {
    this->SourceDirectory = dir;
  }

  std::string dataDirectory() {
    return this->DataDirectory;
  }

  void setDataDirectory(std::string dir) {
    this->DataDirectory = dir;
  }

  bool isTesting() {
    return this->IsTesting;
  }

  void setTesting(bool testing) {
    this->IsTesting = testing;
  }

private:

  vesKiwiViewerApp App;

  std::string       SourceDirectory;
  std::string       DataDirectory;
  bool              IsTesting;
};

//----------------------------------------------------------------------------
vesTestHelper* testHelper;

//----------------------------------------------------------------------------
void LoadData(int index)
{
  std::string dataRoot = testHelper->sourceDirectory() + "/Apps/iOS/Kiwi/Kiwi/Data/";
  std::string filename = dataRoot + testHelper->app()->builtinDatasetFilename(index);
  testHelper->app()->loadDataset(filename);
  testHelper->app()->applyBuiltinDatasetCameraParameters(index);
}

//----------------------------------------------------------------------------
bool DoTesting()
{
  const double threshold = 10.0;
  bool allTestsPassed = true;

  vesKiwiBaselineImageTester baselineTester;
  baselineTester.setApp(testHelper->app());
  baselineTester.setBaselineImageDirectory(testHelper->dataDirectory());

  // This loads each builtin dataset, renders it, and saves a screenshot

  // Note, this loop renders but does not bother to swap buffers
  for (int i = 0; i < testHelper->app()->numberOfBuiltinDatasets(); ++i) {
    LoadData(i);

    // Enable the background image for the final image regression test
    if (i == testHelper->app()->numberOfBuiltinDatasets()-1)
      {
      testHelper->app()->setBackgroundTexture(testHelper->sourceDirectory() + "/Apps/iOS/Kiwi/Kiwi/Data/kiwi.png");
      }

    // call the info methods, this helps coverage, though we're not testing the return values
    testHelper->app()->numberOfModelFacets();
    testHelper->app()->numberOfModelVertices();
    testHelper->app()->numberOfModelLines();

    testHelper->app()->render();
    std::string testName = testHelper->app()->builtinDatasetName(i);

    if (!baselineTester.performTest(testName, threshold)) {
      allTestsPassed = false;
    }
  }

  return allTestsPassed;
}

//----------------------------------------------------------------------------
std::string GetFileContents(const std::string& filename)
{
  std::ifstream file(filename.c_str());
  std::stringstream buffer;
  if (file) {
    buffer << file.rdbuf();
    file.close();
  }
  return buffer.str();
}

//----------------------------------------------------------------------------
void InitRendering()
{
  testHelper->app()->initGL();
}

//----------------------------------------------------------------------------
bool InitTest(int argc, char* argv[])
{
  if (argc < 2) {
    printf("Usage: %s <path to VES source directory> [path to testing data directory]\n", argv[0]);
    return false;
  }

  testHelper = new vesTestHelper();
  testHelper->setSourceDirectory(argv[1]);

  if (argc == 3) {
    testHelper->setDataDirectory(argv[2]);
    testHelper->setTesting(true);
  }
  return true;
}

//----------------------------------------------------------------------------
void FinalizeTest()
{
  delete testHelper;
}

}; // end namespace
//----------------------------------------------------------------------------


/*
 * Create an RGB, double-buffered X window.
 * Return the window and context handles.
 */
static void
make_x_window(Display *x_dpy, EGLDisplay egl_dpy,
              const char *name,
              int x, int y, int width, int height,
              Window *winRet,
              EGLContext *ctxRet,
              EGLSurface *surfRet)
{
   static const EGLint attribs[] = {
      EGL_RED_SIZE, 1,
      EGL_GREEN_SIZE, 1,
      EGL_BLUE_SIZE, 1,
      EGL_DEPTH_SIZE, 1,
      EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT,
      EGL_NONE
   };
   static const EGLint ctx_attribs[] = {
      EGL_CONTEXT_CLIENT_VERSION, 2,
      EGL_NONE
   };
   int scrnum;
   XSetWindowAttributes attr;
   unsigned long mask;
   Window root;
   Window win;
   XVisualInfo *visInfo, visTemplate;
   int num_visuals;
   EGLContext ctx;
   EGLConfig config;
   EGLint num_configs;
   EGLint vid;

   scrnum = DefaultScreen( x_dpy );
   root = RootWindow( x_dpy, scrnum );

   if (!eglChooseConfig( egl_dpy, attribs, &config, 1, &num_configs)) {
      printf("Error: couldn't get an EGL visual config\n");
      exit(1);
   }

   assert(config);
   assert(num_configs > 0);

   if (!eglGetConfigAttrib(egl_dpy, config, EGL_NATIVE_VISUAL_ID, &vid)) {
      printf("Error: eglGetConfigAttrib() failed\n");
      exit(1);
   }

   /* The X window visual must match the EGL config */
   visTemplate.visualid = vid;
   visInfo = XGetVisualInfo(x_dpy, VisualIDMask, &visTemplate, &num_visuals);
   if (!visInfo) {
      printf("Error: couldn't get X visual\n");
      exit(1);
   }

   /* window attributes */
   attr.background_pixel = 0;
   attr.border_pixel = 0;
   attr.colormap = XCreateColormap( x_dpy, root, visInfo->visual, AllocNone);
   attr.event_mask = StructureNotifyMask | ExposureMask | KeyPressMask | ButtonPressMask | ButtonReleaseMask | ButtonMotionMask;
   mask = CWBackPixel | CWBorderPixel | CWColormap | CWEventMask;

   win = XCreateWindow( x_dpy, root, 0, 0, width, height,
                        0, visInfo->depth, InputOutput,
                        visInfo->visual, mask, &attr );

   /* set hints and properties */
   {
      XSizeHints sizehints;
      sizehints.x = x;
      sizehints.y = y;
      sizehints.width  = width;
      sizehints.height = height;
      sizehints.flags = USSize | USPosition;
      XSetNormalHints(x_dpy, win, &sizehints);
      XSetStandardProperties(x_dpy, win, name, name,
                              None, (char **)NULL, 0, &sizehints);
   }

#if USE_FULL_GL /* XXX fix this when eglBindAPI() works */
   eglBindAPI(EGL_OPENGL_API);
#else
   eglBindAPI(EGL_OPENGL_ES_API);
#endif

   ctx = eglCreateContext(egl_dpy, config, EGL_NO_CONTEXT, ctx_attribs );
   if (!ctx) {
      printf("Error: eglCreateContext failed\n");
      exit(1);
   }

   /* test eglQueryContext() */
   {
      EGLint val;
      eglQueryContext(egl_dpy, ctx, EGL_CONTEXT_CLIENT_VERSION, &val);
      assert(val == 2);
   }

   *surfRet = eglCreateWindowSurface(egl_dpy, config, win, NULL);
   if (!*surfRet) {
      printf("Error: eglCreateWindowSurface failed\n");
      exit(1);
   }

   /* sanity checks */
   {
      EGLint val;
      eglQuerySurface(egl_dpy, *surfRet, EGL_WIDTH, &val);
      assert(val == width);
      eglQuerySurface(egl_dpy, *surfRet, EGL_HEIGHT, &val);
      assert(val == height);
      assert(eglGetConfigAttrib(egl_dpy, config, EGL_SURFACE_TYPE, &val));
      assert(val & EGL_WINDOW_BIT);
   }

   XFree(visInfo);

   *winRet = win;
   *ctxRet = ctx;
}

bool haveLastMotion = false;
int lastMotionX = 0;
int lastMotionY = 0;
int currentX = 0;
int currentY = 0;

static void
event_loop(Display *dpy, Window win,
           EGLDisplay egl_dpy, EGLSurface egl_surf)
{
  vesNotUsed(win);

   while (1) {
      int redraw = 0;
      XEvent event;

      XNextEvent(dpy, &event);

      switch (event.type) {
      case Expose:
         redraw = 1;
         break;
      case ConfigureNotify:
         testHelper->app()->resizeView(event.xconfigure.width, event.xconfigure.height);
         break;

      case ButtonPress:
        testHelper->app()->handleSingleTouchDown(event.xbutton.x, event.xbutton.y);

        haveLastMotion = true;
        lastMotionX = event.xbutton.x;
        lastMotionY = event.xbutton.y;
        redraw = 1;
        break;

      case ButtonRelease:
        testHelper->app()->handleSingleTouchUp();
        haveLastMotion = false;
        redraw = 1;
        break;

      case MotionNotify:

        if (haveLastMotion) {
          currentX = event.xmotion.x;
          currentY = event.xmotion.y;
          testHelper->app()->handleSingleTouchPanGesture(currentX - lastMotionX, currentY - lastMotionY);
          lastMotionX = currentX;
          lastMotionY = currentY;
          redraw = 1;
        }
        break;

      case KeyPress:
         {
            int panDelta = 100;
            char buffer[10];
            int r, code;
            code = XLookupKeysym(&event.xkey, 0);
            if (code == XK_Left) {
              testHelper->app()->handleSingleTouchPanGesture(-panDelta, 0);
            }
            else if (code == XK_Right) {
              testHelper->app()->handleSingleTouchPanGesture(panDelta, 0);
            }
            else if (code == XK_Up) {
              testHelper->app()->handleSingleTouchPanGesture(0, -panDelta);
            }
            else if (code == XK_Down) {
              testHelper->app()->handleSingleTouchPanGesture(0, panDelta);
            }
            else {
               r = XLookupString(&event.xkey, buffer, sizeof(buffer),
                                 NULL, NULL);
               if (buffer[0] == 27) {
                  /* escape */
                  return;
               }
               if (buffer[0] == 't') {
                 static int currentShadingModelIndex =
                   testHelper->app()->getNumberOfShadingModels() - 1;

                 currentShadingModelIndex = (currentShadingModelIndex + 1) %
                   testHelper->app()->getNumberOfShadingModels();

                 testHelper->app()->setShadingModel(testHelper->app()->getShadingModel(
                    currentShadingModelIndex));
               }
               else if (buffer[0] == 'n') {
                 static int currentDataset = 0;
                 currentDataset = (currentDataset + 1) % testHelper->app()->numberOfBuiltinDatasets();
                 LoadData(currentDataset);
               }
               else if (buffer[0] == 'r') {
                 testHelper->app()->resetView();
               }
            }
         }
         redraw = 1;
         break;
      default:
         ; /*no-op*/
      }

      if (redraw) {
         testHelper->app()->render();
         eglSwapBuffers(egl_dpy, egl_surf);
      }
   }
}


int
main(int argc, char *argv[])
{
  const int winWidth = 800, winHeight = 600;
  Display *x_dpy;
  Window win;
  EGLSurface egl_surf;
  EGLContext egl_ctx;
  EGLDisplay egl_dpy;
  char *dpyName = NULL;
  GLboolean printInfo = GL_FALSE;
  EGLint egl_major, egl_minor;
  const char *s;


  if (!InitTest(argc, argv)) {
    return -1;
  }

  x_dpy = XOpenDisplay(dpyName);
  if (!x_dpy) {
    printf("Error: couldn't open display %s\n",
           dpyName ? dpyName : getenv("DISPLAY"));
    return -1;
  }

  egl_dpy = eglGetDisplay(x_dpy);
  if (!egl_dpy) {
    printf("Error: eglGetDisplay() failed\n");
    return -1;
  }

  if (!eglInitialize(egl_dpy, &egl_major, &egl_minor)) {
    printf("Error: eglInitialize() failed\n");
    return -1;
  }

  s = eglQueryString(egl_dpy, EGL_VERSION);
  printf("EGL_VERSION = %s\n", s);

  s = eglQueryString(egl_dpy, EGL_VENDOR);
  printf("EGL_VENDOR = %s\n", s);

  s = eglQueryString(egl_dpy, EGL_EXTENSIONS);
  printf("EGL_EXTENSIONS = %s\n", s);

  s = eglQueryString(egl_dpy, EGL_CLIENT_APIS);
  printf("EGL_CLIENT_APIS = %s\n", s);

  make_x_window(x_dpy, egl_dpy,
               "OpenGL ES 2.x tri", 0, 0, winWidth, winHeight,
               &win, &egl_ctx, &egl_surf);

  XMapWindow(x_dpy, win);
  if (!eglMakeCurrent(egl_dpy, egl_surf, egl_surf, egl_ctx)) {
    printf("Error: eglMakeCurrent() failed\n");
    return -1;
  }

  if (printInfo) {
    printf("GL_RENDERER   = %s\n", (char *) glGetString(GL_RENDERER));
    printf("GL_VERSION    = %s\n", (char *) glGetString(GL_VERSION));
    printf("GL_VENDOR     = %s\n", (char *) glGetString(GL_VENDOR));
    printf("GL_EXTENSIONS = %s\n", (char *) glGetString(GL_EXTENSIONS));
  }


  InitRendering();

  LoadData(testHelper->app()->defaultBuiltinDatasetIndex());

  // render once
  testHelper->app()->resizeView(winWidth, winHeight);
  testHelper->app()->applyBuiltinDatasetCameraParameters(testHelper->app()->defaultBuiltinDatasetIndex());
  testHelper->app()->render();
  eglSwapBuffers(egl_dpy, egl_surf);

  // begin the event loop if not in testing mode
  bool testPassed = true;
  if (!testHelper->isTesting()) {
    event_loop(x_dpy, win, egl_dpy, egl_surf);
  }
  else {
    testPassed = DoTesting();
  }

  FinalizeTest();

  eglDestroyContext(egl_dpy, egl_ctx);
  eglDestroySurface(egl_dpy, egl_surf);
  eglTerminate(egl_dpy);


  XDestroyWindow(x_dpy, win);
  XCloseDisplay(x_dpy);

  return testPassed ? 0 : 1;
}
