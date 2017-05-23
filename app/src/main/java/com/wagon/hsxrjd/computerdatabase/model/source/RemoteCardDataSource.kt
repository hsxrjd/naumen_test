package com.wagon.hsxrjd.computerdatabase.model.source

import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.presenter.NaumenAPI
import io.reactivex.Observable

/**
 * Created by hsxrjd on 24.05.17.
 */
object RemoteCardDataSource : CardDataSource {
    override fun getCard(id: Int): Observable<Card> {
        return NaumenAPI.api.getCard(id)
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return NaumenAPI.api.getSimilarTo(id)
    }

    override fun getCards(page: Int): Observable<Page> {
        return NaumenAPI.api.getPage(page)
    }

}