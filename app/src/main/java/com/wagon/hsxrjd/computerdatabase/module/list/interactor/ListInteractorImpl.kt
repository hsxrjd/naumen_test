package com.wagon.hsxrjd.computerdatabase.module.list.interactor

import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.net.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CacheDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import io.reactivex.Observer
import io.reactivex.subjects.ReplaySubject

/**
 * Created by erychkov on 7/14/17.
 */
class ListInteractorImpl(val mDataSource: CardDataSource, val mLocalSource: CacheDataSource, internal val mSubject: ReplaySubject<Page>) : ListInteractor {


    fun loadPageFromRemote(id: Int, observerF: Observer<in Page>) {
        mDataSource
                .getCards(id)
                .doOnNext { mLocalSource.storePage(it) }
                .onErrorResumeNext { observer: Observer<in Page> -> loadPageFromDirtyCache(id, observer) }
                .subscribe(observerF)
    }
    fun loadPageFromDirtyCache(id: Int, observerF: Observer<in Page>) {
        mLocalSource
                .getDirtyCards(id)
                .subscribe(observerF)
    }

    override fun loadPage(id: Int) {
        mLocalSource
                .getCards(id)
                .onErrorResumeNext { observer: Observer<in Page> -> loadPageFromRemote(id, observer) }
                .subscribe { mSubject.onNext(it) }
    }
}