package com.wagon.hsxrjd.computerdatabase.model.source.local

import android.util.Log
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.model.Similar
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.WritableDataSource
import io.reactivex.Observable
import io.realm.Realm

/**
 * Created by hsxrjd on 24.05.17.
 */
class RealmCacheCardDataSource(val source: Realm) : CardDataSource, WritableDataSource {
    override fun attachSimilaritiesTo(cardList: List<Card>, id: Int) {
        source.executeTransactionAsync {
            val obj = it.createObject(Similar::class.java)
            obj.similarities.addAll(cardList)
            obj.id = id
        }
    }

    override fun storePage(page: Page) {
        source.executeTransactionAsync {
            it.beginTransaction()
            it.copyToRealmOrUpdate(page)
            it.commitTransaction()
        }
    }


    override fun storeCard(card: Card) {
        source.executeTransactionAsync {
            it.beginTransaction()
            it.copyToRealmOrUpdate(card)
            it.commitTransaction()
        }
    }

    override fun getCards(page: Int): Observable<Page> {
        val t = source
                .where(Page::class.java)
                .equalTo("page", page)
                .findFirst()
        return Observable.just(
                t ?: Page()
        )
    }

    override fun getCard(id: Int): Observable<Card> {
        val t = source
                .where(Card::class.java)
                .equalTo("id", id)
                .findFirst()
        return Observable.just(
                t ?: Card()
        )
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return Observable.just(
                source
                        .where(Similar::class.java)
                        .equalTo("id", id)
                        .findFirst()
                        .similarities
        )
    }


}