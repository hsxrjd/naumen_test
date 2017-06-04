package com.wagon.hsxrjd.computerdatabase.model.source

import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by hsxrjd on 24.05.17.
 */
class CacheCardDataSource : CardDataSource {

    private val storage: MutableMap<Int, Card> = hashMapOf()

    fun storeCard(card: Card) {
        storage.put(card.id, card)
    }

    override fun getCards(page: Int): Observable<Page> {
        return Observable.just(null)
    }

    override fun getCard(id: Int): Observable<Card> = Observable
            .just(storage[id] ?: Card(-1))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return Observable.just(null)
    }


}