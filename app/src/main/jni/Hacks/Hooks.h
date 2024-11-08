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
        LOGD("Drawing ESP");
        // Draw ESP here
        esp.DrawCrosshair(Color::Red(), Vector2(screenWidth / 2, screenHeight / 2), 100);
    }
}