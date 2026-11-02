<p align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" alt="Zakhrafa icon" width="112">
</p>

<h1 align="center">Zakhrafa · زخرفة</h1>

<p align="center">
  An Arabic-first, privacy-respecting Android keyboard, based on HeliBoard.<br>
  لوحة مفاتيح أندرويد عربية أولاً، تحترم خصوصيتك، مبنية على HeliBoard.
</p>

<p align="center">
  <a href="https://github.com/adel5000/zakhrafa/releases/latest"><img src="https://img.shields.io/github/v/release/adel5000/zakhrafa?include_prereleases&label=release" alt="Latest release"></a>
  <a href="LICENSE"><img src="https://img.shields.io/badge/license-GPL--3.0-blue" alt="License: GPL-3.0"></a>
  <img src="https://img.shields.io/badge/Android-5.0%2B-3DDC84?logo=android&logoColor=white" alt="Android 5.0+">
  <img src="https://img.shields.io/badge/internet%20permission-none-success" alt="No internet permission">
</p>

<p align="center">
  <a href="#english">English</a> · <a href="#العربية">العربية</a>
</p>

---

## English

Zakhrafa is an open-source keyboard designed around Arabic typing first. It has a refined Arabic layout, the Cairo typeface for clear Arabic key labels, and a set of themes designed for this app. It also brings over everything that makes HeliBoard a strong keyboard: offline suggestions, multilingual typing, clipboard history and deep customization.

The app requests **no internet permission**. Nothing you type can leave your device.

### What Zakhrafa adds

- **Refined Arabic layout:** `ى`, `آ` and `ئ` are on the main keys, and the bottom rows are reordered for more natural typing.
- **Cairo typeface built in:** a variable-weight Arabic font that keeps key labels crisp at every size. You can switch to the system font, or load your own font file.
- **Six new themes:** Zakhrafa, Inkly and Nuqta, each in a light and a dark variant. They follow your system's day/night setting and use key borders by default.
- **Clipboard key** on the bottom row, next to the emoji key. Each key can be turned on or off separately.
- **Swipe the spacebar to switch languages.** When more than one language is enabled, arrows (`◄ Language ►`) on the spacebar show that it's swipeable.
- **Size & layout settings:** one-tap height presets (Compact, Normal, Tall) and separate horizontal and vertical key spacing.
- **Redesigned text-editing pad:** arrow keys, jump to the start/end of the line or the text, select all, copy/cut, paste, delete, undo and redo. The pad is pinned to the toolbar.

### Features inherited from HeliBoard

