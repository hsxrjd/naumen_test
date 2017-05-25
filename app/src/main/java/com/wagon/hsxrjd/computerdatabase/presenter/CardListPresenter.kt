package com.wagon.hsxrjd.computerdatabase.presenter

import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.view.CardListFragmentView
import java.util.concurrent.TimeUnit

/**
 * Created by hsxrjd on 24.05.17.
 */
class CardListPresenter(private var mDataSource: CardDataSource) : BasePresenter<CardListFragmentView>() {
    fun start() {
        loadCardList(0)
    }

    private fun loadCardList(page: Int) {
        mView?.showLoading()
        mDataSource.getCards(page)
                .doOnComplete { mView?.hideLoading() }
                .doOnError { mView?.showMessage("Error loading data") }
                .subscribe { p: Page? -> p?.items?.let { mView?.showCardList(it) } }

    }

    fun onCardClicked(card: Card) {}
}