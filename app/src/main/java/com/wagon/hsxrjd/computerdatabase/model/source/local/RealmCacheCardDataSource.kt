package com.wagon.hsxrjd.computerdatabase.model.source.local

import com.wagon.hsxrjd.computerdatabase.log.LoggedClass
import com.wagon.hsxrjd.computerdatabase.log.Logger
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
class RealmCacheCardDataSource : CacheDataSource, LoggedClass {


    override fun getDirtyCard(id: Int): Observable<Card> {
        Logger.logger.debug(getClassName(), this::getDirtyCard.name)

        val source = Realm.getDefaultInstance()
        source
                .where(CardRealm::class.java)
                .equalTo("id", id)
                .findFirst()
                ?.let {
                    return Observable.just(it.mutate())
                }
                ?: return Observable.error { Throwable("Card with id=$id not in db") }
    }

    override fun getDirtySimilarTo(id: Int): Observable<List<Card>> {
        Logger.logger.debug(getClassName(), this::getDirtySimilarTo.name)

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
                ?: return Observable.error { Throwable("Card with id=$id not in db") }
    }

    override fun getDirtyCards(page: Int): Observable<Page> {
        Logger.logger.debug(getClassName(), this::getDirtyCards.name)

        val source = Realm.getDefaultInstance()
        source
                .where(PageRealm::class.java)
                .equalTo("page", page)
                .findFirst()
                ?.let {
                    return Observable.just(it.mutate())
                }
                ?: return Observable.error { Throwable("Page #$page not in db") }
    }

    override fun attachSimilaritiesTo(cardList: List<Card>, id: Int) {
        Logger.logger.debug(getClassName(), this::attachSimilaritiesTo.name)

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
        Logger.logger.debug(getClassName(), this::storePage.name)
        val source = Realm.getDefaultInstance()
        val rPage = page.mutate()
        rPage.creationTime = System.currentTimeMillis()
        source.executeTransactionAsync {
            it.copyToRealmOrUpdate(rPage)
        }
    }


    override fun storeCard(card: Card) {
        Logger.logger.debug(getClassName(), this::storeCard.name)
        val source = Realm.getDefaultInstance()
        val rCard = card.mutate()
        rCard.creationTime = System.currentTimeMillis()
        source.executeTransactionAsync {
            it.copyToRealmOrUpdate(rCard)
        }
    }

    override fun getCards(page: Int): Observable<Page> {
        Logger.logger.debug(getClassName(), this::getCards.name)
        val source = Realm.getDefaultInstance()
        source
                .where(PageRealm::class.java)
                .equalTo("page", page)
                .findFirst()
                ?.let {
                    if (System.currentTimeMillis() - it.creationTime <= cacheLifeTime) {
                        return Observable.just(it.mutate())
                    } else {
                        return Observable.error { Throwable("Cache is dirty") }
                    }
                }
                ?: return Observable.error { Throwable("Page #$page not in db") }
    }

    override fun getCard(id: Int): Observable<Card> {
        Logger.logger.debug(getClassName(), this::getCard.name)
        val source = Realm.getDefaultInstance()
        source
                .where(CardRealm::class.java)
                .equalTo("id", id)
                .findFirst()
                ?.let {
                    if (System.currentTimeMillis() - it.creationTime <= cacheLifeTime) {
                        return Observable.just(it.mutate())
                    } else {
                        return Observable.error { Throwable("Cache is dirty") }
                    }
                }
                ?: return Observable.error { Throwable("Card with id=$id not in db") }
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        Logger.logger.debug(getClassName(), this::getSimilarTo.name)
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
                            return Observable.error { Throwable("Similarities to Card#$id not in db") }
                        return Observable.just(list)
                    } else {
                        return Observable.error { Throwable("Cache is dirty") }
                    }
                }
                ?: return Observable.error { Throwable("Card with id=$id not in db") }
    }


    override fun getClassName(): String {
        return className
    }

    companion object {
        val className = "RealmCacheCardDataSource"

        val cacheLifeTime: Int = 60 * 1000
    }
}