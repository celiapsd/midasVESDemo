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
    init(-1, NULL, NOTSET);

}
midasResource::~midasResource()
{
    LOGI("destructor midasResource");
    delete this;
}
//----------------------------------------------------------------------------
void midasResource::init(int id, std::string name, Type type)
{
   LOGI("init");
   setId(id);
   setName(name);
   setType(type);
}
//----------------------------------------------------------------------------
void midasResource::setId(int id)
{
    this.id = id;
}
//----------------------------------------------------------------------------
void midasResource::setName(std::string name)
{
    this.name = name;
}
//----------------------------------------------------------------------------
void midasResource::setType(Type type)
{
    this.type = type;
}
