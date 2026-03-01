package com.github.brokenithm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class BrokenithmApplication : Application() {

    abstract class BasePreference<T>(context: Context, fileName: String) {
        protected val config: SharedPreferences = context.getSharedPreferences(fileName, MODE_PRIVATE)
        abstract fun value(): T
        abstract fun update(value: T)
    }

    abstract class Settings<T>(context: Context) : BasePreference<T>(context, settings_preference)

    open class StringPreference(
        context: Context,
        private val key: String,
        private val defValue: String
    ) : Settings<String>(context) {
        override fun value() = config.getString(key, defValue) ?: defValue
        override fun update(value: String) = config.edit().putString(key, value).apply()
    }

    open class BooleanPreference(
        context: Context,
        private val key: String,
        private val defValue: Boolean
    ) : Settings<Boolean>(context) {
        override fun value() = config.getBoolean(key, defValue)
        override fun update(value: Boolean) = config.edit().putBoolean(key, value).apply()
    }

    open class IntegerPreference(
        context: Context,
        private val key: String,
        private val defValue: Int
    ) : Settings<Int>(context) {
        override fun value() = config.getInt(key, defValue)
        override fun update(value: Int) = config.edit().putInt(key, value).apply()
    }

    open class FloatPreference(
        context: Context,
        private val key: String,
        private val defValue: Float
    ) : Settings<Float>(context) {
        override fun value() = config.getString(key, defValue.toString())?.toFloat() ?: defValue
        override fun update(value: Float) = config.edit().putString(key, value.toString()).apply()
    }

    lateinit var lastServer: StringPreference
    lateinit var showDelay: BooleanPreference
    lateinit var enableVibrate: BooleanPreference
    lateinit var tcpMode: BooleanPreference
    lateinit var enableNFC: BooleanPreference
    lateinit var enableTouchSize: BooleanPreference
    lateinit var fatTouchThreshold: FloatPreference
    lateinit var extraFatTouchThreshold: FloatPreference
    lateinit var wideTouchRange: BooleanPreference

    lateinit var fullSliderSensors: BooleanPreference

    override fun onCreate() {
        super.onCreate()
        lastServer = StringPreference(this, "server", "")
        showDelay = BooleanPreference(this, "show_delay", false)
        enableVibrate = BooleanPreference(this, "enable_vibrate", true)
        tcpMode = BooleanPreference(this, "tcp_mode", false)
        enableNFC = BooleanPreference(this, "enable_nfc", true)
        enableTouchSize = BooleanPreference(this, "enable_touch_size", false)
        fatTouchThreshold = FloatPreference(this, "fat_touch_threshold", 0.027f)
        extraFatTouchThreshold = FloatPreference(this, "extra_fat_touch_threshold", 0.035f)
        wideTouchRange = BooleanPreference(this, "wide_touch_range", false)
        fullSliderSensors = BooleanPreference(this, "full_slider_sensors", true)
    }

    companion object {
        private const val settings_preference = "settings"
    }
}