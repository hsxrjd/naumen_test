package com.wagon.hsxrjd.computerdatabase.module.card

import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.contract.BaseCardView

/**
 * Created by hsxrjd on 03.06.17.
 */
interface CardFragmentView : BaseCardView {
    fun showCard(card: Card)
    fun showSimilarTo(cardList: List<Card>)
}
