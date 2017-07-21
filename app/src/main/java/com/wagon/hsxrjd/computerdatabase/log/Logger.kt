package com.wagon.hsxrjd.computerdatabase.log

/**
 * Created by erychkov on 7/18/17.
 */
interface Logger {
    fun debug(toastTag: String, message: String)
    fun debug(toastTag: String, message: String, throwable: Throwable)

    fun info(toastTag: String, message: String)
    fun info(toastTag: String, message: String, throwable: Throwable)

    companion object {
        val logger: Logger = BaseLogger()
    }
}