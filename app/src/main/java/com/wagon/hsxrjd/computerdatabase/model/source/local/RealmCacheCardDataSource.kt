package com.wagon.hsxrjd.computerdatabase.model.source.local

import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import io.reactivex.Observable
import io.realm.Realm

/**
 * Created by hsxrjd on 24.05.17.
 */
class RealmCacheCardDataSource(val source: Realm) : CardDataSource {


    fun storeCard(card: Card) {
        source.beginTransaction()
        source.copyToRealmOrUpdate(card)
        source.commitTransaction()
    }

    override fun getCards(page: Int): Observable<Page> {
        return Observable.just(source.where(Page::class.java).equalTo("page", page).findFirst())
    }

    override fun getCard(id: Int): Observable<Card> {
        return Observable.just(source.where(Card::class.java).equalTo("id", id).findFirst())
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return Observable.just(null)
    }


}