package com.wagon.hsxrjd.computerdatabase.list.interactor

import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import io.reactivex.subjects.ReplaySubject

/**
 * Created by erychkov on 7/14/17.
 */
class ListInteractorImpl(val mDataSource: CardDataSource, internal val mSubject: ReplaySubject<Page>) : ListInteractor {

    override fun loadPage(id: Int) {
        mDataSource.getCards(id).subscribe { mSubject.onNext(it) }
    }

}