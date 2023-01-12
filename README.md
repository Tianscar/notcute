# A3WT
[![Discord](https://img.shields.io/discord/1062481080202055752?style=for-the-badge&logo=discord)](https://discord.gg/hghED8mWUd)

The abstract layer for Android GUI and AWT.

"Any problem in computer science can be solved with another layer of indirection [except the problem of too many layers of indirection]."  
A3WT is not a framework built from scratch, just an indirection layer over existing frameworks.

## Design
- A cross-platform desktop/mobile Windowing Toolkit/Game Engine for Java.
- Modular and flexible.
- Can be embedded to related GUI frameworks (Android GUI, AWT, etc).

## Architecture

(root node depends on children nodes)
```
a3wt-core
├── a3wt-android
│   └── Android GUI
│       └── Android
├── a3wt-awt
│   └── AWT
│       ├── Cocoa
│       │   └── macOS
│       ├── Motif
│       │   └── *nix⁄Motif
│       ├── Win32
│       │   └── Windows
│       └── X11
│           ├── *nix⁄X11
│           └── XWayland
│               └── *nix⁄Wayland
└── (WIP) a3wt-teavm
    └── TeaVM
        └── HTML5
            └── Modern Browsers
```

(children nodes depend on root node)
```
a3wt-core
├── 2D Graphics Framework
│   ├── Custom Cursor API
│   │   ├── Single-frame Cursor
│   │   └── (WIP) Multi-frame Cursor
│   ├── Graphics API
│   │   ├── Affine Transform API (Java2D-like)
│   │   ├── Geometry Framework (Java2D-like)
│   │   ├── Graphics (Java2D-like)
│   │   └── Vector Path API (Android-like)
│   └── Image Framework
│       ├── Image I⁄O
│       │   ├── Multi-frame
│       │   │   ├── (AWT Only) TIFF (*.tiff)
│       │   │   └── GIF (*.gif)
│       │   └── Single-frame
│       │       ├── (Android Only) WebP (*.webp)
│       │       ├── BMP (*.bmp)
│       │       ├── JPEG (*.jpeg, *.jpg)
│       │       └── PNG (*.png)
│       ├── Multi-frame Image (List-based)
│       └── Single-frame Image
├── Application Framework
│   ├── Context (2D Drawing)
│   │   ├── Assets
│   │   ├── Clipboard
│   │   │   ├── Application
│   │   │   └── System
│   │   │       ├── Clipboard
│   │   │       └── Selection
│   │   ├── Container (Windowing)
│   │   ├── Factory (Create kits by name)
│   │   ├── I18N Text Manager
│   │   ├── Listeners (Event-driven)
│   │   │   ├── Container
│   │   │   │   └── Window Events (Resize, Move, etc.)
│   │   │   ├── Context
│   │   │   │   ├── Drawing Events
│   │   │   │   └── Widget Events (Resize, Move, etc.)
│   │   │   └── Input
│   │   │       ├── Keyboard
│   │   │       ├── Pointer
│   │   │       │   ├── Mouse
│   │   │       │   └── Touch Screen
│   │   │       └── (WIP) Gamepad
│   │   ├── Logger
│   │   ├── Preferences (File-based)
│   │   └── (WIP) Uri
│   └── Platform (Related-infos)
├── Audio Framework
│   ├── Audio Reader
│   │   ├── (Android Only) 3GPP (*.3gp)
│   │   ├── (Android Only) AAC (*.aac)
│   │   ├── (AWT Only) AIFF (*.aif, *.aiff)
│   │   ├── (AWT Only) AU (*.au)
│   │   ├── (AWT Only) SND (*.snd)
│   │   ├── FLAC (*.flac)
│   │   ├── MIDI (*.midi, *.mid)
│   │   ├── MP3 (*.mp3)
│   │   ├── OGG (*.ogg)
│   │   └── WAV (*.wav)
│   ├── Music
│   ├── Sound
│   └── (WIP) Audio Recorder
├── Bundle Framework (Serialization⁄Deserialization)
│   ├── Extensive Map Bundle (Map-based, XML-like)
│   │   ├── (WIP) JSON (*.json)
│   │   └── XML (.xml)
│   ├── Map Bundle (Map-based, Properties-like)
│   │   └── Properties (*.properties, *.prop)
│   └── Sectional Map Bundle (Map-based, INI-like)
│       └── INI (*.ini)
└── Utilities
    ├── DiskLruCache
    ├── Interface
    │   ├── Callable
    │   ├── Copyable
    │   ├── Disposable
    │   ├── Paintable
    │   ├── Prepareable
    │   └── Resetable
    ├── LruCache
    └── Toolkit⁄Factory
        ├── Arrays
        ├── Asserts
        ├── Charsets
        ├── Collections
        ├── Colors
        ├── Files (BIO)
        ├── Maps
        ├── Math
        ├── Preconditions
        ├── StreamUtils
        └── TextUtils
```

## Notes
### Running with AWT/X11 backend & Java 9+
Add the following lines to the JVM args: 
```
--add-exports java.desktop/sun.awt=ALL-UNNAMED
--add-exports java.desktop/sun.awt.X11=ALL-UNNAMED
```

## Contributing
The A3WT project currently maintained by only [me](https://github.com/Tianscar).  
Since I'm not a skilled programmer, the code may be badly written...  
So! Contributions welcome! PRs welcome! If you're planning to contribute, I'm quite glad to have you!

## License
[Apache-2.0](LICENSE) (c) Tianscar

### A3WT currently uses some code from the following projects:
Apache-2.0 [Android Open Source Project (AOSP)](https://source.android.com/)  
Apache-2.0 [Apache Harmony](https://harmony.apache.org)  
Apache-2.0 [animated-gif-lib-for-java](https://github.com/rtyley/animated-gif-lib-for-java)  
[Apache-2.0](https://github.com/JakeWharton/DiskLruCache/blob/master/LICENSE.txt) [DiskLruCache](http://jakewharton.github.io/DiskLruCache)  
[MIT](https://github.com/philfrei/AudioCue-maven/blob/main/LICENSE) [AudioCue-Maven](https://github.com/philfrei/AudioCue-maven)
### A3WT currently uses the following projects as dependencies:
[MIT](https://github.com/koral--/android-gif-drawable/blob/dev/LICENSE) [android-gif-drawable](https://github.com/koral--/android-gif-drawable)  
[Apache-2.0](https://ini4j.sourceforge.net/license.html) [ini4j](https://ini4j.sourceforge.net/)  
[BSD-3-Clause](https://github.com/haraldk/TwelveMonkeys/blob/master/LICENSE.txt) [TwelveMonkeys](http://haraldk.github.io/TwelveMonkeys/)  
[Apache-2.0](https://github.com/jnr/jnr-ffi/blob/master/LICENSE) [jnr-ffi](https://github.com/jnr/jnr-ffi)  
LGPL-2.1 [MP3SPI](https://mvnrepository.com/artifact/com.googlecode.soundlibs/mp3spi/1.9.5.4)  
LGPL-2.1 [VorbisSPI](https://mvnrepository.com/artifact/com.googlecode.soundlibs/vorbisspi/1.0.3.3)  
LGPL-2.1 [jFLAC](https://jflac.sourceforge.net)
