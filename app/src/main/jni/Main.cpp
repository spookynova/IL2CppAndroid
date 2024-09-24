#include <list>
#include <vector>
#include <string.h>
#include <pthread.h>
#include <thread>
#include <cstring>
#include <jni.h>
#include <unistd.h>
#include <fstream>
#include <sys/mman.h>
#include <iostream>
#include <dlfcn.h>
#include <numbers>
#include "Includes/Logger.h"
#include "Includes/obfuscate.h"
#include "dobby/dobby.h"
#include "IL2CPPResolver/IL2CPP_Resolver.hpp"
#include "Includes/Utils.h"
#include "Includes/RemapTools.h"
#include "KittyMemory/MemoryPatch.h"
#include "Includes/ObsecuresTypes.h"
#include "Includes/Unity/Vector3.h"
#include "Includes/Unity/Vector2.h"
#include "Includes/Unity/Color.h"
#include "Includes/Unity/Rect.h"
#include "Includes/ESPManager.h"
#include "Includes/Draw.h"
#include "Hacks/Vars.h"
#include "Hacks/Offsets.h"
#include "Hacks/Hooks.h"
#include "Menu/Setup.h"
#include "Menu/Features.h"

//Target lib here
#define IL2CPP_MODULE OBFUSCATE("libil2cpp.so")

#define HOOK(clazz, method, count, new_method, old_method) IL2CPP::Hook(IL2CPP::Class::Utils::GetMethodPointer(OBFUSCATE(clazz), OBFUSCATE(method),count), (void *) &new_method, (void **) &old_method)

void hook_thread() {
    // ----------------- Unity Engine -------------------
    get_transform = (void *(*)(void *)) IL2CPP::Class::Utils::GetMethodPointer( OBFUSCATE("Component"), OBFUSCATE("get_transform"), 0);
    get_position_Injected = (void (*)(void *, Vector3 &)) IL2CPP::Class::Utils::GetMethodPointer( OBFUSCATE("Transform"), OBFUSCATE("get_position_Injected"), 1);
    WorldToScreenPoint_Injected = (void (*)(void *, Vector3, int, Vector3 &)) IL2CPP::Class::Utils::GetMethodPointer( OBFUSCATE("Camera"), OBFUSCATE("WorldToScreenPoint_Injected"), 3);
    // -------------------------------------------------
}


void *hack_thread(void *) {
    LOGI(OBFUSCATE("pthread created"));

    //Check if target lib is loaded
    do {
        sleep(1);
    } while (!isLibraryLoaded(IL2CPP_MODULE));

    LOGI(OBFUSCATE("%s has been loaded"), (const char *) IL2CPP_MODULE);
    LOGI(OBFUSCATE("Trying to hook in il2cpp now..."));

    if (!IL2CPP::Initialize(true, 10))
        LOGE(OBFUSCATE("Failed to initialize IL2CPP Resolver"));
    else
        LOGI(OBFUSCATE("IL2CPP Resolver initialized"));

    // sleep for 10 seconds to make sure all the libs are loaded
    sleep(10);

    // Start hooking
    hook_thread();
    return NULL;
}

__attribute__((constructor))
void init() {
    // Create a new thread so it does not block the main thread, means the game would not freeze
    pthread_t ptid;
    pthread_create(&ptid, NULL, hack_thread, NULL);
}
