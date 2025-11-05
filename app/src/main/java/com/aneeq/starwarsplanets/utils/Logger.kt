package com.aneeq.starwarsplanets.utils

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Logger @Inject constructor() {

    fun debug(source: Any, message: String) {
        val tag = source::class.java.simpleName
        Log.d(tag, message)
    }

    fun info(source: Any, message: String) {
        val tag = source::class.java.simpleName
        Log.i(tag, message)
    }

    fun warn(source: Any, message: String) {
        val tag = source::class.java.simpleName
        Log.w(tag, message)
    }

    fun error(source: Any, message: String, throwable: Throwable? = null) {
        val tag = source::class.java.simpleName
        if (throwable != null) {
            Log.e(tag, message, throwable)
        } else {
            Log.e(tag, message)
        }
    }
}