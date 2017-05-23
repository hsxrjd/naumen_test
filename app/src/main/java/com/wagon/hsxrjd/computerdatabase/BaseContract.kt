package com.wagon.hsxrjd.computerdatabase

/**
 * Created by hsxrjd on 23.05.17.
 */
interface BaseContract {
    interface View

    interface Presenter<in T: View> {
        fun setView(view: T)
    }
}