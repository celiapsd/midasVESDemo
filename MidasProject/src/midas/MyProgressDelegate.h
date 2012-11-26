/// \class MyProgressDelegate
/// \ingroup
#ifndef __MyProgressDelegate_h
#define __MyProgressDelegate_h


// VES includes
#include <midasFilesTools.h>
#include <jni.h>

// C++ includes
#include <string>

//-------------------------------------------------

class MyProgressDelegate : public vesKiwiCurlDownloader::ProgressDelegate
{
  public:

    MyProgressDelegate();
    void setFilesTool(midasFilesTools * filesTool);
    void setTotalBytes(int totalBytes);
    void setProgressDialog(jobject* progressDialog);
    //void initProgressDialog(JNIEnv * env);

    virtual int downloadProgress(double totalToDownload, double nowDownloaded);

  private :

    midasFilesTools * mFilesTool;
    jobject* mProgressDialog;
    int totalBytes;
    bool shouldAbort;

};


#endif
