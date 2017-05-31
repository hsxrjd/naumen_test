package com.wagon.hsxrjd.computerdatabase.presenter

import android.util.Log
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.view.CardListFragmentView

/**
 * Created by hsxrjd on 24.05.17.
 */
class CardListPresenter : BasePresenter<CardListFragmentView>() {
    private lateinit var mDataSource: CardDataSource
    private var pageCount: Int = 0

    fun setDataSource(source: CardDataSource) {
        mDataSource = source
    }

    fun start() {
        loadPage(0)
    }

    fun loadPage(page: Int) {
        pageCount = page
        val view: CardListFragmentView? = mView.get()
        view?.showLoading()
        mDataSource.getCards(page)
                .doOnComplete { view?.hideLoading() }
                .doOnError { view?.showMessage("Error loading data on page $page") }
                .subscribe { p: Page? -> p?.items?.let { view?.showCardList(it) } }

    }

    fun loadNextPage() {
        pageCount++
        Log.d("DEBUG", "Page to load: $pageCount")
        val view: CardListFragmentView? = mView.get()
        view?.showLoading()
        mDataSource.getCards(pageCount)
                .doOnComplete { view?.hideLoading() }
                .doOnError {
                    view?.showMessage("Error loading data on page $pageCount")
                    pageCount--
                }
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