package com.wagon.hsxrjd.computerdatabase.model.source

import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page

/**
 * Created by erychkov on 7/17/17.
 */
interface WritableDataSource: CardDataSource {
    fun storeCard(card: Card)
    fun storePage(page: Page)
    fun attachSimilaritiesTo(cardList: List<Card>, id:Int)
}