package com.wagon.hsxrjd.computerdatabase.presenter

import android.util.Log
import com.wagon.hsxrjd.computerdatabase.BaseContract
import com.wagon.hsxrjd.computerdatabase.view.BaseView
import java.lang.ref.WeakReference

/**
 * Created by hsxrjd on 23.05.17.
 */


abstract class BasePresenter<T : BaseView> : BaseContract.Presenter<T> {
    internal var mView: WeakReference<T?> = WeakReference(null)

    override fun setView(view: T) {
        mView = WeakReference(view)
    }

}

