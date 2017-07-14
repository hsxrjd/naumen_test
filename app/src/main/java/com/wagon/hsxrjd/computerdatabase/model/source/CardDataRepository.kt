package com.wagon.hsxrjd.computerdatabase.model.source

import android.util.Log
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.presenter.NaumenApi
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by hsxrjd on 24.05.17.
 */
class CardDataRepository @Inject constructor(private val mRemoteSource: RemoteCardDataSource) : CardDataSource {

    override fun getCards(page: Int): Observable<Page> {
        return mRemoteSource.getCards(page)
    }

    override fun getCard(id: Int): Observable<Card> {
        return mRemoteSource.getCard(id)
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return mRemoteSource.getSimilarTo(id)
    }

}