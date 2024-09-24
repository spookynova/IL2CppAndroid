## IL2CPP Resolver
A run-time API resolver for IL2CPP Unity for Android.

Credits
[SneakyEvil IL2CPP_Resolver](https://github.com/sneakyevil/IL2CPP_Resolver)

### Quick Example
```cpp
#include "Il2cpp_Resolver_Android/IL2CPP_Resolver.hpp"

void SomeFunction()
{
    IL2CPP::Initialize(); // This needs to be called once!

    Unity::CGameObject* m_Local = Unity::GameObject::Find("LocalPlayer");
    Unity::CComponent* m_LocalData = m_Local->GetComponent("PlayerData");
    m_LocalData->SetMemberValue<bool>("CanFly", true);
}
```

### Registering OnUpdate Callback
```cpp
void OurUpdateFunction()
{
    // Your special code...
}

void OnLoad()
{
    IL2CPP::Initialize();

    IL2CPP::Callback::Initialize();
    IL2CPP::Callback::OnUpdate::Add(OurUpdateFunction);
}
```

# Requirements
* C++17 or Greater.
* Android only.
