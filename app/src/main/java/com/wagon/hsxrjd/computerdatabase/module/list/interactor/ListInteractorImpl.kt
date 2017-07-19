package com.wagon.hsxrjd.computerdatabase.module.list.interactor

import com.wagon.hsxrjd.computerdatabase.model.source.ResultObject
import com.wagon.hsxrjd.computerdatabase.model.source.strategy.OperationFactory
import io.reactivex.subjects.PublishSubject

/**
 * Created by erychkov on 7/14/17.
 */
class ListInteractorImpl(val operationFactory: OperationFactory, internal val mSubject: PublishSubject<ResultObject>) : ListInteractor {


    override fun loadPage(id: Int) {
        operationFactory
                .buildFetchPageOperation(id)
                .perform()
//                .onErrorResumeNext { _: Throwable -> fetchRemoteAndStore(id) }
//                .onErrorResumeNext { _: Throwable -> mLocalSource.getDirtyPage(id) }
                .subscribe({ mSubject.onNext(ResultObject(it)) }, { mSubject.onNext(ResultObject(it)) })
    }
//    private fun fetchRemoteAndStore(id: Int): Observable<Page> {
//        return operationFactory.getStrategy()
//                .getPage(id)
//                .doOnNext { mLocalSource.storePage(it) }
//    }
}