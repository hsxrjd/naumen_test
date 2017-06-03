package com.wagon.hsxrjd.computerdatabase.presenter

import android.util.Log
import android.view.View
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.view.CardListFragmentView

/**
 * Created by hsxrjd on 24.05.17.
 */
class CardListPresenter private constructor() : BasePresenter<CardListFragmentView>() {
    //todo продумать как ограничивать кол-во загрузок если все на текущий момент загружено

    private var pageCount: Int = 0

    fun start() {
        loadPage(0)
    }

    fun loadPage(page: Int) {
        pageCount = page
        val view: CardListFragmentView? = mView.get()
        view?.showLoading()
        mDataSource.get()?.let {
            it
                    .getCards(page)
                    .doOnComplete { view?.hideLoading() }
                    .doOnError { view?.showMessage("Error loading data on page $page") }
                    .subscribe { p: Page? -> p?.items?.let { view?.showCardList(it) } }
        }

    }

    fun loadNextPage() {
        pageCount++
        Log.d("DEBUG", "Page to load: $pageCount")
        val view: CardListFragmentView? = mView.get()
        view?.showLoading()
        mDataSource.get()?.let {
            it
                    .getCards(pageCount)
                    .doOnComplete {
                        Log.d("DEBUG", "Page $pageCount loaded")
                        view?.hideLoading()
                    }
                    .doOnError {
                        view?.showMessage("Error loading data on page $pageCount")
                        pageCount--
                    }
                    .subscribe { p: Page? ->
                        Log.d("DEBUG", "${p?.items?.size}")

                        p?.items?.let {
                            if (it.isEmpty())
                                view?.showMessage("All cards loaded")
                            else
                                view?.showCardList(it)
                        }
                    }
        }
    }

    fun onCardClicked(view: View, card: Card) {
        mView.get()?.cardClicked(view, card)
    }

    private object Holder {
        val instance = CardListPresenter()
    }

    companion object {
        val instance: CardListPresenter by lazy {
            Log.d("DEB", "${Holder.instance}")
            Holder.instance
        }
    }
}