package com.wagon.hsxrjd.computerdatabase.presenter

import android.util.Log
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.view.CardListFragmentView
import io.reactivex.Observable

/**
 * Created by hsxrjd on 24.05.17.
 */
class CardListPresenter(mObservable: Observable<Page>) : BasePresenter<CardListFragmentView>() {

    init {
        val view = mView.get()
        mObservable
                .subscribe({
                    p: Page ->
                    p.items.let {
                        if (it.isEmpty()) {
                            view?.showMessage(R.string.message_all_data_loaded)
                        } else view?.showCardList(it)
                    }
                }, {
                    view?.showMessage(R.string.message_error_loading_page)
                    view?.hideLoading()
                })

    }


}

