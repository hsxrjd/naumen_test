package com.wagon.hsxrjd.computerdatabase.module.list

import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.contract.BaseCardView

/**
 * Created by hsxrjd on 23.05.17.
 */


interface CardListFragmentView : BaseCardView {
    fun showCardList(cardList: List<Card>)
    fun showNextPage(cardList: List<Card>)
    fun switchLoadingAbility(flag: Boolean)
}