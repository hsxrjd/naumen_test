package com.wagon.hsxrjd.computerdatabase.module.card.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.model.net.Card

/**
 * Created by hsxrjd on 05.06.17.com.wagon.hsxrjd.computerdatabase.model.
 */
open class CardRecyclerViewAdapter : RecyclerView.Adapter<CardRecyclerViewAdapter.BaseViewHolder>() {
    protected var mCardList: MutableList<Card?> = mutableListOf()

    interface OnItemClickListener {
        fun onItemClick(view: View, card: Card)
    }

    protected lateinit var mOnItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup?, viewType: Int): BaseViewHolder {
        val v: View = LayoutInflater
                .from(viewGroup?.context)
                .inflate(viewType, viewGroup, false)
        return CardViewHolder(v)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.list_item_card_cardview
    }

    override fun getItemCount(): Int {
        return mCardList.size
    }

    open fun setCardList(cardList: List<Card>) {
        mCardList.clear()
        mCardList.addAll(cardList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder?, position: Int) {
        when (viewHolder) {
            is CardViewHolder? -> {
                val card: Card = mCardList[position]!!
                viewHolder?.mTitle?.text = card.name
                card.company?.let {
                    viewHolder?.mCompany?.text = it.name
                }
                viewHolder?.mCompany?.visibility =
                        if (card.company == null)
                            View.GONE
                        else
                            View.VISIBLE
                viewHolder?.itemView?.setOnClickListener { mOnItemClickListener.onItemClick(viewHolder.mTitle, card) }
            }
        }
    }

    open fun getCardList(): List<Card> {
        return mCardList as List<Card>
    }

    open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class CardViewHolder(itemView: View) : BaseViewHolder(itemView) {
        @BindView(R.id.card_title_cardview) lateinit var mTitle: TextView
        @BindView(R.id.card_company_name_cardview) lateinit var mCompany: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}