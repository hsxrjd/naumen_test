package com.wagon.hsxrjd.computerdatabase.adapter

import com.wagon.hsxrjd.computerdatabase.model.net.Card

/**
 * Created by erychkov on 7/21/17.
 */

interface ClickableItem {
    fun onItemClick(card: Card)
}