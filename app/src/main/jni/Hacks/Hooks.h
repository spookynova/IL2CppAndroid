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
// Example: esp.DrawCrosshair(Color::Red(),  Vector2(screenWidth / 2, screenHeight / 2), 10);

void DrawESP(Draw esp, int screenWidth, int screenHeight) {
    if (Vars::PlayerData.ESPCrosshair) {
        // Draw ESP here
        Color crosshair_color = Color::Red();
        int crosshair_size = 0;

        if (Vars::PlayerData.CrosshairColor == 0) {
            crosshair_color = Color::Red();
        } else if (Vars::PlayerData.CrosshairColor == 1) {
            crosshair_color = Color::Green();
        } else {
            crosshair_color = Color::Blue();
        }



        if (Vars::PlayerData.CrosshairSize > 0) {
            crosshair_size = Vars::PlayerData.CrosshairSize;
        } else {
            crosshair_size = 1;
        }

        esp.DrawCrosshair(crosshair_color,  Vector2(screenWidth / 2, screenHeight / 2), crosshair_size * 10);
    }
}