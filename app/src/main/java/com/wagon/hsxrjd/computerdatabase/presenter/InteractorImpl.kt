package com.wagon.hsxrjd.computerdatabase.presenter

import android.util.Log
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

/**
 * Created by erychkov on 7/14/17.
 */
class InteractorImpl(val mDataSource: CardDataSource, internal val mSubject: ReplaySubject<Page>) : Interactor {

    override fun loadPage(id: Int) {
        mDataSource.getCards(id).subscribe { mSubject.onNext(it)  }
//        mDataSource.getCards(id).map { Log.d("DEF", "${it.total}") }
//        mDataSource.getCards(id).map { p: Page ->
//            Log.d("LOP", "$p")
//            mSubject.onNext(p)
//        }
    }
}