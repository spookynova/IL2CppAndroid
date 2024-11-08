//
// Created by rosetta on 13/09/2024.
//

extern "C"
JNIEXPORT void JNICALL
Java_com_android_support_Natives_OnDrawLoad(JNIEnv *env, jclass clazz, jobject draw_view, jobject canvas) {
    Draw draw = Draw(env, draw_view, canvas);
    if (draw.isValid()) {
        DrawESP(draw, draw.getWidth(), draw.getHeight());
    }
}


extern "C"
JNIEXPORT void JNICALL
Java_com_android_support_Natives_LoadNativeLibPath(JNIEnv *env, jclass clazz, jstring native_lib_dir) {
    LOGE("Load injector in pid %d", getpid());
    const char* dir = env->GetStringUTFChars(native_lib_dir, 0);
    std::string path = std::string(dir) + "/libLoader.so";

    //Open the library containing the actual code
    void *open = dlopen(path.c_str(), RTLD_NOW);
    if (open == nullptr) {
        LOGE("Error opening %s %s", path.c_str(), dlerror());
    }
    RemapTools::RemapLibrary(OBFUSCATE("libLoader.so"));
}

// Do not change or translate the first text unless you know what you are doing
// Assigning feature numbers is optional. Without it, it will automatically count for you, starting from 0
// Assigned feature numbers can be like any numbers 1,3,200,10... instead in order 0,1,2,3,4,5...
// ButtonLink, Category, RichTextView and RichWebView is not counted. They can't have feature number assigned
// Toggle, ButtonOnOff and Checkbox can be switched on by default, if you add True_. Example: CheckBox_True_The Check Box
// To learn HTML, go to this page: https://www.w3schools.com/

jobjectArray GetFeatureList(JNIEnv *env, jobject context) {
    jobjectArray ret;

    const char *features[] = {
            OBFUSCATE("ICollapse_ESP Hack"),
            OBFUSCATE("1_CollapseAdd_ICheckBox_Crosshair"),
            OBFUSCATE("2_CollapseAdd_ISpinner_Crosshair Color_Red,Green,Blue"),
            OBFUSCATE("3_CollapseAdd_ISlider_Crosshair Size_1_5"),
            OBFUSCATE("4_CollapseAdd_ISwitch_Player Box"),
            OBFUSCATE("5_CollapseAdd_IRadioButton_Box Type_2D,3D"),
            OBFUSCATE("6_CollapseAdd_IInputText_Player Name"),
            OBFUSCATE("7_CollapseAdd_IInputInt_Player Health"),
            OBFUSCATE("8_CollapseAdd_IButton_Player ESP"),
            OBFUSCATE("9_CollapseAdd_IButtonLink_Website_https://github.com/spookynova/IL2CppAndroid"),
            OBFUSCATE("10_CollapseAdd_ICategory_Player Info"),
            OBFUSCATE("11_CollapseAdd_ITextView_<b>Player Info</b> <br> <i>Player Name: </i> <u>Unknown</u> <br> <i>Player Health: </i> <u>100</u>"),

            };

    //Now you dont have to manually update the number everytime;
    int Total_Feature = (sizeof features / sizeof features[0]);
    ret = (jobjectArray)
            env->NewObjectArray(Total_Feature, env->FindClass(OBFUSCATE("java/lang/String")),
                                env->NewStringUTF(""));

    for (int i = 0; i < Total_Feature; i++)
        env->SetObjectArrayElement(ret, i, env->NewStringUTF(features[i]));

    return (ret);
}

void Changes(JNIEnv *env, jclass clazz, jobject obj,jint featNum, jstring featName, jint value,jboolean boolean, jstring str) {

    LOGD(OBFUSCATE("Feature name: %d - %s | Value: = %d | Bool: = %d | Text: = %s"), featNum,env->GetStringUTFChars(featName, 0), value,boolean, str != NULL ? env->GetStringUTFChars(str, 0) : "");

    //BE CAREFUL NOT TO ACCIDENTLY REMOVE break;

    switch (featNum) {
        case 1:
            Vars::PlayerData.ESPCrosshair = boolean;
            break;
        case 2:
            Vars::PlayerData.CrosshairColor = value;
            break;
        case 3:
            Vars::PlayerData.CrosshairSize = value;
            break;
        default:
            break;
    }
}


int RegisterMenu(JNIEnv *env) {
    jclass clazz = env->FindClass("com/android/support/Natives");
    if (!clazz) {
        LOGE(OBFUSCATE("Natives class not found"));
        return JNI_ERR; // Class not found
    }

    static const JNINativeMethod methods[] = {
            {OBFUSCATE("IsGameLibLoaded"), OBFUSCATE("()Z"),                    reinterpret_cast<void *>(isGameLibLoaded)},
            {OBFUSCATE("Init"),            OBFUSCATE("(Landroid/content/Context;Landroid/widget/TextView;Landroid/widget/TextView;)V"), reinterpret_cast<void *>(Init)},
            {OBFUSCATE("GetFeatureList"),  OBFUSCATE("()[Ljava/lang/String;"),                                                          reinterpret_cast<void *>(GetFeatureList)},
            {OBFUSCATE("CheckOverlayPermission"), OBFUSCATE("(Landroid/content/Context;)V"),              reinterpret_cast<void *>(CheckOverlayPermission)},
    };

    jint ret = env->RegisterNatives(clazz, methods, sizeof(methods) / sizeof(JNINativeMethod));
    if (ret != JNI_OK) {
        return ret; // Failed to register natives
    }

    LOGI(OBFUSCATE("Menu registered"));
    return JNI_OK;
}

int RegisterPreferences(JNIEnv *env) {
    JNINativeMethod methods[] = {
            {OBFUSCATE("Changes"),
             OBFUSCATE("(Landroid/content/Context;ILjava/lang/String;IZLjava/lang/String;)V"),
             reinterpret_cast<void *>(Changes)},
    };
    jclass clazz = env->FindClass(OBFUSCATE("com/android/support/Preferences"));
    if (!clazz)
        return JNI_ERR;
    if (env->RegisterNatives(clazz, methods, sizeof(methods) / sizeof(methods[0])) != 0)
        return JNI_ERR;
    return JNI_OK;
}


extern "C"
JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    vm->GetEnv((void **) &env, JNI_VERSION_1_6);

    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR; // Failed to obtain JNIEnv
    }

    if (RegisterMenu(env) != 0)
        return JNI_ERR;
    if (RegisterPreferences(env) != 0)
        return JNI_ERR;

    return JNI_VERSION_1_6;
}