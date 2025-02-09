//
// Created by rosetta on 05/07/2024.
//

// ----------------- UNITY -----------------

void (*orig_PlayerUpdate)(void *_this);

void PlayerUpdate(void *_this) {
    // Call the original function

    // orig_PlayerUpdate(_this);
}

// ----------------- ESP -----------------
// If you have an esp feature, you can draw it here
// You can use the Draw class to draw lines, text, etc.
// Example: esp.DrawCrosshair(Unity::Color::Red(),  Unity::Vector2(screenWidth / 2, screenHeight / 2), 10);

void DrawESP(Draw esp, int screenWidth, int screenHeight) {

     //Example to get camera
     //Unity::CCamera *camera = Unity::Camera::GetMain();

     //Example to get world to screen point

     //Unity::Vector3 worldPos = Unity::Vector3(0, 0, 0);
     //Unity::Vector3 screenPos;
     //camera->WorldToScreen(worldPos, screenPos,2);

    if (Vars::PlayerData.ESPCrosshair) {
        // Draw ESP here
        Unity::Color crosshair_color;
        int crosshair_size;

        if (Vars::PlayerData.CrosshairColor == 0) {
            crosshair_color = Unity::Color::Red();
        } else if (Vars::PlayerData.CrosshairColor == 1) {
            crosshair_color = Unity::Color::Green();
        } else {
            crosshair_color = Unity::Color::Blue();
        }


        if (Vars::PlayerData.CrosshairSize > 0) {
            crosshair_size = Vars::PlayerData.CrosshairSize;
        } else {
            crosshair_size = 1;
        }

        esp.DrawCrosshair(crosshair_color,  Unity::Vector2(screenWidth / 2, screenHeight / 2), crosshair_size * 10);
    }
}