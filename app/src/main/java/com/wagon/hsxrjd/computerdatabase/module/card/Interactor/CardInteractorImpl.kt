package com.wagon.hsxrjd.computerdatabase.module.card.Interactor

import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.source.CacheDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import io.reactivex.Observable

/**
 * Created by erychkov on 7/17/17.
 */
class CardInteractorImpl(val mDataSource: CardDataSource, val mLocalSource: CacheDataSource) : CardInteractor {

    fun fetchCardRemoteAndStore(id: Int): Observable<Card> {
        return mDataSource
                .getCard(id)
                .doOnNext { mLocalSource.storeCard(it) }
    }

    fun fetchSimilarToRemoteAndStore(id: Int): Observable<List<Card>> {
        return mDataSource
                .getSimilarTo(id)
                .doOnNext { mLocalSource.attachSimilaritiesTo(it, id) }
    }

    override fun getCard(id: Int): Observable<Card> {
        return mLocalSource
                .getCard(id)
                .onErrorResumeNext { _: Throwable -> fetchCardRemoteAndStore(id) }
                .onErrorResumeNext { _: Throwable -> mLocalSource.getDirtyCard(id) }
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return mLocalSource
                .getSimilarTo(id)
                .onErrorResumeNext { _: Throwable -> fetchSimilarToRemoteAndStore(id) }
                .onErrorResumeNext { _: Throwable -> mLocalSource.getDirtySimilarTo(id) }
    }
}