# Fluent UI for Android

##### The Android UI framework for building experiences for Microsoft 365.

Fluent UI for Android is a native library that provides the Office UI experience for the Android platform. It contains information about colors and typography, as well as custom controls and customizations for platform controls, all from the official Fluent design language used in Microsoft 365 products.


### Build status (master branch)

| Build Service   | Status                                                                                                                                                                                                                                                           |
| --------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| App Center      | [![Build status](https://build.appcenter.ms/v0.1/apps/7acc51be-c1e6-4351-8fa4-c4536fd42dd8/branches/master/badge)](https://appcenter.ms)                                                                                                                         |
| Build Pipeline | [![Android CI](https://github.com/microsoft/fluentui-android/actions/workflows/android-build-check.yml/badge.svg?branch=master)](https://github.com/microsoft/fluentui-android/actions/workflows/android-build-check.yml)                                                                                                                        |
| Release Pipeline | ![Build Status](https://dev.azure.com/microsoftdesign/fluentui-native/_apis/build/status/fluentui-android-CI-maven-publish?branchName=master)        |

## Contents

- [Colors and typography](#colors-and-typography)
- [Controls](#controls)
- [Modularization](#modularization)
- [Install and use Fluent UI](#install-and-use-fluent-ui)
- [Demo app](#demo-app)
- [Contributing](#contributing)
- [License](#license)
- [Changelog](#changelog)

## Colors and typography

Fluent UI for Android provides colors and typography based on the Fluent design language.

In version 0.0.12, as a part of modularization, the colors and typographies have been moved to respective modules
Please refer to [Modularization](#modularization) section for more details

## Controls

Fluent UI for Android includes an expanding library of controls written in Kotlin. These controls implement the Fluent design language and bring consistency across Office app experiences.


## XML based Controls (v1)

- [Action Bar](fluentui_others/src/main/java/com/microsoft/fluentui)
- [App Bar](fluentui_others/src/main/java/com/microsoft/fluentui)
- [AvatarView](fluentui_others/src/main/java/com/microsoft/fluentui)
- [AvatarGroupView](fluentui_others/src/main/java/com/microsoft/fluentui)
- [BottomSheet](fluentui_drawer/src/main/java/com/microsoft/fluentui)
- [Bottom Navigation](fluentui_others/src/main/java/com/microsoft/fluentui)
- [Button styles](fluentui_others/src/main/java/com/microsoft/fluentui)
- [CalendarView](fluentui_others/src/main/java/com/microsoft/fluentui)
- [CircularProgress styles](fluentui_others/src/main/java/com/microsoft/fluentui)
- [Contextual Command Bar](fluentui_others/src/main/java/com/microsoft/fluentui)
- [DateTimePickerDialog](fluentui_others/src/main/java/com/microsoft/fluentui)
- [Drawer](fluentui_drawer/src/main/java/com/microsoft/fluentui)
- [ListItemView](fluentui_listitem/src/main/java/com/microsoft/fluentui)
- [PeoplePickerView](fluentui_others/src/main/java/com/microsoft/fluentui)
- [PersonaChipView](fluentui_others/src/main/java/com/microsoft/fluentui)
- [PersonaListView](fluentui_others/src/main/java/com/microsoft/fluentui)
- [PersonaView](fluentui_others/src/main/java/com/microsoft/fluentui)
- [Popup Menu](fluentui_others/src/main/java/com/microsoft/fluentui)
- [Snackbar](fluentui_others/src/main/java/com/microsoft/fluentui)
- [Tablayout](fluentui_tablayout/src/main/java/com/microsoft/fluentui)
- [TemplateView](fluentui_core/src/main/java/com/microsoft/fluentui)
- [Tooltip](fluentui_others/src/main/java/com/microsoft/fluentui)

## Compose based Controls (v2)
- [AnnouncementCard](fluentui_controls/src/main/java/com/microsoft/fluentui/tokenized/controls/AnnouncementCard.kt)
- [AppBar](fluentui_topappbars/src/main/java/com/microsoft/fluentui/tokenized/AppBar.kt)
- [Avatar](fluentui_persona/src/main/java/com/microsoft/fluentui/tokenized/persona/Avatar.kt)
- [AvatarCarousel](fluentui_persona/src/main/java/com/microsoft/fluentui/tokenized/persona/AvatarCarousel.kt)
- [AvatarGroup](fluentui_persona/src/main/java/com/microsoft/fluentui/tokenized/persona/AvatarGroup.kt)
- [Badge](fluentui_notification/src/main/java/com/microsoft/fluentui/tokenized/notification/Badge.kt)
- [BasicCard](fluentui_controls/src/main/java/com/microsoft/fluentui/tokenized/controls/BasicCard.kt)
- [BasicChip](fluentui_controls/src/main/java/com/microsoft/fluentui/tokenized/controls/BasicChip.kt)
- [BottomSheet](fluentui_drawer/src/main/java/com/microsoft/fluentui/tokenized/bottomsheet/BottomSheet.kt)
- [Button](fluentui_controls/src/main/java/com/microsoft/fluentui/tokenized/controls/Button.kt)
- [CardNudge](fluentui_notification/src/main/java/com/microsoft/fluentui/tokenized/notification/CardNudge.kt)
- [Checkbox](fluentui_controls/src/main/java/com/microsoft/fluentui/tokenized/controls/Checkbox.kt)
- [CircularProgressIndicator](fluentui_progress/src/main/java/com/microsoft/fluentui/tokenized/progress/CircularProgressIndicator.kt)
- [Citation](fluentui_controls/src/main/java/com/microsoft/fluentui/tokenized/controls/Citation.kt)
- [ContextualCommandBar](fluentui_ccb/src/main/java/com/microsoft/fluentui/tokenized/contextualcommandbar/ContextualCommandBar.kt)
- [Dialog](fluentui_menus/src/main/java/com/microsoft/fluentui/tokenized/menu/Dialog.kt)
- [Divider](fluentui_listitem/src/main/java/com/microsoft/fluentui/tokenized/divider/Divider.kt)
- [Drawer](fluentui_drawer/src/main/java/com/microsoft/fluentui/tokenized/drawer/Drawer.kt)
- [FileCard](fluentui_controls/src/main/java/com/microsoft/fluentui/tokenized/controls/FileCard.kt)
- [FloatingActionButton](fluentui_controls/src/main/java/com/microsoft/fluentui/tokenized/controls/FloatingActionButton.kt)
- [Label](fluentui_controls/src/main/java/com/microsoft/fluentui/tokenized/controls/Label.kt)
- [LinearProgressIndicator](fluentui_progress/src/main/java/com/microsoft/fluentui/tokenized/progress/LinearProgressIndicator.kt)
- [ListContentBuilder](fluentui_listitem/src/main/java/com/microsoft/fluentui/tokenized/contentBuilder/ListContentBuilder.kt)
- [ListItem](fluentui_listitem/src/main/java/com/microsoft/fluentui/tokenized/listitem/ListItem.kt)
- [Menu](fluentui_menus/src/main/java/com/microsoft/fluentui/tokenized/menu/Menu.kt)
- [NotificationCommon](fluentui_notification/src/main/java/com/microsoft/fluentui/tokenized/notification/NotificationCommon.kt)
- [PeoplePicker](fluentui_peoplepicker/src/main/java/com/microsoft/fluentui/tokenized/peoplepicker/PeoplePicker.kt)
- [Persona](fluentui_persona/src/main/java/com/microsoft/fluentui/tokenized/persona/Persona.kt)
- [PersonaChip](fluentui_persona/src/main/java/com/microsoft/fluentui/tokenized/persona/PersonaChip.kt)
- [PersonaList](fluentui_persona/src/main/java/com/microsoft/fluentui/tokenized/persona/PersonaList.kt)
- [Pill](fluentui_tablayout/src/main/java/com/microsoft/fluentui/tokenized/segmentedcontrols/Pill.kt)
- [PillSwitch](fluentui_tablayout/src/main/java/com/microsoft/fluentui/tokenized/segmentedcontrols/PillSwitch.kt)
- [PillTabs](fluentui_tablayout/src/main/java/com/microsoft/fluentui/tokenized/segmentedcontrols/PillTabs.kt)
- [ProgressText](fluentui_progress/src/main/java/com/microsoft/fluentui/tokenized/progress/ProgressText.kt)
- [RadioButton](fluentui_controls/src/main/java/com/microsoft/fluentui/tokenized/controls/RadioButton.kt)
- [SearchBar](fluentui_topappbars/src/main/java/com/microsoft/fluentui/tokenized/SearchBar.kt)
- [SearchBarPersonaChip](fluentui_persona/src/main/java/com/microsoft/fluentui/tokenized/persona/SearchBarPersonaChip.kt)
- [Shimmer](fluentui_progress/src/main/java/com/microsoft/fluentui/tokenized/shimmer/Shimmer.kt)
- [SideRail](fluentui_tablayout/src/main/java/com/microsoft/fluentui/tokenized/navigation/SideRail.kt)
- [Snackbar](fluentui_notification/src/main/java/com/microsoft/fluentui/tokenized/notification/Snackbar.kt)
- [TabBar](fluentui_tablayout/src/main/java/com/microsoft/fluentui/tokenized/navigation/TabBar.kt)
- [TabItem](fluentui_listitem/src/main/java/com/microsoft/fluentui/tokenized/tabItem/TabItem.kt)
- [TextField](fluentui_controls/src/main/java/com/microsoft/fluentui/tokenized/controls/TextField.kt)
- [ToggleSwitch](fluentui_controls/src/main/java/com/microsoft/fluentui/tokenized/controls/ToggleSwitch.kt)
- [ToolTip](fluentui_notification/src/main/java/com/microsoft/fluentui/tokenized/notification/ToolTip.kt)





## Install and use Fluent UI

### Requirements

API 21+

### 1. Using Gradle

- Our library is now published using MavenCentral, so make sure the `mavenCentral()` repository has been added to your project level build.gradle file if you want to consume versions `>= 0.0.17`.

- If you are still using older versions you can consume `jcenter()` too. Please note it will only work for versions `<=0.0.16`

- Inside the dependency block in your build.gradle, add this line for the FluentUI library:
```gradle
dependencies {
    ...
    implementation 'com.microsoft.fluentui:FluentUIAndroid:$version'
    ...
}
```
- Make sure you replace `$version` with the latest version of FluentUI.
- From version 0.0.12, individual modules can also be used by applications as per need. E.g
  To use just BottomSheet control which is part of fluentui_drawer module, following block can be added to build.gradle

 ```gradle
 dependencies {
     ...
     implementation 'com.microsoft.fluentui:fluentui_drawer:$version'
     ...
 }
 ```
 Replace `${version}` with the latest version of fluentui_drawer
 
 More information about contents of each module can be found in [Modularization](#modularization) section

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
- As in case of Gradle, here too, to use specific modules add dependency as follows:
```xml
<dependency>
  <groupId>com.microsoft.fluentui</groupId>
  <artifactId>fluentui_drawer</artifactId>
  <version>${version}</version>
</dependency>
```

 Replace `${version}` with the latest version of fluentui_drawer

 Check [Modularization](#modularization) for details of every module.

### 3. Manual installation

###### Modularized FluentUI manual installation involves building individual AARs of required modules  ######

- Download the latest changes from the [Fluent UI Android](https://github.com/microsoft/fluentui-android) repository.

- Follow [these instructions](https://developer.android.com/studio/projects/android-library) to build and output an AAR files from the FluentUI modules, import the module(s) to your project, and add it as a dependency. If you're having trouble generating an AAR file for the module, make sure you select it and run e.g "Make Module 'fluentui_drawer'" from the Build menu.

- Some components have dependencies you will need to manually add to your app if you are using this library as an AAR artifact because these dependencies do not get included in the output.
  - None at the moment
  - Double check that these library versions correspond to the latest versions we implement in the FluentUI [build.gradle](fluentui_others\build.gradle).

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

## Modularization
Starting from version 0.0.12, Fluent UI has been split into multiple modules by grouping related controls together. The main objective of this is to reduce the size impact of library on the applications which want to use only specific controls from Fluent UI

The list of modules and their respective controls is as follows

| Modules                  | v1 Controls                                                       | v2 Controls                                                                                                     | 
|:-------------------------|:------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------|
|fluentui_calendar         | Calendar, DateTime Picker                                         |                                                                                                                 |
| fluentui_ccb             |                                                                   | ContextualCommandBar                                                                                            |
| fluentui_controls        |                                                                   | AnnouncementCard, BasicCard,<br> BasicChip, Button,<br> Checkbox, Citation,<br> FileCard, FloatingActionButton,<br> Label, RadioButton,<br> TextField, ToggleSwitch |
| fluentui_core            | TemplateView, Utilities                                           |                                                                                                                 |
| fluentui_drawer          | Drawer, BottomSheet,<br> Persistent BottomSheet                    | BottomSheet, Drawer                                                                                             |
| fluentui_listitem        | ListItemView                                                      | ListContentBuilder, Divider,<br> ListItem, TabItem                                                              |
| fluentui_menus           | Pop up Menu                                                       | Dialog, Menu                                                                                                    |
| fluentui_notification    |                                                                   | Badge, CardNudge,<br> NotificationCommon, Snackbar,<br> ToolTip                                                 |
| fluentui_others          | Action Bar Layout, Bottom Navigation, Buttons, View Pager         |                                                                                                                 |
| fluentui_persona         | AvatarView, AvatarGroup View,<br>PersonaChipView, PersonaListView | Avatar, AvatarCarousel,<br> AvatarGroup, Persona,<br> PersonaChip, PersonaList,<br> SearchBarPersonaChip        |
| fluentui_peoplepicker    | PeoplePickerView                                                  | PeoplePicker                                                                                                    |
| fluentui_progress        | Linear Progress Bar, Circular Progress Bar                        | CircularProgressIndicator, LinearProgressIndicator,<br> ProgressText, Shimmer                                   |
| fluentui_tablayout       | Tablayout                                                         | SideRail, TabBar,<br> Pill, PillSwitch,<br> PillTabs                                                            |
| fluentui_topappbars      | Toolbar, AppBar Layout, SearchBar                                 | AppBar, SearchBar                                                                                               |
|fluentui_transients       | Snackbar, Tooltip                                                 |                                                                                                                 |



## Contributing

Post bug reports, feature requests, and questions in [Issues](https://github.com/microsoft/fluentui-android/issues).
Contributions to any specific controls should be done in respective modules. Refer [Modularization](#modularization) section for more details

## Changelog

We use [GitHub Releases](https://github.com/blog/1547-release-your-software) to manage our releases, including the changelog between every release. You'll find a complete list of additions, fixes, and changes on the [Releases page](https://github.com/microsoft/fluentui-android/releases).

## License

All files on the Fluent UI for Android GitHub repository are subject to the MIT license. Please read the [LICENSE](LICENSE) file at the root of the project.

Usage of the logos and icons referenced in Fluent UI for Android is subject to the terms of the [assets license agreement](https://aka.ms/fabric-assets-license).
