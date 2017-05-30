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

    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun getCardList(): List<Card> {
        return mCardList
    }

    fun setCardList(cardList: List<Card>) {
        mCardList.clear()
        mCardList.addAll(cardList)
        notifyDataSetChanged()
    }

    fun addCardsToList(cardList: List<Card>) {
        mCardList.addAll(cardList)
    }

    override fun onBindViewHolder(viewHolder: CardViewHolder?, p1: Int) {
        val card: Card = mCardList[p1]
        viewHolder?.title?.text = card.name

        card.company?.let {
            viewHolder?.company?.visibility = View.VISIBLE
            viewHolder?.company?.text = it.name
        }
        if (card.company == null){
            viewHolder?.company?.visibility = View.GONE
        }

        viewHolder?.itemView?.setOnClickListener { onItemClickListener.onItemClick(card) }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup?, viewType: Int): CardViewHolder {
        val v: View = LayoutInflater
                .from(viewGroup?.context)
                .inflate(viewType, viewGroup, false)
        return CardViewHolder(v)
    }

    override fun getItemViewType(position: Int): Int {
        return mCardList.getOrNull(position)?.let { R.layout.card_list_item } ?: R.layout.loading_list_item
    }

    override fun getItemCount(): Int {
        return mCardList.size
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.card_title) lateinit var title: TextView
        @BindView(R.id.card_company_name) lateinit var company: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

    }
}