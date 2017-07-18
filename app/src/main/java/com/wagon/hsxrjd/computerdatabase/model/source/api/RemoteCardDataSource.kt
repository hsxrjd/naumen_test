package com.wagon.hsxrjd.computerdatabase.model.source.api

import android.util.Log
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.net.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


/**
 * Created by hsxrjd on 24.05.17.
 */
class RemoteCardDataSource constructor(val service: NaumenApi) : CardDataSource {

    override fun getCard(id: Int): Observable<Card> {
        return service.mApi.getCard(id)
                .subscribeOn(Schedulers.io())
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return service.mApi.getSimilarTo(id)
                .subscribeOn(Schedulers.io())
    }

    override fun getCards(page: Int): Observable<Page> {
        return service.mApi.getPage(page)
                .subscribeOn(Schedulers.io())
    }

}