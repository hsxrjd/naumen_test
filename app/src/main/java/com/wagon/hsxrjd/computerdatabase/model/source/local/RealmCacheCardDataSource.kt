package com.wagon.hsxrjd.computerdatabase.model.source.local

import com.wagon.hsxrjd.computerdatabase.model.db.CardRealm
import com.wagon.hsxrjd.computerdatabase.model.db.PageRealm
import com.wagon.hsxrjd.computerdatabase.model.mutate
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.net.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CacheDataSource
import io.reactivex.Observable
import io.realm.Realm

/**
 * Created by hsxrjd on 24.05.17.
 */
class RealmCacheCardDataSource : CacheDataSource {
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
        val source = Realm.getDefaultInstance()
        source
                .where(CardRealm::class.java)
                .equalTo("id", id)
                .findFirst()
                ?.let {
                    val list: MutableList<Card> = mutableListOf()
                    it.similarities.mapTo(list) { it.mutate() }
                    return Observable.just(list)
                }
                ?: return Observable.error { Throwable("not in db") }
    }

    override fun getDirtyCards(page: Int): Observable<Page> {
        val source = Realm.getDefaultInstance()
        source
                .where(PageRealm::class.java)
                .equalTo("page", page)
                .findFirst()
                ?.let {
                    return Observable.just(it.mutate())
                }
                ?: return Observable.error { Throwable("not in db") }
    }

    override fun attachSimilaritiesTo(cardList: List<Card>, id: Int) {
        val source = Realm.getDefaultInstance()
        source
                .where(CardRealm::class.java)
                .equalTo("id", id)
                .findFirst()
                ?.let {
                    card: CardRealm ->
                    source.beginTransaction()
                    cardList.mapTo(card.similarities) { it.mutate() }
                    source.copyToRealmOrUpdate(card)
                    source.commitTransaction()
                }
    }


    override fun storePage(page: Page) {
        val source = Realm.getDefaultInstance()
        val rPage = page.mutate()
        rPage.creationTime = System.currentTimeMillis()
        source.executeTransactionAsync {
            it.copyToRealmOrUpdate(rPage)
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

    override fun getCards(page: Int): Observable<Page> {
        val source = Realm.getDefaultInstance()
        source
                .where(PageRealm::class.java)
                .equalTo("page", page)
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
        val source = Realm.getDefaultInstance()
        source
                .where(CardRealm::class.java)
                .equalTo("id", id)
                .findFirst()
                ?.let {
                    if (System.currentTimeMillis() - it.creationTime <= cacheLifeTime) {
                        val list: MutableList<Card> = mutableListOf()
                        it.similarities.mapTo(list) { it.mutate() }
                        if (list.isEmpty())
                            return Observable.error { Throwable("not in db") }
                        return Observable.just(list)
                    } else {
                        return Observable.error { Throwable("dirty") }
                    }
                }
                ?: return Observable.error { Throwable("not in db") }
    }

    companion object {
        val cacheLifeTime: Int = 60 * 1000
    }
}