- Suggestions and spell check, using dictionaries you can add ([dictionary repository](https://codeberg.org/Helium314/aosp-dictionaries))
- Multilingual typing
- Clipboard history
- Emoji search (requires the emoji dictionary)
- One-handed mode, split keyboard and number pad
- Custom layouts ([layout format](layouts.md)), and custom colors with background images and Android 12+ dynamic colors
- Glide typing, using a gesture library you supply yourself (it isn't open source, so it isn't bundled)
- Backup and restore of settings and learned words

### Install

1. Download the APK from the [latest release](https://github.com/adel5000/zakhrafa/releases/latest) and install it.
2. Open **Zakhrafa** and follow the setup wizard, or enable the keyboard yourself under *Settings → System → Languages & input → On-screen keyboard*.

> [!NOTE]
> Current releases are **debug preview builds** (package `com.adel.zakhrafa.debug`). They can be installed next to HeliBoard or any other keyboard. When signed stable releases arrive, you will have to uninstall the preview before installing them.

### Build from source

Requirements: JDK 17 or newer, Android SDK platform 37 and NDK `28.0.13004108`.

```bash
git clone https://github.com/adel5000/zakhrafa.git
cd zakhrafa
./gradlew assembleDebug           # APK in app/build/outputs/apk/debug/
./gradlew testDebugUnitTest       # unit tests
```

On Windows, use `gradlew.bat`. For faster builds while developing, use `assembleDebugNoMinify`.

### Reporting issues

Please report bugs and feature requests in this repository's [Issues](https://github.com/adel5000/zakhrafa/issues), not to the HeliBoard project. Zakhrafa is an independent fork and is not affiliated with HeliBoard or its maintainers.

### License and credits

Zakhrafa is free software under the [GNU General Public License v3.0](LICENSE). It is built on:

- [HeliBoard](https://github.com/HeliBorg/HeliBoard) and its contributors
- [OpenBoard](https://github.com/openboard-team/openboard)
- [AOSP LatinIME](https://android.googlesource.com/platform/packages/inputmethods/LatinIME/) (Apache 2.0, see [LICENSE-Apache-2.0](LICENSE-Apache-2.0))

The Cairo typeface is used under the [SIL Open Font License 1.1](app/src/main/assets/fonts/Cairo-OFL.txt).

---

<div dir="rtl">

## العربية

زخرفة لوحة مفاتيح مفتوحة المصدر، صُمّمت للكتابة بالعربية أولاً. تأتي بتخطيط عربي مُحسَّن، وخط «القاهرة» (Cairo) لعرض الحروف العربية بوضوح على المفاتيح، ومجموعة سمات صُمّمت خصيصاً لها. وتحتفظ بكل ما يجعل HeliBoard لوحة مفاتيح قوية: اقتراحات تعمل دون اتصال، وكتابة بعدة لغات، وسجل للحافظة، وتخصيص واسع.

التطبيق **لا يطلب إذن الوصول إلى الإنترنت**، فلا يمكن لأي شيء تكتبه أن يغادر جهازك.

### ما تضيفه زخرفة

- **تخطيط عربي مُحسَّن:** الحروف «ى» و«آ» و«ئ» على المفاتيح الرئيسية، مع إعادة ترتيب الصفوف السفلية لكتابة أكثر سلاسة.
- **خط «القاهرة» مدمج:** خط عربي متغيّر السماكة يُبقي حروف المفاتيح واضحة بكل الأحجام. ويمكنك التبديل إلى خط النظام أو تحميل ملف خط خاص بك.
- **ست سمات جديدة:** زخرفة وInkly ونقطة، ولكل منها نسخة فاتحة وأخرى داكنة. تتبع وضع النهار/الليل في النظام، وتُظهر حدود المفاتيح افتراضياً.
- **مفتاح للحافظة** في الصف السفلي بجانب مفتاح الرموز التعبيرية، ويمكن إظهار كل منهما أو إخفاؤه على حدة.
- **اسحب على مفتاح المسافة للتبديل بين اللغات.** عند تفعيل أكثر من لغة، يظهر سهمان على مفتاح المسافة (`◄ اللغة ►`) للدلالة على إمكانية السحب.
- **إعدادات الحجم والتخطيط:** خيارات جاهزة لارتفاع اللوحة (مُدمج، عادي، طويل) بلمسة واحدة، وتحكم منفصل في المسافات الأفقية والعمودية بين المفاتيح.
- **لوحة تحرير نص مُعاد تصميمها:** مفاتيح الأسهم، والانتقال إلى بداية السطر أو النص ونهايته، وتحديد الكل، ونسخ/قص، ولصق، وحذف، والتراجع والإعادة. واللوحة مثبّتة في شريط الأدوات.

### ميزات موروثة من HeliBoard

- الاقتراحات والتدقيق الإملائي عبر قواميس يمكنك إضافتها ([مستودع القواميس](https://codeberg.org/Helium314/aosp-dictionaries))
- الكتابة بعدة لغات
- سجل الحافظة
- البحث في الرموز التعبيرية (يتطلب قاموس الرموز التعبيرية)
- وضع اليد الواحدة، ولوحة مقسومة، ولوحة أرقام
- تخطيطات مخصصة ([صيغة التخطيطات](layouts.md))، وألوان مخصصة مع صورة خلفية والألوان الديناميكية في أندرويد 12 فأحدث
- الكتابة بالإيماءات (السحب)، باستخدام مكتبة إيماءات تضيفها بنفسك (المكتبة ليست مفتوحة المصدر لذا لا تأتي مع التطبيق)
- النسخ الاحتياطي واستعادة الإعدادات والكلمات المُتعلَّمة

### التثبيت

1. نزّل ملف APK من [أحدث إصدار](https://github.com/adel5000/zakhrafa/releases/latest) وثبّته.
2. افتح تطبيق **زخرفة** واتّبع معالج الإعداد، أو فعّل اللوحة يدوياً من *الإعدادات ← النظام ← اللغات والإدخال ← لوحة المفاتيح على الشاشة*.

> [!NOTE]
> الإصدارات الحالية **نسخ تجريبية للمعاينة** (الحزمة `com.adel.zakhrafa.debug`)، ويمكن تثبيتها بجانب HeliBoard أو أي لوحة مفاتيح أخرى. وعند صدور الإصدارات المستقرة الموقّعة، ستحتاج إلى إزالة النسخة التجريبية قبل تثبيتها.

### البناء من المصدر

المتطلبات: JDK 17 أو أحدث، ومنصة Android SDK رقم 37، وNDK بالإصدار `28.0.13004108`.

```bash
git clone https://github.com/adel5000/zakhrafa.git
cd zakhrafa
./gradlew assembleDebug           # APK in app/build/outputs/apk/debug/
./gradlew testDebugUnitTest       # unit tests
```

على ويندوز استخدم `gradlew.bat`. ولبناء أسرع أثناء التطوير استخدم `assembleDebugNoMinify`.

### الإبلاغ عن المشكلات

يُرجى الإبلاغ عن الأخطاء واقتراح الميزات في قسم [Issues](https://github.com/adel5000/zakhrafa/issues) في هذا المستودع، لا لدى مشروع HeliBoard. زخرفة نسخة مستقلة، وليست تابعة لمشروع HeliBoard أو القائمين عليه.

### الترخيص والشكر

زخرفة برنامج حر مرخّص بموجب [رخصة جنو العمومية العامة، الإصدار 3.0](LICENSE)، ومبني على:

- [HeliBoard](https://github.com/HeliBorg/HeliBoard) والمساهمين فيه
- [OpenBoard](https://github.com/openboard-team/openboard)
- [AOSP LatinIME](https://android.googlesource.com/platform/packages/inputmethods/LatinIME/) (رخصة Apache 2.0، انظر [LICENSE-Apache-2.0](LICENSE-Apache-2.0))

يُستخدم خط «القاهرة» (Cairo) بموجب [رخصة SIL للخطوط المفتوحة 1.1](app/src/main/assets/fonts/Cairo-OFL.txt).

</div>
