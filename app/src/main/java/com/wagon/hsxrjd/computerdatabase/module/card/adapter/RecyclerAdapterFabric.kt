package com.wagon.hsxrjd.computerdatabase.module.card.adapter

import android.view.View

/**
 * Created by erychkov on 7/19/17.
 */
class RecyclerAdapterFabric {

    fun buildHolder(itemView: View): CardRecyclerViewAdapter.BaseViewHolder {
        return CardRecyclerViewAdapter.BaseViewHolder(itemView)
    }
}