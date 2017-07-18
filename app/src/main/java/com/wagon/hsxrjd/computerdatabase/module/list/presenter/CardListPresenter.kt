package com.wagon.hsxrjd.computerdatabase.module.list.presenter

import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.contract.BasePresenter
import com.wagon.hsxrjd.computerdatabase.model.source.ResultObject
import com.wagon.hsxrjd.computerdatabase.module.list.CardListFragmentView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by hsxrjd on 24.05.17.
 */
class CardListPresenter(mObservable: Observable<ResultObject>) : BasePresenter<CardListFragmentView>() {

    init {
        mObservable
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    mView.get()?.showMessage(R.string.message_error_loading_page)
                }
                .subscribe({
                    p: ResultObject ->
                    when (p.state) {
                        ResultObject.State.Success -> {
                            p.page?.items?.let {
                                if (it.isEmpty()) {
                                    mView.get()?.showMessage(R.string.message_all_data_loaded)
                                } else mView.get()?.showCardList(it)
                            }
                        }
                        ResultObject.State.Failure -> {
                            mView.get()?.showMessage(R.string.message_error_loading_page)
                            mView.get()?.hideLoading()
                        }
                    }
                }, {
                    mView.get()?.showMessage(R.string.message_error_loading_page)
                    mView.get()?.hideLoading()
                })
    }
}

