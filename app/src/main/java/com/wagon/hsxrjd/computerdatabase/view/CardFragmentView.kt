package com.wagon.hsxrjd.computerdatabase.view

import com.wagon.hsxrjd.computerdatabase.model.Card

/**
 * Created by hsxrjd on 03.06.17.
 */
interface CardFragmentView : BaseCardView {
    fun showCard(card: Card)
    fun showSimilarTo(cardList: List<Card>)
}
