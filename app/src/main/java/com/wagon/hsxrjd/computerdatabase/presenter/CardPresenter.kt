package com.wagon.hsxrjd.computerdatabase.presenter

import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.view.CardFragmentView

/**
 * Created by hsxrjd on 03.06.17.
 */
class CardPresenter private constructor(): BasePresenter<CardFragmentView>() {

    fun loadCard(id: Int) {
        val view: CardFragmentView? = mView.get()
        view?.showLoading()
        mDataSource.get()?.let {
            it
                    .getCard(id)
                    .doOnComplete { view?.hideLoading() }
                    .doOnError { view?.showMessage("Error loading card $id") }
                    .subscribe { c: Card? -> c?.let { view?.showCard(it) } }
        }
    }

    fun showSimilarities() {
        val view: CardFragmentView? = mView.get()
        view?.showLoading()
    }

    private object Holder {
        val mInstance = CardPresenter()
    }

    companion object {
        val instance: CardPresenter by lazy { Holder.mInstance }
    }
}