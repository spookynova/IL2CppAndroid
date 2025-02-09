
/*
Thanks to dollaz for their obscured types functions:
https://platinmods.com/threads/how-to-hook-obscured-lgl-mod-menu.153735/ (scroll down to replies)
I modified them and added some of my own
*/

union intfloat {
    int i;
    float f;
};

int GetObscuredInt(uint64_t location){
    int cryptoKey = *(int *)location;
    int encryptedValue = *(int *)(location + 0x4);
    int realValue = encryptedValue ^ cryptoKey;
    return realValue;
}
void SetObscuredInt(uint64_t location, int value){
    int cryptoKey = *(int *)location;
    int encryptedValue = value ^ cryptoKey;
    *(int *)(location + 0x4) = encryptedValue;
}
int GetObscuredIntAB(uint64_t location){
    int cryptoKey = *(int *)location;
    int encryptedValue = *(int *)(location + 0x4);
    int realValue = encryptedValue ^ cryptoKey;
    return realValue;
}
void SetObscuredIntAB(uint64_t location, int value){
    int cryptoKey = *(int *)location;
    int encryptedValue = value ^ cryptoKey;
    *(int *)(location + 0x4) = encryptedValue;
}
float GetObscuredFloat(uint64_t location){
    int cryptoKey = *(int *)location;
    intfloat encryptedValue;
    encryptedValue.f = *(float *)(location + 0x4);
    intfloat realValue;
    realValue.i = encryptedValue.i ^ cryptoKey;
    return realValue.f;
}
void SetObscuredFloat(uint64_t location, float value){
    int cryptoKey = *(int *)location;
    intfloat realValue;
    realValue.f = value;
    intfloat encryptedValue;
    encryptedValue.i = realValue.i ^ cryptoKey;
    *(float *)(location + 0x4) = encryptedValue.f;
}
