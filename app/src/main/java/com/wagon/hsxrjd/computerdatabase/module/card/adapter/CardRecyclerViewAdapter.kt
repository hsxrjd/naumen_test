package com.wagon.hsxrjd.computerdatabase.module.card.adapter

import com.wagon.hsxrjd.computerdatabase.adapter.GenericRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.adapter.RecyclerAdapterFactory
import com.wagon.hsxrjd.computerdatabase.adapter.attr.*

/**
 * Created by erychkov on 7/20/17.
 */
class CardRecyclerViewAdapter : GenericRecyclerViewAdapter<Attribute>() {

    override fun onBindViewHolder(viewHolder: RecyclerAdapterFactory.BaseViewHolder?, position: Int) {
        val attr = mContainer[position]
        viewHolder?.bind(attr)
        when (attr) {
            is TextAttribute -> {
                viewHolder?.bind(attr.title, attr.subTitle)
            }
            is ImageAttribute -> {
                viewHolder?.bind(attr.url, null)
            }
            is ClickableTextAttribute -> {
                viewHolder?.bind(attr.title, attr.subTitle)
                (viewHolder as RecyclerAdapterFactory.TextViewHolder).textViewMain.setOnClickListener(attr.listener)
            }
            is CardAttribute -> {
                viewHolder?.bind(attr.card.name, attr.card.company?.name)
                viewHolder?.itemView?.setOnClickListener { view -> attr.listener.onItemClick(view, attr.card) }
            }
        }
    }

}