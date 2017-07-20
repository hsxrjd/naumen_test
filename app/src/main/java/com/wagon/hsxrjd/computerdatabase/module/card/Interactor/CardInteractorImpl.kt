package com.wagon.hsxrjd.computerdatabase.module.card.Interactor

import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.source.strategy.OperationFactory
import io.reactivex.Observable

/**
 * Created by erychkov on 7/17/17.
 */
class CardInteractorImpl(val operationFactory: OperationFactory) : CardInteractor {

    override fun getCard(id: Int): Observable<Card> {
        return operationFactory
                .buildFetchCardOperation(id)
                .perform()
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return operationFactory
                .buildFetchSimilarOperation(id)
                .perform()
    }
}