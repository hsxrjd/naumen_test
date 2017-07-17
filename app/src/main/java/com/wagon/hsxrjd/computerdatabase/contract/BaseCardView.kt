package com.wagon.hsxrjd.computerdatabase.contract

/**
 * Created by hsxrjd on 23.05.17.
 */
interface BaseCardView : BaseContract.View, Loading {
    fun showMessage(message: String)
    fun showMessage(resource: Int)
    fun showMessage(resource: Int, vararg varargs: Any)
}