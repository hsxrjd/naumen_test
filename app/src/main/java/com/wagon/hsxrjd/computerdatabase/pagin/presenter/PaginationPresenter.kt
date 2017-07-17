package com.wagon.hsxrjd.computerdatabase.pagin.presenter

import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.contract.BasePresenter
import com.wagon.hsxrjd.computerdatabase.list.interactor.ListInteractor
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.pagin.PaginationFragmentView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by erychkov on 7/14/17.
 */
class PaginationPresenter(mObservable: Observable<Page>, val mInteractor: ListInteractor) : BasePresenter<PaginationFragmentView>() {

    private var mCurrent = 0
    private var mTotal = 0

    init {
        mObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    p: Page ->
                    mCurrent = p.page
                    mTotal = p.total / 10
                    mView.get()?.showPage(mCurrent, mTotal)
                    calcButtonNextState()
                    calcButtonPrevState()
                }, {
                    mView.get()?.showMessage(R.string.message_error_loading_page)
                })
    }

    fun calcButtonNextState() {
        if (mCurrent + 1 >= mTotal) {
            mView.get()?.changeButtonNextState(false)
        }
    }

    fun calcButtonPrevState() {
        if (mCurrent <= 0) {
            mView.get()?.changeButtonPrevState(false)
        }
    }

    fun onPressNext() {
        if (mCurrent < mTotal) {
            mView.get()?.changeButtonPrevState(true)
            mInteractor.loadPage(mCurrent + 1)
        }
    }

    fun onPressPrev() {
        if (mCurrent > 0) {
            mView.get()?.changeButtonNextState(true)
            mInteractor.loadPage(mCurrent - 1)
        }
    }

    fun start() {
        mView.get()?.showPage(mCurrent, mTotal)
        if (mTotal != 0) {
            calcButtonNextState()
            calcButtonPrevState()
        }
        mInteractor.loadPage(mCurrent)
    }

}