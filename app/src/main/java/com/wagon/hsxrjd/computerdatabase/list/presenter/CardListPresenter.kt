package com.wagon.hsxrjd.computerdatabase.list.presenter

import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.contract.BasePresenter
import com.wagon.hsxrjd.computerdatabase.list.CardListFragmentView
import io.reactivex.Observable

/**
 * Created by hsxrjd on 24.05.17.
 */
class CardListPresenter(mObservable: Observable<Page>) : BasePresenter<CardListFragmentView>() {

    init {
        mObservable
                .subscribe({
                    p: Page ->
                    p.items.let {
                        if (it.isEmpty()) {
                            mView.get()?.showMessage(R.string.message_all_data_loaded)
                        } else mView.get()?.showCardList(it)
                    }
                }, {
                    mView.get()?.showMessage(R.string.message_error_loading_page)
                    mView.get()?.hideLoading()
                })

    }


}

