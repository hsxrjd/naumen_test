package com.wagon.hsxrjd.computerdatabase.presenter

import android.os.Handler
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.view.CardListFragmentView

/**
 * Created by hsxrjd on 24.05.17.
 */
class CardListPresenter private constructor() : BasePresenter<CardListFragmentView>() {

    private var mPageCount: Int = 0
    private var mIsAllLoaded: Boolean = false

    private val mHandler: Handler = Handler()

    private val mRunnable: Runnable = Runnable {
        mIsAllLoaded = false
        mView.get()?.switchLoadingAbility(true)
    }

    fun start() {
        loadPage(0)
    }

    fun isAllLoaded(): Boolean {
        return mIsAllLoaded
    }

    fun loadPage(page: Int) {
        val pageCache = mPageCount
        mPageCount = page
        val view: CardListFragmentView? = mView.get()
        mDataSource.get()?.let {
            it
                    .getCards(page)
                    .doOnSubscribe {
                        view?.showLoading()
                    }
                    .doOnComplete { view?.hideLoading() }
                    .subscribe({
                        p: Page? ->
                        p?.items?.let {
                            if (it.isEmpty()) {
                                view?.showMessage("No data available")
                            } else view?.showCardList(it)
                        }
                    }, {
                        t: Throwable ->
                        view?.showMessage("Error loading data on page $page because ${t.localizedMessage}")
                        mPageCount = pageCache
                        view?.hideLoading()
                    })

        }

    }

    fun loadNextPage() {
        mHandler.removeCallbacks(mRunnable)
        val view: CardListFragmentView? = mView.get()
        mPageCount++
        mDataSource.get()?.let {
            it
                    .getCards(mPageCount)
                    .doOnSubscribe {
                        view?.showLoading()
                    }
                    .doOnComplete { view?.hideLoading() }
                    .subscribe({
                        p: Page? ->
                        p?.items?.let {
                            if (it.isEmpty()) {
                                view?.showMessage("All cards loaded")
                                mIsAllLoaded = true
                                view?.switchLoadingAbility(false)
                                mHandler.postDelayed(mRunnable, EXPIRATION_TIME)
                                mPageCount--
                            } else view?.showNextPage(it)
                        }
                    }, {
                        t: Throwable ->
                        view?.showMessage("Error loading data on page $mPageCount because ${t.localizedMessage}")
                        mPageCount--
                        view?.hideLoading()
                    })
        }
    }

    private object Holder {
        val mInstance = CardListPresenter()
    }

    companion object {
        val EXPIRATION_TIME: Long = 20000 //20 sec in millis
        val instance: CardListPresenter by lazy { Holder.mInstance }
    }
}