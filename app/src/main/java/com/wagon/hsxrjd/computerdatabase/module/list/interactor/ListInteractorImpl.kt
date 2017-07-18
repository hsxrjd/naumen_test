package com.wagon.hsxrjd.computerdatabase.module.list.interactor

import com.wagon.hsxrjd.computerdatabase.model.net.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CacheDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.ResultObject
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by erychkov on 7/14/17.
 */
class ListInteractorImpl(val mDataSource: CardDataSource, val mLocalSource: CacheDataSource, internal val mSubject: PublishSubject<ResultObject>) : ListInteractor {


    override fun loadPage(id: Int) {
        mLocalSource
                .getCards(id)
                .onErrorResumeNext { _: Throwable -> fetchRemoteAndStore(id) }
                .onErrorResumeNext { _: Throwable -> mLocalSource.getDirtyCards(id) }
                .subscribe({ mSubject.onNext(ResultObject(it)) }, { mSubject.onNext(ResultObject(it)) })
    }

    private fun fetchRemoteAndStore(id: Int): Observable<Page> {
        return mDataSource.getCards(id).doOnNext { mLocalSource.storePage(it) }
    }
}