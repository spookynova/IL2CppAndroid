#pragma once

namespace Unity
{
    struct Vector4
    {
        float x, y, z, w;

        Vector4() { x = y = z = w = 0.f; }
        Vector4(float f1, float f2, float f3, float f4) { x = f1; y = f2; z = f3; w = f4; }
    };
    
    struct Bounds
    {
        Vector3 m_vCenter;
        Vector3 m_vExtents;
    };

    struct Plane
    {
        Vector3 m_vNormal;
        float fDistance;
    };

    struct Ray
    {
        Vector3 m_vOrigin;
        Vector3 m_vDirection;
    };

    struct Rect
    {
        float fX, fY;
        float fWidth, fHeight;

        Rect() { fX = fY = fWidth = fHeight = 0.f; }
        Rect(float f1, float f2, float f3, float f4) { fX = f1; fY = f2; fWidth = f3; fHeight = f4; }
    };

    struct Color
    {
        float r, g, b, a;

        Color() { r = g = b = a = 0.f; }
        Color(float fRed = 0.f, float fGreen = 0.f, float fBlue = 0.f, float fAlpha = 1.f) { r = fRed; g = fGreen; b = fBlue; a = fAlpha; }
    };

    struct Matrix4x4
    {
        float m[4][4] = { 0 };

        Matrix4x4() { }

        float* operator[](int i) { return m[i]; }
    };
    
    struct CSMatrix4x4
    {
        float 
        m11 = 0, m12 = 0, m13 = 0, m14 = 0,
        m21 = 0, m22 = 0, m23 = 0, m24 = 0,
        m31 = 0, m32 = 0, m33 = 0, m34 = 0,
        m41 = 0, m42 = 0, m43 = 0, m44 = 0;
        
        CSMatrix4x4() { }
    };
}
