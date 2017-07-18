package com.wagon.hsxrjd.computerdatabase.module.card.Interactor

import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.source.CacheDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.WritableDataSource
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by erychkov on 7/17/17.
 */
class CardInteractorImpl(val mDataSource: CardDataSource, val mLocalSource: CardDataSource) : CardInteractor {

    override fun getCard(id: Int): Observable<Card> {
        return mLocalSource
                .getCard(id)
                .onErrorResumeNext {
                    subscriber: Observer<in Card> ->
                    mDataSource
                            .getCard(id)
                            .doOnNext { (mLocalSource as WritableDataSource).storeCard(it) }
                            .onErrorResumeNext {
                                sub:Observer<in Card> ->
                                (mLocalSource as CacheDataSource)
                                        .getDirtyCard(id)
                                        .subscribe(sub)
                            }
                            .subscribe(subscriber)
                }
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return mDataSource.getSimilarTo(id)
    }
}