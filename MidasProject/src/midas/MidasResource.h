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
   MidasResource(const int& id,const std::string& name,const Type& type);
   virtual ~MidasResource();



   void init(const int& id,const std::string& name, const Type& type);

   void setId(const int& id);
   void setName(const std::string& name);
   void setType(const Type& type);

   int getId() const;
   std::string getName() const;
   Type getType() const;


private :

   int id;
   std::string name;
   Type type;

};


#endif
