package com.wagon.hsxrjd.computerdatabase.model.source

import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page
import io.reactivex.Observable

/**
 * Created by hsxrjd on 24.05.17.
 */
class CardDataRepository private constructor(): CardDataSource {
    private object Holder {
        val mInstance: CardDataRepository = CardDataRepository()
    }
    private val mRemoteSource: RemoteCardDataSource = RemoteCardDataSource()
    private val mLocalSource: CacheCardDataSource = CacheCardDataSource()

    override fun getCards(page: Int): Observable<Page> {
        return mRemoteSource.getCards(page)
    }

    override fun getCard(id: Int): Observable<Card> {
        return mRemoteSource.getCard(id)
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        return mRemoteSource.getSimilarTo(id)
    }

    companion object {
        val instance: CardDataRepository by lazy { Holder.mInstance }
    }
}