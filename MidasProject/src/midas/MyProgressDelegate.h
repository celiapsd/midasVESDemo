/// \class MyProgressDelegate
/// \ingroup
#ifndef __MyProgressDelegate_h
#define __MyProgressDelegate_h


// VES includes
#include <midasFilesTools.h>

// C++ includes
#include <string>

//-------------------------------------------------

class MyProgressDelegate : public vesKiwiCurlDownloader::ProgressDelegate
{
  public:

    MyProgressDelegate() : mFilesTool(0), totalBytes(0),shouldAbort(false)
    {
    }

    void setFilesTool(midasFilesTools * filesTool);
    void setTotalBytes(int totalBytes);

    virtual int downloadProgress(double totalToDownload, double nowDownloaded);

private :

   midasFilesTools * mFilesTool;
   int totalBytes;
   bool shouldAbort;

};


#endif
