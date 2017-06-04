package com.wagon.hsxrjd.computerdatabase.presenter

import android.os.Handler
import android.util.Log
import android.view.View
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.view.CardListFragmentView
import io.reactivex.Observable

/**
 * Created by hsxrjd on 24.05.17.
 */
class CardListPresenter private constructor() : BasePresenter<CardListFragmentView>() {
    //todo продумать как ограничивать кол-во загрузок если все на текущий момент загружено

    private var mPageCount: Int = 0
    private var mLastTimeUpdated: Long = 0
    private var mShouldUpdate: Boolean = true

    private val mHandler: Handler = Handler()

    private val mRunnable: Runnable = Runnable {
        if (isUpdatable()) mView.get()?.switchIndicatorState(true)
        Log.d("RUNNED", "$this")
    }

    fun start() {
        loadPage(0)
        mHandler.postDelayed(mRunnable, 10000)
    }


    private fun isUpdatable(): Boolean {
        if (!mShouldUpdate) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - mLastTimeUpdated > EXPIRATION_TIME) {
                mShouldUpdate = true
                mLastTimeUpdated = currentTime
                return true
            } else {
                Log.d("DEBUG", "not updatable, time $currentTime - $mLastTimeUpdated")
                return false
            }
        }
        return true
    }


    fun loadPage(page: Int) {
        val pageCache = mPageCount
        mPageCount = page
        val view: CardListFragmentView? = mView.get()
        mDataSource.get()?.let {
            it
                    .getCards(page)
                    .doOnSubscribe {
                        view?.switchIndicatorState(true)
                        view?.showLoading()
                    }
                    .doOnComplete { view?.hideLoading() }
                    .doOnError { view?.showMessage("Error loading data on page $page") }
                    .subscribe { p: Page? ->
                        p?.items?.let {
                            if (it.isEmpty()) {
                                view?.showMessage("No data available")
                                view?.switchIndicatorState(false)
                                mPageCount = pageCache
                            } else view?.showCardList(it)
                        }
                    }
        }

    }

    fun loadNextPage() {
        if (!mShouldUpdate) return
        val view: CardListFragmentView? = mView.get()
        mPageCount++
        Log.d("DEBUG", "$mPageCount")
        mDataSource.get()?.let {
            it
                    .getCards(mPageCount)
                    .doOnSubscribe {
                        view?.showLoading()
                    }
                    .doOnComplete { view?.hideLoading() }
                    .doOnError {
                        view?.showMessage("Error loading data on page $mPageCount")
                        mPageCount--
                    }
                    .subscribe { p: Page? ->
                        p?.items?.let {
                            if (it.isEmpty()) {
                                mShouldUpdate = false
                                view?.showMessage("All cards loaded")
                                view?.switchIndicatorState(false)
                                mPageCount--
                            } else view?.showNextPage(it)
                        }
                    }
        }
    }

    fun onCardClicked(view: View, card: Card) {
        mView.get()?.cardClicked(view, card)
    }

    private object Holder {
        val mInstance = CardListPresenter()
    }

    companion object {
        val EXPIRATION_TIME: Long = 60000 //1 min in millis
        val instance: CardListPresenter by lazy { Holder.mInstance }
    }
}