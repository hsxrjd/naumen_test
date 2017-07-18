package com.wagon.hsxrjd.computerdatabase.module.card.Interactor

import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import io.reactivex.Observable

/**
 * Created by erychkov on 7/17/17.
 */
class CardInteractorImpl(val mDataSource: CardDataSource, val mLocalSource: CardDataSource) : CardInteractor {
    override fun getCard(id: Int): Observable<Card> {
        return mDataSource.getCard(id).onErrorResumeNext(mDataSource.getCard(id))
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return mDataSource.getSimilarTo(id)
    }
}