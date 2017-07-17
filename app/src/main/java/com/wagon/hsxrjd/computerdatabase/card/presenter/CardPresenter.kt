package com.wagon.hsxrjd.computerdatabase.card.presenter

import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.card.Interactor.CardInteractor
import com.wagon.hsxrjd.computerdatabase.contract.BasePresenter
import com.wagon.hsxrjd.computerdatabase.card.CardFragmentView

/**
 * Created by hsxrjd on 03.06.17.
 */
class CardPresenter(val mInteractor: CardInteractor) : BasePresenter<CardFragmentView>() {

    fun loadCard(id: Int) {
        val view: CardFragmentView? = mView.get()
        view?.showLoading()

        mInteractor
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
        mInteractor
                .getSimilarTo(id)
                .doOnComplete { view?.hideLoading() }
                .doOnError { view?.showMessage(R.string.message_error_loading_similar_to) }
                .subscribe({ c: List<Card>? -> c?.let { view?.showSimilarTo(it) } },
                        { view?.showMessage(R.string.message_error_loading_similar_to) }
                )
    }
}