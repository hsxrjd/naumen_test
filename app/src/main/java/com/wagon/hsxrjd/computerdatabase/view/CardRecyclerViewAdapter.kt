package com.wagon.hsxrjd.computerdatabase.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.model.Card

/**
 * Created by hsxrjd on 23.05.17.
 */
class CardRecyclerViewAdapter : RecyclerView.Adapter<CardRecyclerViewAdapter.CardViewHolder>() {
    private var mCardList: MutableList<Card> = mutableListOf()

    interface OnItemClickListener {
        fun onItemClick(card: Card)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setCardList(cardList: List<Card>) {
        mCardList.clear()
        mCardList.addAll(cardList)
    }

    fun addCardsToList(cardList: List<Card>) {
        mCardList.addAll(cardList)
    }

    override fun onBindViewHolder(viewHolder: CardViewHolder?, p1: Int) {
        val card: Card = mCardList[p1]
        viewHolder?.title?.text = card.name
        viewHolder?.itemView!!.setOnClickListener { onItemClickListener!!.onItemClick(card) }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup?, p1: Int): CardViewHolder {
        val v: View = LayoutInflater
                .from(viewGroup?.context)
                .inflate(R.layout.card_list_item, viewGroup, false)
        return CardViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mCardList.size
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.card_text_item) internal lateinit var title: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

    }
}