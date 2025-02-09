//
// Created by rosetta on 16/09/2024.
//

#include <cstdio>
#include <string>
#include <jni.h>
#include <dlfcn.h>
#include <unistd.h>

#include "Includes/obfuscate.h"
#include "Includes/Logger.h"
#include "Includes/RemapTools.h"
#include "Includes/Utils.h"
#include "Menu/Setup.h"


bool remapLoaded = false;

std::string GetNativeLibraryDirectory() {
    char buffer[512];
    FILE *fp = fopen(OBFUSCATE("/proc/self/maps"), OBFUSCATE("re"));
    if (fp != nullptr) {
        while (fgets(buffer, sizeof(buffer), fp)) {
            if (strstr(buffer, OBFUSCATE("libLoader.so"))) {
                RemapTools::ProcMapInfo info{};
                char perms[10];
                char path[255];
                char dev[25];

                sscanf(buffer, OBFUSCATE("%lx-%lx %s %ld %s %ld %s"), &info.start, &info.end, perms,
                       &info.offset, dev, &info.inode, path);
                info.path = path;

                //Remove libLoader.so to get path
                std::string pathStr = std::string(info.path);
                if (!pathStr.empty()) {
                    pathStr.resize(pathStr.size() - 12); //libLoader.so = 12 chars
                }

                return pathStr;
            }
        }
    }
    return "";
}


jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    LOGI(OBFUSCATE("libLoader.so loaded in %d"), getpid());

    std::string native = GetNativeLibraryDirectory();
    if (native.empty()) {
        LOGE(OBFUSCATE("Error getting native library directory"));
        exit(1);
    }

    LOGI(OBFUSCATE("Found native library directory: %s"), native.c_str());
    std::string path = native + std::string(OBFUSCATE("libGameHelper.so"));

    // Open the library containing the actual code
    void *open = dlopen(path.c_str(), RTLD_NOW);
    if (open == nullptr) {
        LOGE(OBFUSCATE("Error opening libGameHelper.so %s"), dlerror());
        return JNI_ERR;
    }

    RemapTools::RemapLibrary(OBFUSCATE("libGameHelper.so"));
    remapLoaded = true;

    return JNI_VERSION_1_6;
}


extern "C"
JNIEXPORT jboolean JNICALL
Java_com_android_support_Loader_isRemapSuccess(JNIEnv *env, jclass clazz) {
    return remapLoaded;
}
