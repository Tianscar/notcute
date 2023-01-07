# A3WT
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
│       ├── Win32
│       │   └── Windows
│       └── X11
│           └── *nix X11
├── a3wt-qt (WIP)
│   └── Qt Jambi
│       └── Qt
│           └── *nix Wayland
└── a3wt-teavm (WIP)
    └── TeaVM
        └── HTML5
```

(children nodes depends on root node)
```
a3wt-core
├── 2D Graphics Framework
├── Audio Framework (WIP)
└── Bundle Framework
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
So! Contributing welcome! PRs welcome! I'm quite glad to have you!

## License
[Apache-2.0](https://github.com/AnsdoShip/a3wt/blob/main/LICENSE) (c) [A3WT](https://github.com/AnsdoShip/a3wt)

### A3WT currently uses code from the following projects:
Apache-2.0 (c) [Android Open Source Project (AOSP)](https://source.android.com/)  
Apache-2.0 (c) [Apache Harmony](https://harmony.apache.org)  
[MIT](https://github.com/koral--/android-gif-drawable/blob/dev/LICENSE) (c) [android-gif-drawable](https://github.com/koral--/android-gif-drawable)  
[Apache-2.0](https://ini4j.sourceforge.net/license.html) (c) [ini4j](https://ini4j.sourceforge.net/)  
Apache-2.0 (c) [animated-gif-lib-for-java](https://github.com/rtyley/animated-gif-lib-for-java)  
[Apache-2.0](https://github.com/JakeWharton/DiskLruCache/blob/master/LICENSE.txt) (c) [DiskLruCache](http://jakewharton.github.io/DiskLruCache)  
[BSD-3-Clause](https://github.com/haraldk/TwelveMonkeys/blob/master/LICENSE.txt) (c) [TwelveMonkeys](http://haraldk.github.io/TwelveMonkeys/)  
[Apache-2.0](https://github.com/jnr/jnr-ffi/blob/master/LICENSE) (c) [jnr-ffi](https://github.com/jnr/jnr-ffi)