package com.wagon.hsxrjd.computerdatabase.contract

import java.lang.ref.WeakReference

/**
 * Created by hsxrjd on 23.05.17.
 */


abstract class BasePresenter<T : BaseCardView> : BaseContract.Presenter<T> {
    protected var mView: WeakReference<T?> = WeakReference(null)

    override fun setView(view: T) {
        mView = WeakReference(view)
    }

}

