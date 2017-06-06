package com.wagon.hsxrjd.computerdatabase.presenter

import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.view.CardFragmentView

/**
 * Created by hsxrjd on 03.06.17.
 */
class CardPresenter private constructor() : BasePresenter<CardFragmentView>() {

    fun loadCard(id: Int) {
        val view: CardFragmentView? = mView.get()
        view?.showLoading()
        mDataSource.get()?.let {
            it
                    .getCard(id)
                    .doOnComplete { view?.hideLoading() }
                    .subscribe(
                            { c: Card? -> c?.let { view?.showCard(it) } },
                            { t: Throwable -> view?.showMessage(R.string.message_error_loading_card, id, t.localizedMessage) }
                    )
        }
    }

    fun showSimilarTo(id: Int) {
        val view: CardFragmentView? = mView.get()
        view?.showLoading()
        mDataSource.get()?.let {
            it
                    .getSimilarTo(id)
                    .doOnComplete { view?.hideLoading() }
                    .subscribe({ c: List<Card>? -> c?.let { view?.showSimilarTo(it) } },
                            { t: Throwable -> view?.showMessage(R.string.message_error_loading_similar_to, id, t.localizedMessage) }
                    )
        }
    }

    private object Holder {
        val mInstance = CardPresenter()
    }

    companion object {
        val instance: CardPresenter by lazy { Holder.mInstance }
    }
}