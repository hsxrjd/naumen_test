package com.wagon.hsxrjd.computerdatabase.module.card.adapter

import com.wagon.hsxrjd.computerdatabase.adapter.GenericRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.adapter.RecyclerAdapterFactory
import com.wagon.hsxrjd.computerdatabase.adapter.attribute.Attribute
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.module.card.presenter.CardPresenter

/**
 * Created by erychkov on 7/20/17.
 */
class CardRecyclerViewAdapter(factory: RecyclerAdapterFactory, val presenter: CardPresenter) : GenericRecyclerViewAdapter<Attribute>(factory), ICardRecyclerViewAdapter {

    override fun onItemClick(card: Card) {
        presenter.startCardFragment(card)
    }

    override fun addAttributes(vararg attribute: Attribute) {
        val list = getData().toMutableList()
        list.addAll(attribute)
        setData(list)
    }
}