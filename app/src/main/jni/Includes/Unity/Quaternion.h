class Quaternion {
public:
    float a;
    float b;
    float c;
    float d;

    Quaternion() {a = 1; b = c = d = 0;}

    // This is a vector that can be rotated in Quaternion space.
    Quaternion(float x, float y, float z) {a = 0; b = x; c = y; d = z;}

    // This returns a Quaternion that rotates in each given axis in radians.
    // We use standard right hand rule for rotations and coordinates.
    static const Quaternion from_euler_rotation(float x, float y, float z);

    // This is like from_euler_rotation but for small angles (less than 45 deg (PI/4))
    static const Quaternion from_euler_rotation_approx(float x, float y, float z);

    Quaternion & operator=(const Quaternion &rhs) {
        a = rhs.a;
        b = rhs.b;
        c = rhs.c;
        d = rhs.d;
        return *this;
    }

    // http://www.euclideanspace.com/maths/algebra/realNormedAlgebra/quaternions/arithmetic/index.htm
    Quaternion & operator*=(const Quaternion &q);
    const Quaternion operator* (const Quaternion& q) const { return Quaternion(*this) *= q; }
    Quaternion & operator+=(const Quaternion &q);
    const Quaternion operator+(const Quaternion& q) const { return Quaternion(*this) += q; }
    Quaternion & operator*=(float scale);
    const Quaternion operator*(float scale) const { return Quaternion(*this) *= scale; }
    float norm() const;
    Quaternion & normalize();
    const Quaternion conj() const;
    // This method takes two vectors and computes the rotation vector between them.
    // Both the left and right hand sides must be pure vectors (a == 0)
    // Both the left and right hand sides must normalized already.
    // This computes the rotation that will tranform this to q.
    const Quaternion rotation_between_vectors(const Quaternion& v) const;
    float dot_product(const Quaternion& q) const;

    // This method takes one vector and rotates it using this Quaternion.
    // The input must be a pure vector (a == 0)
    const Quaternion rotate(const Quaternion& q) const;
    Quaternion & fractional(float f);
};
