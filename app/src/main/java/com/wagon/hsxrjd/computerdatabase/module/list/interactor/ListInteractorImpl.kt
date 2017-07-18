package com.wagon.hsxrjd.computerdatabase.module.list.interactor

import com.wagon.hsxrjd.computerdatabase.model.net.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CacheDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.ResultObject
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.functions.Function
import io.reactivex.subjects.ReplaySubject

/**
 * Created by erychkov on 7/14/17.
 */
class ListInteractorImpl(val mDataSource: CardDataSource, val mLocalSource: CacheDataSource, internal val mSubject: ReplaySubject<ResultObject>) : ListInteractor {


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
//        val function: Function<in Throwable, out ObservableSource<Page>> = Function {
//
//        }
        mLocalSource
                .getCards(id)
//                .onErrorResumeNext(function)
                .onErrorResumeNext { observer: Observer<in Page> -> loadPageFromRemote(id, observer) }
                .subscribe({ mSubject.onNext(ResultObject(it)) }, { mSubject.onNext(ResultObject(it)) })
    }
}