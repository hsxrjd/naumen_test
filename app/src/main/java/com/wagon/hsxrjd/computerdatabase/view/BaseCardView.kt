package com.wagon.hsxrjd.computerdatabase.view

import com.wagon.hsxrjd.computerdatabase.BaseContract
import com.wagon.hsxrjd.computerdatabase.model.Card

/**
 * Created by hsxrjd on 23.05.17.
 */
interface BaseCardView : BaseContract.View, Loading{
    fun showMessage(message: String)
    fun showMessage(resource: Int)
    fun showMessage(resource: Int, vararg varargs: Any)
}