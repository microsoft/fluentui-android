# Fluent UI for Android

##### The Android UI framework for building experiences for Office and Office 365.

Fluent UI for Android is a native library that provides the Office UI experience for the Android platform. It contains information about colors and typography, as well as custom controls and customizations for platform controls, all from the official Fluent design language used in Office and Office 365 products.

## Contents

- [Colors and typography](#colors-and-typography)
- [Controls](#controls)
- [Install and use Fluent UI](#install-and-use-fluent-ui)
- [Demo app](#demo-app)
- [Contributing](#contributing)
- [License](#license)
- [Changelog](#changelog)

## Colors and typography

Fluent UI for Android provides [colors](FluentUI/src/main/res/values/colors.xml) and [typography](FluentUI/src/main/res/values/styles_font.xml) based on the Fluent design language.

## Controls

Fluent UI for Android includes an expanding library of controls written in Kotlin. These controls implement the Fluent design language and bring consistency across Office app experiences.

Some of the controls available include:
- AvatarView
- Button styles
- BottomSheet
- CalendarView
- CircularProgress styles
- DateTimePickerDialog
- Drawer
- ListItemView
- PeoplePickerView
- PersonaChipView
- PersonaListView
- PersonaView
- Snackbar
- TemplateView
- Tooltip

A full list of currently supported controls can be found here: [FluentUI](FluentUI/src/main/java/com/microsoft/fluentui).

## Install and use Fluent UI

### Requirements

API 21+

### 1. Using Gradle

- Our library is published through JCenter, so make sure the `jcenter()` repository has been added to your project level build.gradle file (which usually is automatic).

- Inside the dependency block in your build.gradle, add this line for the FluentUI library:
```gradle
dependencies {
    ...
    implementation 'com.microsoft.fluentui:FluentUIAndroid:$version'
    ... 
}
```

- Make sure you replace `$version` with the latest version of FluentUI.

### 2. Using Maven

- Add the FluentUI library as a dependency:
```xml
<dependency>
  <groupId>com.microsoft.fluentui</groupId>
  <artifactId>FluentUIAndroid</artifactId>
  <version>${version}</version>
</dependency>
```

- Make sure you replace `${version}` with the latest version of FluentUI.

### 3. Manual installation

- Download the latest changes from the [Fluent UI Android](https://github.com/microsoft/fluentui-android) repository.

- Follow [these instructions](https://developer.android.com/studio/projects/android-library) to build and output an AAR file from the FluentUI module, import the module to your project, and add it as a dependency. If you're having trouble generating an AAR file for the module, make sure you select it and run "Make Module 'FluentUI'" from the Build menu.

- Some components have dependencies you will need to manually add to your app if you are using this library as an AAR artifact because these dependencies do not get included in the output.
  - If using **PeoplePickerView**, include this dependency in your gradle file:  
    ```gradle
    implementation 'com.splitwise:tokenautocomplete:2.0.8'
    ```
  - If using **CalendarView** or **DateTimePickerDialog**, include this dependency in your gradle file:
    ```gradle
    implementation 'com.jakewharton.threetenabp:threetenabp:1.1.0'
    ```
  - Double check that these library versions correspond to the latest versions we implement in the FluentUI [build.gradle](FluentUI/build.gradle).

### Import and use the library

In code:
```kotlin
import com.microsoft.fluentui.persona.AvatarView
```

In XML:
```xml
<com.microsoft.fluentui.persona.AvatarView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:name="Mona Kane" />
```

## Demo app

Included in this repository is a demo of currently implemented controls. A full list of implemented controls available in the demo can be found here:  [Demos](FluentUI.Demo/src/main/java/com/microsoft/fluentuidemo/demos).

To see samples of all of our implemented controls and design language, run the [FluentUI.Demo](FluentUI.Demo) module in Android Studio.

## Contributing

Post bug reports, feature requests, and questions in [Issues](https://github.com/microsoft/fluentui-android/issues).

## Changelog

We use [GitHub Releases](https://github.com/blog/1547-release-your-software) to manage our releases, including the changelog between every release. You'll find a complete list of additions, fixes, and changes on the [Releases page](https://github.com/microsoft/fluentui-android/releases).

## License

All files on the Fluent UI for Android GitHub repository are subject to the MIT license. Please read the [LICENSE](LICENSE) file at the root of the project.

Usage of the logos and icons referenced in Fluent UI for Android is subject to the terms of the [assets license agreement](https://aka.ms/fabric-assets-license).
