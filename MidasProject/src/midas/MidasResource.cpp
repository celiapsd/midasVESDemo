#include <sys/types.h>
#include <android/log.h>

#include <cstdlib>
#include <cstdio>
#include <cstring>
#include <vector>
#include <iostream>
#include <sstream>
#include <fstream>
#include <cassert>


#include <MidasResource.h>



#define  LOG_TAG    "MidasResource"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

//----------------------------------------------------------------------------

MidasResource::MidasResource()
{
    LOGI("constructor MidasResource");
    init(-1, "", NOTSET);

}
MidasResource::MidasResource(const int& id,const std::string& name,const Type& type)
{
    //LOGI("init");
    setId(id);
    //LOGI("setID, %d",id);
    setName(name);
    LOGI("setName %s",name.c_str());
    setType(type);
    //LOGI("setType");

}
MidasResource::~MidasResource()
{
}

//----------------------------------------------------------------------------
void MidasResource::init(const int& id,const std::string& name,const Type& type)
{
   //LOGI("init");
   setId(id);
   //LOGI("setID, %d",id);
   setName(name);
   //LOGI("setName %s",name.c_str());
   setType(type);
   //LOGI("setType");
}
//----------------------------------------------------------------------------
void MidasResource::setId(const int& mId)
{
     //LOGI("setID");
     this->id = mId;
}
//----------------------------------------------------------------------------
void MidasResource::setName(const std::string& mName)
{
    this->name = mName;
}
//----------------------------------------------------------------------------
void MidasResource::setType(const Type& mType)
{
    this->type = mType;
}
//----------------------------------------------------------------------------
int MidasResource::getId() const
{
    return this->id;
}
//----------------------------------------------------------------------------
std::string MidasResource::getName() const
{
    return this->name;
}
//----------------------------------------------------------------------------
MidasResource::Type MidasResource::getType() const
{
    return this->type;
}
