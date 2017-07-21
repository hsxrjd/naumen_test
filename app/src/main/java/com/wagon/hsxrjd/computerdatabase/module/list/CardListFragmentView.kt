package com.wagon.hsxrjd.computerdatabase.module.list

import com.wagon.hsxrjd.computerdatabase.contract.BaseCardView
import com.wagon.hsxrjd.computerdatabase.model.net.Card

/**
 * Created by hsxrjd on 23.05.17.
 */


interface CardListFragmentView : BaseCardView {
    fun showCardList(cardList: List<Card>)
}