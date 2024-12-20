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

void hook_thread() {
    // ----------------- Hooks -------------------
    // You can hook your methods here
    // if the class have namespace, you can use it like this:
    // IL2CPP::Hook("WarPolygon.CPlayerLocal", "get_CurrentHeight", 0, (void*) HookPlayerCurrentHeight, (void**)&old_PlayerCurrentHeight);
}


void *hack_thread(void *) {
    LOGI(OBFUSCATE("pthread created"));

    espManager = new ESPManager();

    //Check if target lib is loaded
    do {
        sleep(1);
    } while (!KittyMemory::getLibraryMap(IL2CPP_MODULE).isValid());

    LOGI(OBFUSCATE("%s has been loaded"), (const char *) IL2CPP_MODULE);
    LOGI(OBFUSCATE("Trying to hook in il2cpp now..."));

    do {
        sleep(1);
    } while (!IL2CPP::Initialize());

    LOGI(OBFUSCATE("IL2CPP Resolver initialized"));

    // Start hooking
    LOGI(OBFUSCATE("Starting hooks"));
    hook_thread();
    return NULL;
}

__attribute__((constructor))
void init() {
    // Create a new thread so it does not block the main thread, means the game would not freeze
    pthread_t ptid;
    pthread_create(&ptid, NULL, hack_thread, NULL);
}
