// SPDX-License-Identifier: GPL-3.0-only

package helium314.keyboard.keyboard

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.widget.TextView
import androidx.compose.ui.text.font.FontFamily
import helium314.keyboard.latin.common.isEmoji
import helium314.keyboard.latin.settings.Defaults
import helium314.keyboard.latin.settings.Settings
import helium314.keyboard.latin.utils.prefs

object KeyboardTypeface {
    private val lock = Any()

    private var cachedCustomTypeface: Typeface? = null
    private var cachedCustomFontFamily: FontFamily? = null
    @Volatile
    private var customTypefaceLoaded = false

    private var cachedEmojiTypeface: Typeface? = null
    @Volatile
    private var emojiTypefaceLoaded = false

    private var cachedUserTypeface: Typeface? = null
    private var cachedBuiltInBoldTypeface: Typeface? = null

    private fun loadCustomTypeface(context: Context): Typeface? {
        return runCatching {
            Typeface.createFromFile(Settings.getCustomFontFile(context))
        }.getOrNull()
    }

    // built-in keyboard font selected in settings, used whenever the user has not set a custom font file
    private fun loadBuiltInTypeface(context: Context, weight: Int): Typeface? {
        val bold = weight >= BOLD_WEIGHT
        return runCatching {
            when (context.prefs().getString(Settings.PREF_KEYBOARD_FONT, Defaults.PREF_KEYBOARD_FONT)) {
                FONT_SYSTEM -> null
                else -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Typeface.Builder(context.assets, CAIRO)
                        .setFontVariationSettings("'wght' $weight")
                        .build()
                } else {
                    val typeface = Typeface.createFromAsset(context.assets, CAIRO)
                    if (bold) Typeface.create(typeface, Typeface.BOLD) else typeface
                }
            }
        }.getOrNull()
    }

    private fun loadCustomEmojiTypeface(context: Context): Typeface? {
        return runCatching {
            Typeface.createFromFile(Settings.getCustomEmojiFontFile(context))
        }.getOrNull()
    }

    @JvmStatic
    fun customTypeface(): Typeface? {
        if (customTypefaceLoaded) return cachedCustomTypeface
        val context = Settings.getCurrentContext() ?: return null
        synchronized(lock) {
            if (!customTypefaceLoaded) {
                cachedUserTypeface = loadCustomTypeface(context)
                cachedBuiltInBoldTypeface = if (cachedUserTypeface == null) loadBuiltInTypeface(context, BOLD_WEIGHT) else null
                cachedCustomTypeface = cachedUserTypeface ?: loadBuiltInTypeface(context, REGULAR_WEIGHT)
                cachedCustomFontFamily = cachedCustomTypeface?.let(::FontFamily)
                customTypefaceLoaded = true
            }
            return cachedCustomTypeface
        }
    }

    /** The font file chosen by the user in settings, without the built-in fallback. */
    @JvmStatic
    fun userTypeface(): Typeface? {
        if (!customTypefaceLoaded) customTypeface()
        return cachedUserTypeface
    }

    @JvmStatic
    fun emojiTypeface(): Typeface? {
        if (emojiTypefaceLoaded) return cachedEmojiTypeface
        val context = Settings.getCurrentContext() ?: return null
        synchronized(lock) {
            if (!emojiTypefaceLoaded) {
                cachedEmojiTypeface = loadCustomEmojiTypeface(context)
                emojiTypefaceLoaded = true
            }
            return cachedEmojiTypeface
        }
    }

    @JvmStatic
    fun customFontFamily(): FontFamily? {
        if (!customTypefaceLoaded) customTypeface()
        return cachedCustomFontFamily
    }

    @JvmStatic
    fun resolve(
        text: CharSequence?,
        defaultTypeface: Typeface = Typeface.DEFAULT,
    ): Typeface {
        val emojiTypeface = emojiTypeface()
        return if (emojiTypeface != null && text != null && isEmoji(text)) {
            emojiTypeface
        } else {
            val typeface = customTypeface() ?: return defaultTypeface
            if (defaultTypeface.isBold) cachedBuiltInBoldTypeface ?: typeface else typeface
        }
    }

    @JvmStatic
    fun applyToTextView(textView: TextView) {
        applyToTextView(textView, textView.text, Typeface.DEFAULT)
    }

    @JvmStatic
    fun applyToTextView(textView: TextView, text: CharSequence?, defaultTypeface: Typeface) {
        textView.typeface = resolve(text, defaultTypeface = defaultTypeface)
    }

    @JvmStatic
    fun clearCache() {
        synchronized(lock) {
            cachedCustomTypeface = null
            cachedCustomFontFamily = null
            cachedUserTypeface = null
            cachedBuiltInBoldTypeface = null
            customTypefaceLoaded = false
            cachedEmojiTypeface = null
            emojiTypefaceLoaded = false
        }
    }

    const val FONT_CAIRO = "cairo"
    const val FONT_SYSTEM = "system"
    private const val CAIRO = "fonts/Cairo.ttf"
    private const val REGULAR_WEIGHT = 500
    private const val BOLD_WEIGHT = 700
}
