![Mod Preview](https://github.com/user-attachments/assets/1102b880-769e-4439-9221-2ac579f65235)

# IL2CPP Android Template

This project is based on [LGL Mod Menu](https://github.com/LGLTeam/Android-Mod-Menu/). Since the original project is no longer actively maintained, I have made several modifications, including:

- **UI Changes**: Updated the menu interface.
- **New Modules**: Added additional modules to extend functionality.
- **Obfuscation**:
  - Integrated [BlackObfuscator](https://github.com/CodingGay/BlackObfuscator-ASPlugin) for enhanced code obfuscation.
  - Added [LSParanoid](https://github.com/LSPosed/LSParanoid) to provide string encryption for securing sensitive text data.

> [!NOTE]\
> Starting from version 1.3, `Il2cppResolver` has been replaced with `UnityResolve` to offer more extensive API support and improved functionality.
> 
> New feature suggestions or bugs can be commit as issues.

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
<service android:name="com.android.support.Launcher" android:enabled="true" android:exported="true" android:stopWithTask="true" />
```

#### 3. Call the Loader class
To integrate and initialize the floating menu loader, add the following code in the `onCreate` method within `UnityPlayerActivity.smali`. If your target app uses a custom launcher activity, locate it in `AndroidManifest.xml` and insert the code in that activity’s `onCreate` method instead.

```smali
invoke-static {p0}, Lcom/android/support/Loader;->Start(Landroid/content/Context;)V
```

If you prefer not to use `Remap` , simply call this method:
```smali
invoke-static {p0}, Lcom/android/support/Main;->Start(Landroid/content/Context;)V
```

## Customizing Fonts  

You can integrate custom fonts into your project using a `C array`. To generate the required font data, you can use a hexadecimal editor such as [ImHex](https://github.com/WerWolv/ImHex).  

#### 1. Exporting the Font  

To export a font, follow these steps:  

1. Load the `.ttf` font file into ImHex.  
2. Navigate to the **File** tab and select **Export**.  
3. Choose **Text Formatted Bytes** -> **C Array**.  
4. Save the exported data as a `.h` file.  

#### 2. Importing the Font into Your Project  

1. Copy the exported `.h` font file to the `Includes/Fonts/` directory.  
2. Open `Utils.h` located in the `Includes` folder.  
3. Locate the `LoadFontData` method and modify it to use the newly exported font.  

#### Example: Modifying `LoadFontData`  

```cpp
jbyteArray LoadFontData(JNIEnv *env, jclass thiz, jobject ctx) {
    // Create a byte array in Java to hold the font data
    jbyteArray fontData = env->NewByteArray(std::size(font));

    // Populate the byte array with the embedded font data
    env->SetByteArrayRegion(fontData, 0, std::size(font), (jbyte*)font);

    return fontData;  // Return the font data as a byte array
}
```

#### 3. Updating the Font Variable
Replace the `font` variable in the code above with the array from your exported font file. This ensures that the custom font is correctly loaded and utilized in the project.

## Credits

- [UnityResolve](https://github.com/issuimo/UnityResolve.hpp)
- [Dobby](https://github.com/jmpews/Dobby/)
- [Remap Hide](https://github.com/reveny/Android-Library-Remap-Hide)


## Disclaimer
This project is for Educational Use only. We do not condone this project being used to gain an advantage against other people. This project was made for fun.

We strongly refrain you from buying any source codes on Telegram even if the author can be trusted, there is always a risk getting scammed. We will not be responsible for that. This project is always FREE to use
