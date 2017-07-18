package com.wagon.hsxrjd.computerdatabase.model.source.local

import com.wagon.hsxrjd.computerdatabase.model.db.CardRealm
import com.wagon.hsxrjd.computerdatabase.model.mutate
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.net.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CacheDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.WritableDataSource
import io.reactivex.Observable
import io.realm.Realm

/**
 * Created by hsxrjd on 24.05.17.
 */
class RealmCacheCardDataSource : CardDataSource, WritableDataSource, CacheDataSource {
    override fun getDirtyCard(id: Int): Observable<Card> {
        val source = Realm.getDefaultInstance()
        source
                .where(CardRealm::class.java)
                .equalTo("id", id)
                .findFirst()
                ?.let {
                    return Observable.just(it.mutate())
                }

                ?: return Observable.error { Throwable("not in db") }
    }

    override fun getDirtySimilarTo(id: Int): Observable<List<Card>> {
        return getSimilarTo(id)
    }

    override fun getDirtyCards(page: Int): Observable<Page?> {
        return getCards(page)
    }

    override fun attachSimilaritiesTo(cardList: List<Card>, id: Int) {
        val source = Realm.getDefaultInstance()

        source.executeTransactionAsync {
            //            val obj = it.createObject(Similar::class.java)
//            obj.similarities.addAll(cardList)
//            obj.id = id
        }
    }

    override fun storePage(page: Page) {
        val source = Realm.getDefaultInstance()

        source.executeTransactionAsync {
            it.beginTransaction()
//            it.copyToRealmOrUpdate(page)
            it.commitTransaction()
        }
    }


    override fun storeCard(card: Card) {
        val source = Realm.getDefaultInstance()
        val rCard = card.mutate()
        rCard.creationTime = System.currentTimeMillis()
        source.executeTransactionAsync {
            it.copyToRealmOrUpdate(rCard)
        }
    }

    override fun getCards(page: Int): Observable<Page?> {
        val t = null
//                .where(Page::class.java)
//                .equalTo("page", page)
//                .findFirst()
        return Observable.just(
                t
        )
    }

    override fun getCard(id: Int): Observable<Card> {
        val source = Realm.getDefaultInstance()
        source
                .where(CardRealm::class.java)
                .equalTo("id", id)
                .findFirst()
                ?.let {
                    if (System.currentTimeMillis() - it.creationTime <= cacheLifeTime) {
                        return Observable.just(it.mutate())
                    } else {
                        return Observable.error { Throwable("dirty") }
                    }
                }
                ?: return Observable.error { Throwable("not in db") }
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return Observable.just(
                arrayListOf()
//                source
//                        .where(Similar::class.java)
//                        .equalTo("id", id)
//                        .findFirst()
//                        .similarities
        )
    }

    companion object {
        val cacheLifeTime: Int = 60 * 1000
    }
}