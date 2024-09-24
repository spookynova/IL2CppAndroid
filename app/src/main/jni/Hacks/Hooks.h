//
// Created by rosetta on 05/07/2024.
//

// ----------------- UNITY -----------------
void *(*get_transform)(void *instance);
void (*get_position_Injected)(void *instance, Vector3 &ret);
void (*WorldToScreenPoint_Injected)(void *instance, Vector3 position, int eye, Vector3 &ret);
void *(*get_MainCamera)(void *instance);

void *get_camera() {
    static const auto get_camera_injected = reinterpret_cast<uint64_t(__fastcall *)()>(IL2CPP::Class::Utils::GetMethodPointer( OBFUSCATE("Camera"), OBFUSCATE("get_main"), 0));
    return (void *) get_camera_injected();
}

Vector3 get_position(void *instance) {
    if (instance != NULL) {
        Vector3 ret;
        get_position_Injected(instance, ret);
        return ret;
    }
    return Vector3();
}

Vector3 WorldToScreenPoint(void *instance, Vector3 position) {
    if (instance != NULL) {
        Vector3 ret;
        WorldToScreenPoint_Injected(instance, position, 4, ret);
        return ret;
    }
    return Vector3();
}

// ----------------- ESP -----------------
// If you have an esp feature, you can draw it here
// You can use the Draw class to draw lines, text, etc.
// Example: esp.DrawCrosshair(Color::Red(),  Vector2(screenWidth / 2, screenHeight / 2), 10);

void DrawESP(Draw esp, int screenWidth, int screenHeight) {

}