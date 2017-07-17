package com.wagon.hsxrjd.computerdatabase.presenter

import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.presenter.interactor.ListInteractor
import com.wagon.hsxrjd.computerdatabase.view.PaginationFragmentView
import io.reactivex.Observable

/**
 * Created by erychkov on 7/14/17.
 */
class PaginationPresenter(mObservable: Observable<Page>, val mInteractor: ListInteractor) : BasePresenter<PaginationFragmentView>() {

    private var mCurrent = 0
    private var mTotal = 0

    init {
        mObservable.subscribe({
            p: Page ->
            mCurrent = p.page
            mTotal = p.total / 10
            mView.get()?.showPage(mCurrent, mTotal)
        }, {
            mView.get()?.showMessage(R.string.message_error_loading_page)
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
        mInteractor.loadPage(mCurrent)
    }

}