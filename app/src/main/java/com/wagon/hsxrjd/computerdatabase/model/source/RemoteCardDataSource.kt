package com.wagon.hsxrjd.computerdatabase.model.source

import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.presenter.NaumenAPI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by hsxrjd on 24.05.17.
 */
class RemoteCardDataSource : CardDataSource {

    override fun getCard(id: Int): Observable<Card> {
        return NaumenAPI.api.getCard(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return NaumenAPI.api.getSimilarTo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCards(page: Int): Observable<Page> {
        return NaumenAPI.api.getPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}