package com.wagon.hsxrjd.computerdatabase.model.source

import android.util.Log
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.presenter.NaumenApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by hsxrjd on 24.05.17.
 */
class RemoteCardDataSource : CardDataSource {
    val service: NaumenApi = NaumenApi.instance

    override fun getCard(id: Int): Observable<Card> {
        return service.mApi.getCard(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return service.mApi.getSimilarTo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCards(page: Int): Observable<Page> {
        return service.mApi.getPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}