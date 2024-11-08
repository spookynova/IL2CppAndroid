
# IL2CPP Android Template

This project is based on [LGL Mod Menu](https://github.com/LGLTeam/Android-Mod-Menu/). Since the original project is no longer actively maintained, I have made several modifications, including:

- **UI Changes**: Updated the menu interface.
- **New Modules**: Added additional modules to extend functionality.
- **Obfuscation**:
  - Integrated [BlackObfuscator](https://github.com/CodingGay/BlackObfuscator-ASPlugin) for enhanced code obfuscation.
  - Added [LSParanoid](https://github.com/LSPosed/LSParanoid) to provide string encryption for securing sensitive text data.


![Mod Preview](https://raw.githubusercontent.com/spookynova/Base-Android-Mod-Menu/refs/heads/main/img/preview.png)

## Credits

- [IL2CPP Resolver](https://github.com/sneakyevil/IL2CPP_Resolver)
- [IL2CPP Resolver Android Version](https://github.com/KING-UTKARSH/Il2cpp_Resolver_Android)
- [Dobby](https://github.com/jmpews/Dobby/)
- [Remap Hide](https://github.com/reveny/Android-Library-Remap-Hide)


## How to use
To enable and display a floating menu in your target app, follow these steps:

#### 1. Request Permission for Floating Menu
To allow your target app to draw a floating menu overlay on top of other apps, add the following permission to your AndroidManifest.xml file:

```xml
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```

This permission is required for overlaying UI elements, such as a floating menu, across other applications.

#### 2. Register the Service to Show the Menu
To activate the floating menu, declare a service in `AndroidManifest.xml` to your targer app. This service will handle the display and lifecycle of the floating menu:
```xml
<service
    android:name="com.android.support.Launcher"
    android:enabled="true"
    android:exported="true"
    android:stopWithTask="true" />
```

#### 3. Call the Loader class
To integrate and initialize the floating menu loader, add the following code in the `onCreate` method within `UnityPlayerActivity.smali`. If your target app uses a custom launcher activity, locate it in `AndroidManifest.xml` and insert the code in that activity’s `onCreate` method instead.

```smali
invoke-static {p0}, Lcom/android/support/Loader;->Start(Landroid/content/Context;)V
```

### Note

And this project containing assets like font, you also need to add the font from assets to game assets, otherwise the game will crash immediately. If you want to disable it, go to Menu.java and replace

```java
typeface = Typeface.createFromAsset(context.getAssets(), "Pixellari.ttf");
```

to

```java
typeface = Typeface.DEFAULT;
```
