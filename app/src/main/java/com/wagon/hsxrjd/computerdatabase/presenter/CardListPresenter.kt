package com.wagon.hsxrjd.computerdatabase.presenter

import android.os.Handler
import com.wagon.hsxrjd.computerdatabase.MainApplication
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.view.CardListFragmentView

/**
 * Created by hsxrjd on 24.05.17.
 */
class CardListPresenter (val mDataSource: CardDataSource): BasePresenter<CardListFragmentView>() {

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
        mDataSource
                    .getCards(page)
                    .doOnSubscribe {
                        view?.showLoading()
                    }
                    .doOnComplete { view?.hideLoading() }
                    .doOnError {
                        view?.showMessage(R.string.message_error_loading_page)
                        mPageCount = pageCache
                        view?.hideLoading()
                    }
                    .subscribe({
                        p: Page? ->
                        p?.items?.let {
                            if (it.isEmpty()) {
                                view?.showMessage(R.string.message_all_data_loaded)
                            } else view?.showCardList(it)
                        }
                    }, {
                        view?.showMessage(R.string.message_error_loading_page)
                        mPageCount = pageCache
                        view?.hideLoading()
                    })



    }

    fun loadNextPage() {
        mHandler.removeCallbacks(mRunnable)
        val view: CardListFragmentView? = mView.get()
        mPageCount++
        mDataSource
                    .getCards(mPageCount)
                    .doOnSubscribe {
                        view?.showLoading()
                    }
                    .doOnComplete { view?.hideLoading() }
                    .doOnError {
                        view?.showMessage(R.string.message_error_loading_page)
                        mPageCount--
                        view?.hideLoading()
                    }
                    .subscribe({
                        p: Page? ->
                        p?.items?.let {
                            if (it.isEmpty()) {
                                view?.showMessage(R.string.message_all_data_loaded)
                                mIsAllLoaded = true
                                view?.switchLoadingAbility(false)
                                mHandler.postDelayed(mRunnable, EXPIRATION_TIME)
                                mPageCount--
                            } else view?.showNextPage(it)
                        }
                    }, {
                        view?.showMessage(R.string.message_error_loading_page)
                        mPageCount--
                        view?.hideLoading()
                    })
    }

    companion object {
        val EXPIRATION_TIME: Long = 20000 //20 sec in millis
    }
}