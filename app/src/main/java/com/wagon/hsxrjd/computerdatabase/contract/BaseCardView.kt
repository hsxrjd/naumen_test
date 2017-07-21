package com.wagon.hsxrjd.computerdatabase.contract

import com.wagon.hsxrjd.computerdatabase.model.net.Card

/**
 * Created by hsxrjd on 23.05.17.
 */
interface BaseCardView : BaseContract.View, Loading {
    fun showMessage(message: String)
    fun showMessage(resource: Int)
    fun startCardFragment(card: Card)
}