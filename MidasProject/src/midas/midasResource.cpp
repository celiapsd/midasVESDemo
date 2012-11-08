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


#include <midasResource.h>



#define  LOG_TAG    "midasResource"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

//----------------------------------------------------------------------------

midasResource::midasResource()
{
    LOGI("constructor midasResource");
    init(-1, "", NOTSET);

}
midasResource::midasResource(const int& id,const std::string& name,const Type& type)
{
    //LOGI("init");
    setId(id);
    //LOGI("setID, %d",id);
    setName(name);
    LOGI("setName %s",name.c_str());
    setType(type);
    //LOGI("setType");

}
midasResource::~midasResource()
{
}

//----------------------------------------------------------------------------
void midasResource::init(const int& id,const std::string& name,const Type& type)
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
void midasResource::setId(const int& mId)
{
     //LOGI("setID");
     this->id = mId;
}
//----------------------------------------------------------------------------
void midasResource::setName(const std::string& mName)
{
    this->name = mName;
}
//----------------------------------------------------------------------------
void midasResource::setType(const Type& mType)
{
    this->type = mType;
}
//----------------------------------------------------------------------------
int midasResource::getId() const
{
    return this->id;
}
//----------------------------------------------------------------------------
std::string midasResource::getName() const
{
    return this->name;
}
//----------------------------------------------------------------------------
midasResource::Type midasResource::getType() const
{
    return this->type;
}
