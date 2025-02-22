

#include <jni.h>
class Draw {
private:
    JNIEnv *_env;
    jobject _cvsView;
    jobject _cvs;

public:
    Draw() {
        _env = nullptr;
        _cvsView = nullptr;
        _cvs = nullptr;
    }

    Draw(JNIEnv *env, jobject cvsView, jobject cvs) {
        this->_env = env;
        this->_cvsView = cvsView;
        this->_cvs = cvs;
    }

    bool isValid() const {
        return (_env != nullptr && _cvsView != nullptr && _cvs != nullptr);
    }

    int getWidth() const {
        if (isValid()) {
            jclass canvas = _env->GetObjectClass(_cvs);
            jmethodID width = _env->GetMethodID(canvas, "getWidth", "()I");
            return _env->CallIntMethod(_cvs, width);
        }
        return 0;
    }

    int getHeight() const {
        if (isValid()) {
            jclass canvas = _env->GetObjectClass(_cvs);
            jmethodID width = _env->GetMethodID(canvas, "getHeight", "()I");
            return _env->CallIntMethod(_cvs, width);
        }
        return 0;
    }

    void DrawLine(UnityResolve::UnityType::Color color, float thickness, UnityResolve::UnityType::Vector2 start, UnityResolve::UnityType::Vector2 end) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawline = _env->GetMethodID(canvasView, "DrawLine",
                                                   "(Landroid/graphics/Canvas;IIIIFFFFF)V");
            _env->CallVoidMethod(_cvsView, drawline, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b,
                                 thickness,
                                 start.x, start.y, end.x, end.y);
        }
    }

    void DrawText(UnityResolve::UnityType::Color color, const char *txt, UnityResolve::UnityType::Vector2 pos, float size) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawtext = _env->GetMethodID(canvasView, "DrawText",
                                                   "(Landroid/graphics/Canvas;IIIILjava/lang/String;FFF)V");
            _env->CallVoidMethod(_cvsView, drawtext, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b,
                                 _env->NewStringUTF(txt), pos.x, pos.y, size);
        }
    }

    // public void DrawCircle(Canvas cvs, int a, int r, int g, int b, float stroke, float posX, float posY, float radius) {
    void DrawCircle(UnityResolve::UnityType::Color color, float stroke, UnityResolve::UnityType::Vector2 pos, float radius) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawcircle = _env->GetMethodID(canvasView, "DrawCircle",
                                                     "(Landroid/graphics/Canvas;IIIIFFFF)V");
            _env->CallVoidMethod(_cvsView, drawcircle, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b, stroke, pos.x, pos.y, radius);
        }
    }

    void DrawFilledCircle(UnityResolve::UnityType::Color color, UnityResolve::UnityType::Vector2 pos, float radius) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_cvsView);
            jmethodID drawfilledcircle = _env->GetMethodID(canvasView, "DrawFilledCircle",
                                                           "(Landroid/graphics/Canvas;IIIIFFF)V");
            _env->CallVoidMethod(_cvsView, drawfilledcircle, _cvs, (int) color.a, (int) color.r,
                                 (int) color.g, (int) color.b, pos.x, pos.y, radius);
        }
    }

    void DrawBox(UnityResolve::UnityType::Color color, float stroke, UnityResolve::UnityType::Rect rect) {
        UnityResolve::UnityType::Vector2 v1 = UnityResolve::UnityType::Vector2(rect.fX, rect.fY);
        UnityResolve::UnityType::Vector2 v2 = UnityResolve::UnityType::Vector2(rect.fX + rect.fWidth, rect.fY);
        UnityResolve::UnityType::Vector2 v3 = UnityResolve::UnityType::Vector2(rect.fX + rect.fWidth, rect.fY + rect.fHeight);
        UnityResolve::UnityType::Vector2 v4 = UnityResolve::UnityType::Vector2(rect.fX, rect.fY + rect.fHeight);

        DrawLine(color, stroke, v1, v2);
        DrawLine(color, stroke, v2, v3);
        DrawLine(color, stroke, v3, v4);
        DrawLine(color, stroke, v4, v1);
    }

    void DrawHorizontalHealthBar(UnityResolve::UnityType::Vector2 screenPos, float width, float maxHealth, float currentHealth) {
        screenPos -= UnityResolve::UnityType::Vector2(0.0f, 8.0f);
        DrawBox(UnityResolve::UnityType::Color(0, 0, 0, 255), 3, UnityResolve::UnityType::Rect(screenPos.x, screenPos.y, width + 2, 5.0f));
        screenPos += UnityResolve::UnityType::Vector2(1.0f, 1.0f);
        UnityResolve::UnityType::Color clr = UnityResolve::UnityType::Color(0, 255, 0, 255);
        float hpWidth = (currentHealth * width) / maxHealth;
        if (currentHealth <= (maxHealth * 0.6)) {
            clr = UnityResolve::UnityType::Color(255, 255, 0, 255);
        }
        if (currentHealth < (maxHealth * 0.3)) {
            clr = UnityResolve::UnityType::Color(255, 0, 0, 255);
        }
        DrawBox(clr, 3, UnityResolve::UnityType::Rect(screenPos.x, screenPos.y, hpWidth, 3.0f));
    }

    void DrawCrosshair(UnityResolve::UnityType::Color clr, UnityResolve::UnityType::Vector2 center, int size = 20) {
        float x = center.x - (size / 2.0f);
        float y = center.y - (size / 2.0f);
        DrawLine(clr, 3, UnityResolve::UnityType::Vector2(x, center.y), UnityResolve::UnityType::Vector2(x + size, center.y));
        DrawLine(clr, 3, UnityResolve::UnityType::Vector2(center.x, y), UnityResolve::UnityType::Vector2(center.x, y + size));
    }
};

