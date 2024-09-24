# Android Mod Menu Template

![Mod Preview](https://raw.githubusercontent.com/spookynova/Base-Android-Mod-Menu/refs/heads/main/img/preview.png)


This project based on [LGL Mod Menu](https://github.com/LGLTeam/Android-Mod-Menu/), i'm just changing the menu, adding some modules and other thing, also i'm adding [BlackObfuscator](https://github.com/CodingGay/BlackObfuscator-ASPlugin) and [LSParanoid](https://github.com/LSPosed/LSParanoid).

## Thanks to

- [IL2CPP Resolver](https://github.com/sneakyevil/IL2CPP_Resolver)
- [IL2CPP Resolver Android](https://github.com/KING-UTKARSH/Il2cpp_Resolver_Android)
- [Dobby](https://github.com/jmpews/Dobby/)
- [Remap Hide](https://github.com/reveny/Android-Library-Remap-Hide)

## How to use

Add this to OnCreate in UnityPlayerActivity.smali or if the game has custom launcher one, you can find it on AndroidManifest.xml

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

Find more useful things about this on [Wiki](https://github.com/LGLTeam/Android-Mod-Menu/wiki)
