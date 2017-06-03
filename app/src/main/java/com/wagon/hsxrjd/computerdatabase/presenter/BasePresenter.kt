package com.wagon.hsxrjd.computerdatabase.presenter

import com.wagon.hsxrjd.computerdatabase.BaseContract
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.view.BaseCardView
import java.lang.ref.WeakReference

/**
 * Created by hsxrjd on 23.05.17.
 */


abstract class BasePresenter<T : BaseCardView> : BaseContract.Presenter<T> {
    protected var mView: WeakReference<T?> = WeakReference(null)
    protected lateinit var mDataSource: WeakReference<CardDataSource>

    override fun setView(view: T) {
        mView = WeakReference(view)
    }

    fun setDataSource(source: CardDataSource) {
        mDataSource = WeakReference(source)
    }

}

