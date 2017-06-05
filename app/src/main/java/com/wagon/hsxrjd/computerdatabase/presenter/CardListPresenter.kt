package com.wagon.hsxrjd.computerdatabase.presenter

import android.os.Handler
import android.util.Log
import android.view.View
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.view.CardListFragmentView

/**
 * Created by hsxrjd on 24.05.17.
 */
class CardListPresenter private constructor() : BasePresenter<CardListFragmentView>() {

    private var mPageCount: Int = 0
//    private var mLastTimeUpdated: Long = 0
//    private var mShouldUpdate: Boolean = true
    private var mIsAllLoaded: Boolean = false

    private val mHandler: Handler = Handler()

    private val mRunnable: Runnable = Runnable {
        Log.d("UPD", "UPDAAATABLE")
        mIsAllLoaded = false
        mView.get()?.switchLoadingAbility(true)
    }

    fun start() {
        loadPage(0)
//        mHandler.postDelayed(mRunnable, 10000)
    }

    fun isAllLoaded(): Boolean {
        return mIsAllLoaded
    }
/*
    private fun isUpdatable(): Boolean {
        if (!mShouldUpdate) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - mLastTimeUpdated > EXPIRATION_TIME) {
                mShouldUpdate = true
                mIsAllLoaded = false
                mLastTimeUpdated = currentTime
                return true
            } else {
                Log.d("DEBUG", "not updatable, time $currentTime - $mLastTimeUpdated")
                return false
            }
        }
        return true
    }*/


    fun loadPage(page: Int) {
        val view: CardListFragmentView? = mView.get()
        mDataSource.get()?.let {
            it
                    .getCards(page)
                    .doOnSubscribe {
                        view?.showLoading()
                    }
                    .doOnComplete { view?.hideLoading() }
                    .doOnError { view?.showMessage("Error loading data on page $page") }
                    .subscribe { p: Page? ->
                        p?.items?.let {
                            if (it.isEmpty()) {
                                view?.showMessage("No data available")
                            } else view?.showCardList(it)
                        }
                    }
        }

    }

    fun loadNextPage() {
        mHandler.removeCallbacks(mRunnable)
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
                                view?.showMessage("All cards loaded")
                                mIsAllLoaded = true
                                view?.switchLoadingAbility(false)
                                mHandler.postDelayed(mRunnable, EXPIRATION_TIME)
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
        val EXPIRATION_TIME: Long = 20000 //20 sec in millis
        val instance: CardListPresenter by lazy { Holder.mInstance }
    }
}