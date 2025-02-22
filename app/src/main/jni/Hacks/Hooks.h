//
// Created by rosetta on 05/07/2024.
//

// ----------------- UNITY -----------------

void (*orig_PlayerUpdate)(void *_this);

void PlayerUpdate(void *_this) {
    if (_this != nullptr){

    }
    // Call the original function
    orig_PlayerUpdate(_this);
}

// ----------------- ESP -----------------
// If you have an esp feature, you can draw it here
// You can use the Draw class to draw lines, text, etc.

void DrawESP(Draw esp, int screenWidth, int screenHeight) {

    //Example to get camera
    //UnityResolve::UnityType::Camera *camera = UnityResolve::UnityType::Camera::GetMain();
    // Full documentation of UnityResolve.hpp
    // https://github.com/issuimo/UnityResolve.hpp

    if (Vars::PlayerData.ESPCrosshair) {
        // Draw ESP here
        UnityResolve::UnityType::Color crosshair_color = UnityResolve::UnityType::Color(255, 0, 0, 255);
        int crosshair_size;

        if (Vars::PlayerData.CrosshairColor == 0) {
            crosshair_color = UnityResolve::UnityType::Color(255, 0, 0, 255);
        } else if (Vars::PlayerData.CrosshairColor == 1) {
            crosshair_color = UnityResolve::UnityType::Color(0, 255, 0, 255);
        } else {
            crosshair_color = UnityResolve::UnityType::Color(0, 0, 255, 255);
        }


        if (Vars::PlayerData.CrosshairSize > 0) {
            crosshair_size = Vars::PlayerData.CrosshairSize;
        } else {
            crosshair_size = 1;
        }

        esp.DrawCrosshair(crosshair_color,  UnityResolve::UnityType::Vector2(screenWidth / 2, screenHeight / 2), crosshair_size * 10);
    }
}