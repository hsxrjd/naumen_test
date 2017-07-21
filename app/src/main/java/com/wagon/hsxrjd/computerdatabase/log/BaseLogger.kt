package com.wagon.hsxrjd.computerdatabase.log

import android.util.Log

/**
 * Created by erychkov on 7/18/17.
 */
class BaseLogger : Logger {

    override fun debug(toastTag: String, message: String) {
        Log.d(toastTag, message)
    }

    override fun debug(toastTag: String, message: String, throwable: Throwable) {
        Log.d(toastTag, message, throwable)
    }

    override fun info(toastTag: String, message: String) {
        Log.i(toastTag, message)
    }

    override fun info(toastTag: String, message: String, throwable: Throwable) {
        Log.i(toastTag, message, throwable)
    }
}




