package com.wagon.hsxrjd.computerdatabase.model.source

import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page
import io.reactivex.Observable

/**
 * Created by hsxrjd on 24.05.17.
 */
object CardDataRepository : CardDataSource {
    override fun getCards(page: Int): Observable<Page> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCard(id: Int): Observable<Card> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}