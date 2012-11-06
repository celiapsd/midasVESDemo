/// \class midasResource
/// \ingroup
#ifndef __midasResource_h
#define __midasResource_h


// VES includes


// C++ includes
#include <string>

//-------------------------------------------------
class midasResource
{

public :

   midasResource();
   virtual ~midasResource();

   enum Type {
         COMMUNITY,
         FOLDER,
         ITEM,
         BITSTREAM,
         NOTSET
      };

   void init(int id, std::string name, Type type);
   void setId(int id);
   void setName(std::string name);
   void setType(Type type);


private :

   int id;
   std::string name;
   Type type;

};


#endif
