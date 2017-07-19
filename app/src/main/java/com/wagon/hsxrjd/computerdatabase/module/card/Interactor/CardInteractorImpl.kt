package com.wagon.hsxrjd.computerdatabase.module.card.Interactor

import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.source.strategy.OperationFactory
import io.reactivex.Observable

/**
 * Created by erychkov on 7/17/17.
 */
class CardInteractorImpl(val operationFactory: OperationFactory) : CardInteractor {

//    fun fetchCardRemoteAndStore(id: Int): Observable<Card> {
//        return operationFactory.getStrategy()
//                .getCard(id)
//                .doOnNext { mLocalSource.storeCard(it) }
//    }
//
//    fun fetchSimilarToRemoteAndStore(id: Int): Observable<List<Card>> {
//        return operationFactory.getStrategy()
//                .getSimilarTo(id)
//                .doOnNext { mLocalSource.attachSimilaritiesTo(it, id) }
//    }
    override fun getCard(id: Int): Observable<Card> {
        return operationFactory
                .buildFetchCardOperation()
                .perform(id)
//                .onErrorResumeNext { _: Throwable -> fetchCardRemoteAndStore(id) }
//                .onErrorResumeNext { _: Throwable -> mLocalSource.getDirtyCard(id) }
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return operationFactory
                .buildFetchSimilarOperation()
                .perform(id)
    }
}