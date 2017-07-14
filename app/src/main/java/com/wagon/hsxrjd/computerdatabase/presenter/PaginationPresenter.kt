package com.wagon.hsxrjd.computerdatabase.presenter

import android.util.Log
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.view.PaginationFragmentView
import io.reactivex.Observable

/**
 * Created by erychkov on 7/14/17.
 */
class PaginationPresenter(mObservable: Observable<Page>, val mInteractor: Interactor) : BasePresenter<PaginationFragmentView>() {

    private var mCurrent = 0
    private var mTotal = 0

    init {
        mObservable.subscribe({
            p: Page ->
            mCurrent = p.page
            mTotal = p.total / 10
            mView.get()?.showPage(mCurrent, mTotal)
        }, {
            mView.get()?.showMessage("some rrr")
        })
    }

    fun onPressNext() {
        if (mCurrent < mTotal) {
            mInteractor.loadPage(mCurrent + 1)
        }
    }

    fun onPressPrev() {
        if (mCurrent > 0) {
            mInteractor.loadPage(mCurrent - 1)
        }
    }

    fun start() {
        mInteractor.loadPage(0)
    }

}