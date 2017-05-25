package com.wagon.hsxrjd.computerdatabase.presenter

import com.wagon.hsxrjd.computerdatabase.BaseContract
import com.wagon.hsxrjd.computerdatabase.view.BaseView

/**
 * Created by hsxrjd on 23.05.17.
 */


abstract class BasePresenter<T: BaseView> : BaseContract.Presenter<T>{
    internal var mView: T? = null

    override fun setView(view: T) {
        mView = view
    }
}

