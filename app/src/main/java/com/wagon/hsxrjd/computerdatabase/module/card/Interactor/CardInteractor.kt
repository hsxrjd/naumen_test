package com.wagon.hsxrjd.computerdatabase.module.card.Interactor

import com.wagon.hsxrjd.computerdatabase.adapter.attribute.Attribute
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import io.reactivex.Observable

/**
 * Created by erychkov on 7/17/17.
 */
interface CardInteractor {
    fun getCard(id: Int): Observable<List<Attribute>>
    fun getSimilarTo(id: Int): Observable<List<Attribute>>
}