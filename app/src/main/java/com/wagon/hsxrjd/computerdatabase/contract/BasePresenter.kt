package com.wagon.hsxrjd.computerdatabase.contract

import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.module.card.adapter.ICardRecyclerViewAdapter
import java.lang.ref.WeakReference

/**
 * Created by hsxrjd on 23.05.17.
 */


abstract class BasePresenter<T : BaseCardView> : BaseContract.Presenter<T> {
    protected var mView: WeakReference<T?> = WeakReference(null)
    var mAdapter:ICardRecyclerViewAdapter? = null

    fun unBind(){
        mAdapter =null
    }

    fun bindAdapter(adapter: ICardRecyclerViewAdapter){
        mAdapter = adapter
    }

    override fun setView(view: T) {
        mView = WeakReference(view)
    }

    fun startCardFragment(card: Card){
        mView.get()?.startCardFragment(card)
    }
}

