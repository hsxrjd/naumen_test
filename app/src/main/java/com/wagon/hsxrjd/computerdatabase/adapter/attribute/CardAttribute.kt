package com.wagon.hsxrjd.computerdatabase.adapter.attribute

import android.view.View
import com.wagon.hsxrjd.computerdatabase.model.net.Card

/**
 * Created by erychkov on 7/20/17.
 */
class CardAttribute(val card: Card, val listener: OnItemClickListener) : Attribute {
    override fun getTitle(): String {
        return card.name
    }

    override fun getSubTitle(): String? {
        return card.company?.name
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, card:Card)
    }
}