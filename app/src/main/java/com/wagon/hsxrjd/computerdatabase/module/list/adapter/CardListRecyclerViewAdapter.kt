package com.wagon.hsxrjd.computerdatabase.module.list.adapter

import android.view.View
import com.wagon.hsxrjd.computerdatabase.adapter.GenericRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.adapter.RecyclerAdapterFactory
import com.wagon.hsxrjd.computerdatabase.adapter.attribute.CardAttribute
import com.wagon.hsxrjd.computerdatabase.model.net.Card

/**
 * Created by hsxrjd on 05.06.17.com.wagon.hsxrjd.computerdatabase.model.
 */
open class CardListRecyclerViewAdapter(factory: RecyclerAdapterFactory) : GenericRecyclerViewAdapter<CardAttribute>(factory) {

    interface OnItemClickListener {
        fun onItemClick(view: View, card: Card)
    }

    private lateinit var mOnItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = onItemClickListener
    }

}