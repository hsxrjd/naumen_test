package com.wagon.hsxrjd.computerdatabase.other

/**
 * Created by erychkov on 7/18/17.
 */
interface Logger {
    fun debug(toastTag: ToastTag, message: String)
    fun debug(toastTag: ToastTag, message: String, throwable: Throwable)

    fun info(toastTag: ToastTag, message: String)
    fun info(toastTag: ToastTag, message: String, throwable: Throwable)

    enum class ToastTag(val text: String) {
        NO_TAG("NO_TAG")
    }
}