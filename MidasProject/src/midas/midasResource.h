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

    typedef enum {
          COMMUNITY,
          FOLDER,
          ITEM,
          BITSTREAM,
          NOTSET
       } Type ;


   midasResource();
   midasResource(const int& id,const std::string& name,const Type& type);
   virtual ~midasResource();



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
