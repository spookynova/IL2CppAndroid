#include <list>
#include <vector>
#include <cstring>
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
#include "UnityResolve/UnityResolve.hpp"
#include "Includes/Utils.h"
#include "Includes/RemapTools.h"
#include "KittyMemory/MemoryPatch.h"
#include "Includes/ObscuredTypes.h"
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
    // If your class have a namespace, you don't need to add the namespace to the class name
    // Example RedNetwork.PlayerManager -> PlayerManager

    // m_pArgs = {} if the method doesn't have any arguments
    // if you're lazy to input type data on args, you can use "*" depends how many arguments the method have
    // Example: m_pArgs = {"*", "*", "*"} if the method have 3 arguments
    UnityResolve::Hook(OBFUSCATE("PlayerManager"), OBFUSCATE("Update"), {}, (void *) PlayerUpdate, (void **) &orig_PlayerUpdate);

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

    //sleep(5); // this is optional depending on your game if it takes time to load all the symbols

    UnityResolve::Init(dlopen(IL2CPP_MODULE, RTLD_NOW));

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
