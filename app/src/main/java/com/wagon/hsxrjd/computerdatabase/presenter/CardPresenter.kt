package com.wagon.hsxrjd.computerdatabase.presenter

import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.view.CardFragmentView

/**
 * Created by hsxrjd on 03.06.17.
 */
class CardPresenter(val mDataSource: CardDataSource) : BasePresenter<CardFragmentView>() {

    fun loadCard(id: Int) {
        val view: CardFragmentView? = mView.get()
        view?.showLoading()
        mDataSource
                .getCard(id)
                .doOnComplete { view?.hideLoading() }
                .doOnError { view?.showMessage(R.string.message_error_loading_card) }
                .subscribe(
                        { c: Card? -> c?.let { view?.showCard(it) } },
                        { view?.showMessage(R.string.message_error_loading_card) }
                )
    }

    fun showSimilarTo(id: Int) {
        val view: CardFragmentView? = mView.get()
        view?.showLoading()
        mDataSource
                .getSimilarTo(id)
                .doOnComplete { view?.hideLoading() }
                .doOnError { view?.showMessage(R.string.message_error_loading_similar_to) }
                .subscribe({ c: List<Card>? -> c?.let { view?.showSimilarTo(it) } },
                        { view?.showMessage(R.string.message_error_loading_similar_to) }
                )
    }
}