package com.wagon.hsxrjd.computerdatabase.module.card.Interactor

import android.util.Log
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.source.CacheDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by erychkov on 7/17/17.
 */
class CardInteractorImpl(val mDataSource: CardDataSource, val mLocalSource: CacheDataSource) : CardInteractor {

    fun getCardFromRemote(id: Int, observerF: Observer<in Card>) {
        mDataSource
                .getCard(id)
                .doOnNext { mLocalSource.storeCard(it) }
                .onErrorResumeNext { observer: Observer<in Card> -> getCardFromDirtyCache(id, observer) }
                .subscribe(observerF)
    }

    fun getCardFromDirtyCache(id: Int, observerF: Observer<in Card>) {
        mLocalSource
                .getDirtyCard(id)
                .subscribe(observerF)
    }

    fun getSimilarToFromRemote(id: Int, observerF: Observer<in List<Card>>) {
        mDataSource
                .getSimilarTo(id)
                .doOnNext { mLocalSource.attachSimilaritiesTo(it, id) }
                .onErrorResumeNext { observer: Observer<in List<Card>> -> getSimilarToFromDirtyCache(id, observer) }
                .subscribe(observerF)
    }

    fun getSimilarToFromDirtyCache(id: Int, observerF: Observer<in List<Card>>) {
        mLocalSource
                .getDirtySimilarTo(id)
                .subscribe(observerF)
    }


    override fun getCard(id: Int): Observable<Card> {
        return mLocalSource
                .getCard(id)
                .onErrorResumeNext { observer: Observer<in Card> -> getCardFromRemote(id, observer) }
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return mLocalSource
                .getSimilarTo(id)
                .onErrorResumeNext { observer: Observer<in List<Card>> -> getSimilarToFromRemote(id, observer) }
    }
}