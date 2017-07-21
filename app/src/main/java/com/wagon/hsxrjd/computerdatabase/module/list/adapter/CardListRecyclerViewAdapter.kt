package com.wagon.hsxrjd.computerdatabase.module.list.adapter

import com.wagon.hsxrjd.computerdatabase.adapter.GenericRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.adapter.RecyclerAdapterFactory
import com.wagon.hsxrjd.computerdatabase.adapter.attribute.Attribute
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.module.card.adapter.ICardRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.module.list.presenter.CardListPresenter

/**
 * Created by hsxrjd on 05.06.17.com.wagon.hsxrjd.computerdatabase.model.
 */
open class CardListRecyclerViewAdapter(factory: RecyclerAdapterFactory, val presenter: CardListPresenter) : GenericRecyclerViewAdapter<Attribute>(factory), ICardRecyclerViewAdapter {

    override fun onItemClick(card: Card) {
        presenter.startCardFragment(card)
    }

    override fun addAttributes(vararg attribute: Attribute) {
        setData(attribute.asList())
    }
}