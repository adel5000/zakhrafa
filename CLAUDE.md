# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this repo is

A private fork of [HeliBoard](https://github.com/HeliBorg/HeliBoard) (GPL-3.0, descended from AOSP LatinIME / OpenBoard), rebranded as **Zakhrafa** (`applicationId = "com.adel.zakhrafa"`, output APKs named `Zakhrafa_<version>-<buildType>.apk`). The fork targets Arabic users. The Java/Kotlin package and Android `namespace` are still `helium314.keyboard.*`. Keep them that way, because renaming would touch thousands of files.

It is a shallow clone of upstream commit `44ddb1a`. All fork changes are **uncommitted** working-tree edits on top of it, so `git diff` / `git status` show the full set of customizations. The parent directory (`..`) also holds `Decoration Text Keyboard 3.0.4.apk+`, a third-party APK that was audited as a reference. It is not part of the build.

## Build and test (Windows)

The `java` on PATH is Java 8 and will not build this project. Point JAVA_HOME at Android Studio's bundled JBR first (the JAVA_HOME env var, Adoptium 21, also works):

```powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
.\gradlew.bat assembleDebug          # minified debug APK -> app/build/outputs/apk/debug/
.\gradlew.bat assembleDebugNoMinify  # faster, unminified, for iteration
.\gradlew.bat testDebugUnitTest      # unit tests
.\gradlew.bat testDebugUnitTest --tests "helium314.keyboard.latin.InputLogicTest"   # single test class
.\gradlew.bat lint
```

- The build needs compileSdk 37 and NDK `28.0.13004108`, and both are installed. `local.properties` (git-ignored) sets `sdk.dir`. A cold build takes about 11 minutes.
- Build types: `debug` (minified, `.debug` suffix, drops `main_ro.dict`), `debugNoMinify`, `release`, `nouserlib` (release without the user-supplied gesture library), and `runTests`. Tests check `BuildConfig.BUILD_TYPE == "runTests"` to skip known failures, but with AGP 9 only the default test build type (`debug`) gets a unit-test task: `testRunTestsUnitTest` (used by upstream's `build-test-auto.yml`) does not exist, so those skips never apply locally.
- Unit tests are JVM tests using Robolectric and Mockito, in `app/src/test`. There are no instrumented tests.
- `lint { abortOnError = true }`, so lint errors fail release builds.
- Native code (dictionary lookup and suggestion engine) is in `app/src/main/jni` and is built with ndk-build through `Android.mk`.

## Architecture

The upstream code is large, mostly AOSP-era, and uneven in style. Key paths (from CONTRIBUTING.md):

- **Input chain:** `PointerTracker` (touch/swipe) → `KeyboardActionListenerImpl` → `LatinIME` (the `InputMethodService`, which receives editor events) → `InputLogic` (keycode/text handling) → `RichInputConnection` (reads and writes the text field).
- **Suggestions:** `DictionaryFacilitatorImpl` → `Suggest` → `InputLogic` → `SuggestionStripView`, with the native library underneath.
- **Be careful in `InputLogic`, `Suggest`, and `RichInputConnection`.** Changes there easily cause side effects that depend on the app and OS version, and those side effects are hard to test.
- **Layouts:** text/JSON files under `app/src/main/assets/layouts/<type>/` (main, symbols, functional, dpad, numpad, …), parsed by `KeyboardParser` and the Floris-derived classes in `keyboard/internal/keyboard_parser/floris/` (`TextKeyData`, `KeyData`). Per-language popup letters live in `assets/locale_key_texts`. See `layouts.md` for the format.
- **Conditional keys:** `KeyboardId` holds boolean flags taken from `SettingsValues` (`emojiKeyEnabled`, `clipboardKeyEnabled`, `dpadKeyEnabled`, …). Layout JSON selects keys from those flags with `{"$": "keyboard_state_selector", "<flag>": {...}}`, which `KeyboardStateSelector.compute()` in `KeyData.kt` evaluates in a fixed order. A new toggleable key needs a flag in both `KeyboardId` and `KeyboardStateSelector`.
- **Themes:** built-in color themes are `THEME_*` constants plus `DefaultColors(...)` branches in `KeyboardTheme.kt`, registered in `getAvailableDefaultColors`. Each theme's display name comes from the string resource `theme_name_<id>`, looked up by name (mark it `tools:keep`).
- **Fonts:** `KeyboardTypeface` resolves fonts in this order: a user-supplied font file, then the built-in font picked by `PREF_KEYBOARD_FONT` (Cairo variable font from `assets/fonts/`; Qomra was removed pending license confirmation and its files kept outside the repo in `../private-fonts/`), then the system font. Call `KeyboardTypeface.clearCache()` after a font setting changes.

### Adding a setting

A setting is spread over several files:
1. Key constant `PREF_*` in `latin/settings/Settings.java`.
2. Default value in `latin/settings/Defaults.kt`.
3. A `final` field read in the `SettingsValues.java` constructor. This is the immutable snapshot the keyboard reads at runtime.
4. UI in a Compose screen under `settings/screens/`: add the key to the screen's list, and add a `Setting(context, KEY, R.string...) { ... }` entry with a callback. Use `KeyboardSwitcher.getInstance().reloadKeyboard()` for layout changes, or `setThemeNeedsReload()` for appearance and size changes.
5. Migrations of stored prefs go in `latin/AppUpgrade.kt`.

Size settings that vary by orientation and fold state (such as `PREF_KEY_GAP_SCALE_PREFIX` and the fork's `PREF_VERTICAL_KEY_GAP_SCALE_PREFIX`) use arrays of 4 defaults, read through `createPrefKeyForBooleanSettings` and `findIndexOfDefaultSetting`.

## Fork customizations to keep in mind

- **Defaults changed from upstream:** Zakhrafa light/dark themes with key borders, emoji and clipboard keys shown, horizontal space swipe set to **switch language** instead of moving the cursor, localized number row off, and DPAD added to and pinned in the toolbar. Tests that depend on upstream defaults must set the pref explicitly. For example, `InputLogicTest` sets `PREF_SPACE_HORIZONTAL_SWIPE` to `MOVE_CURSOR`.
- **Added features:** a clipboard key, a separate vertical key-gap scale, keyboard-height presets, a built-in font picker, "◄ Language ►" arrows on the spacebar when swiping switches language, new themes (Zakhrafa, Inkly, Nuqta), and changes to the Arabic main layout, the DPAD layout, and the functional-keys layout.
- **Strings:** upstream takes translations only through Weblate. This fork edits resources directly. Add new strings to `values/strings.xml` and add Arabic versions in `values-ar/strings.xml`.

## Constraints

- Upstream policy is no INTERNET permission and no proprietary blobs. The glide-typing library is supplied by the user at runtime.
- Licensing: the code is GPL-3.0. Cairo ships with its OFL license file. Qomra is **not** in the public repo because its embedding/redistribution license is unconfirmed. Don't re-add it until the user confirms the license.
- Published at `github.com/adel5000/zakhrafa` (remote `origin`). Upstream HeliBoard is remote `upstream`.
- Upstream `AI_USAGE.md` prohibits AI-generated PRs and issues to HeliBoard. Do not open upstream PRs or issues from this fork.
