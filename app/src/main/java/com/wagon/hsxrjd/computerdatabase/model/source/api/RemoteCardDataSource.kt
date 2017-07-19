package com.wagon.hsxrjd.computerdatabase.model.source.api

import com.wagon.hsxrjd.computerdatabase.log.LoggedClass
import com.wagon.hsxrjd.computerdatabase.log.Logger
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.net.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


/**
 * Created by hsxrjd on 24.05.17.
 */
class RemoteCardDataSource constructor(val service: NaumenApi) : CardDataSource, LoggedClass {
    companion object {
        val className = "RemoteCardDataSource"
    }

    override fun getClassName(): String {
        return className
    }

    override fun getCard(id: Int): Observable<Card> {
        Logger.logger.debug(getClassName(), this::getCard.name)
        return service.mApi.getCard(id)
                .subscribeOn(Schedulers.io())
    }

    override fun getSimilarTo(id: Int): Observable<List<Card>> {
        Logger.logger.debug(getClassName(), this::getSimilarTo.name)
        return service.mApi.getSimilarTo(id)
                .subscribeOn(Schedulers.io())
    }

    override fun getCards(page: Int): Observable<Page> {
        Logger.logger.debug(getClassName(), this::getCards.name)
        return service.mApi.getPage(page)
                .subscribeOn(Schedulers.io())
    }

}