/// \class CProgressDialog
/// \ingroup
#ifndef __CProgressDialog_h
#define __CProgressDialog_h


// VES includes


// C++ includes
#include <string>

//-------------------------------------------------
class CProgressDialog
{

public :


   CProgressDialog();
   virtual ~CProgressDialog();
   void init(const int progress,const int totalToDownload,const int itemBytes);
   void setProgress(const int& mprogress);
   void setTotalToDownload(const int& mtotalToDownload);
   void setItemBytes(const int& mItemBytes);
   int getProgress() const;
   int getTotalToDownload() const;
   int getItemBytes() const;


private :

   int progress;
   double totalToDownload;
   int itemBytes;

};


#endif
