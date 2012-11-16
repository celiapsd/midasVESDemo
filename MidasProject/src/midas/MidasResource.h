/// \class MidasResource
/// \ingroup
#ifndef __MidasResource_h
#define __MidasResource_h


// VES includes


// C++ includes
#include <string>

//-------------------------------------------------
class MidasResource
{

public :

    typedef enum {
          NOTSET,
          COMMUNITY,
          FOLDER,
          ITEM,
          BITSTREAM
       } Type ;


   MidasResource();
   MidasResource(const int& id,const std::string& name,const Type& type, const int& size);
   virtual ~MidasResource();



   void init(const int& id,const std::string& name, const Type& type, const int& size);

   void setId(const int& id);
   void setName(const std::string& name);
   void setType(const Type& type);
   void setSize(const int& size);

   int getId() const;
   std::string getName() const;
   Type getType() const;
   int getSize () const;


private :

   int id;
   std::string name;
   Type type;
   int size;

};


#endif
