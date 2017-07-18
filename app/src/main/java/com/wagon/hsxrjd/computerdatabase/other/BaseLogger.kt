package com.wagon.hsxrjd.computerdatabase.other

import android.util.Log
import com.wagon.hsxrjd.computerdatabase.other.Logger.ToastTag

/**
 * Created by erychkov on 7/18/17.
 */
class BaseLogger : Logger {
    override fun debug(toastTag: ToastTag, message: String) {
        Log.d(toastTag.text, message)
    }

    override fun debug(toastTag: ToastTag, message: String, throwable: Throwable) {
        Log.d(toastTag.text, message, throwable)
    }

    override fun info(toastTag: ToastTag, message: String) {
        Log.i(toastTag.text, message)
    }

    override fun info(toastTag: ToastTag, message: String, throwable: Throwable) {
        Log.i(toastTag.text, message, throwable)
    }
}