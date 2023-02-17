# Notcute
[![Discord](https://img.shields.io/discord/1062481080202055752?style=for-the-badge&logo=discord)](https://discord.gg/hghED8mWUd)

An abstract graphics layer based on Android GUI, AWT, SWT and so on.

"Any problem in computer science can be solved with another layer of indirection [except the problem of too many layers of indirection]."  
Notcute is not a framework built from scratch, just an indirection layer over existing frameworks.

## Compatibility
- Java SE: Java 11 or above
- Android: SDK 16 (version 4.1) or above
- All source are Java 8 compatible (the project is non-modular) grammatically, but the Java SE backend using some Java 11 features
- If you want to share code between platforms, you should use [a subset of Java 8](https://developer.android.com/studio/write/java8-support-table)

## Design
- A cross-platform desktop/mobile Widget Toolkit/Game Engine for Java.
- Modular and flexible.
- Can be embedded to related GUI frameworks (Android GUI, AWT, etc).
- Using Qt-like signal/slot instead of traditional callbacks, to prevent the 'callback hell'.

## Backends
```
ui-core
├── ui-android
│   └── Android GUI
│       └── Android
├── ui-awt
│   └── AWT
│       ├── Cocoa
│       │   └── macOS
│       ├── Win32
│       │   └── Windows
│       └── X11
│           ├── *nix⁄X11
│           └── XWayland
│               └── *nix⁄Wayland
├── (WIP) ui-ikvm
│   ├── UWP
│   │   ├── Windows Phone
│   │   └── Windows⁄UWP
│   └── Win32
│       └── Windows
├── (WIP) ui-javafx
│   └── JavaFX
│       ├── Cocoa
│       │   └── macOS
│       ├── GTK
│       │   ├── *nix⁄Wayland
│       │   └── *nix⁄X11
│       ├── JavaFXPorts
│       │   ├── Android
│       │   └── iOS
│       └── Win32
│           └── Windows
├── (WIP) ui-qtjambi
│   └── Qt-Jambi
│       └── Qt
│           ├── Android
│           ├── macOS
│           ├── *nix⁄Wayland
│           ├── *nix⁄X11
│           └── Windows
├── (WIP) ui-robovm
│   └── CocoaTouch
│       └── iOS
├── (WIP) ui-swt
│   └── SWT
│       ├── Cocoa
│       │   └── macOS
│       ├── GTK
│       │   ├── *nix⁄Wayland
│       │   └── *nix⁄X11
│       └── Win32
│           └── Windows
└── (WIP) ui-teavm
    └── TeaVM
        └── HTML5
            └── Modern Browsers
```

## TODO List
- Port to SWT, TeaVM, JavaFX, Qt-Jambi, RoboVM, IKVM.NET.
- [The pluggable system-independent widgets library](/widgets).
- 2D particle library.
- Scaffolding libraries for several kinds of games: STG, AVG, RTS, Roguelike and so on.

## Notes
### Running with AWT/X11 backend & Java 16+
Add the following lines to the JVM args: 
```
--add-exports java.desktop/sun.awt=ALL-UNNAMED
--add-exports java.desktop/sun.awt.X11=ALL-UNNAMED
```
### Running on KDE, with AWT/X11 backend & Java 16+
```
--add-opens java.desktop/java.awt=ALL-UNNAMED
```

## Contributing
The Notcute project currently maintained by only [me](https://github.com/Tianscar).  
Since I'm not a skilled programmer, the code may be badly written...  
So contributions & PRs welcome!

## License
[Apache-2.0](LICENSE) (c) Tianscar

### This project currently uses some code from the following projects:
Apache-2.0 [Apache Harmony](https://harmony.apache.org)  
[MIT](https://github.com/philfrei/AudioCue-maven/blob/main/LICENSE) [AudioCue-Maven](https://github.com/philfrei/AudioCue-maven)  
[MIT](https://github.com/msteinbeck/sig4j/blob/master/LICENSE) [sig4j](https://github.com/msteinbeck/sig4j)
### This project currently uses the following libraries as dependencies:
[Apache-2.0](https://github.com/jnr/jnr-ffi/blob/master/LICENSE) [jnr-ffi](https://github.com/jnr/jnr-ffi)  
LGPL-2.1 [MP3SPI](https://mvnrepository.com/artifact/com.googlecode.soundlibs/mp3spi/1.9.5.4)  
LGPL-2.1 [VorbisSPI](https://mvnrepository.com/artifact/com.googlecode.soundlibs/vorbisspi/1.0.3.3)  
LGPL-2.1 [jFLAC](https://jflac.sourceforge.net)
