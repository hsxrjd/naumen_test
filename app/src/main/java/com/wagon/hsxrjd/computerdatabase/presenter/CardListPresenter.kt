package com.wagon.hsxrjd.computerdatabase.presenter

import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.view.CardListFragmentView
import io.reactivex.subjects.ReplaySubject

/**
 * Created by hsxrjd on 24.05.17.
 */
class CardListPresenter : BasePresenter<CardListFragmentView>() {
    private lateinit var mDataSource: CardDataSource

    fun setDataSource(source: CardDataSource) {
        mDataSource = source
    }

    fun start() {
        loadCardList(0)
    }

    private fun loadCardList(page: Int) {
        val view: CardListFragmentView? = mView.get()
        view?.showLoading()
        mDataSource.getCards(page)
                .doOnComplete { view?.hideLoading() }
                .doOnError { view?.showMessage("Error loading data on page $page") }
                .subscribe { p: Page? -> p?.items?.let { view?.showCardList(it) } }

    }

    fun onCardClicked(card: Card) {
        mView.get()?.showMessage("Not implemented, card id: ${card.id}")
    }

    private object Holder {
        val instance = CardListPresenter()
    }

    companion object {
        val instance: CardListPresenter by lazy { Holder.instance }
    }
}