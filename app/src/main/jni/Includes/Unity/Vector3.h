class Vector3 {
public:
    float x;
    float y;
    float z;
    Vector3()
            : x(0)
            , y(0)
            , z(0)
    {
    }
    Vector3(float x1, float y1, float z1)
            : x(x1)
            , y(y1)
            , z(z1)
    {
    }
    Vector3(const Vector3& v);
    ~Vector3();
};
Vector3::Vector3(const Vector3& v)
        : x(v.x)
        , y(v.y)
        , z(v.z)
{
}
Vector3::~Vector3() {}