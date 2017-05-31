package com.wagon.hsxrjd.computerdatabase.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.other.addUnique
import com.wagon.hsxrjd.computerdatabase.model.Card

/**
 * Created by hsxrjd on 23.05.17.
 */
class CardRecyclerViewAdapter : RecyclerView.Adapter<CardRecyclerViewAdapter.BaseViewHolder>(), Loading {

    private var mCardList: MutableList<Card> = mutableListOf()
    private var loading: Boolean = false

    interface OnItemClickListener {
        fun onItemClick(card: Card)
    }

    private lateinit var onItemClickListener: OnItemClickListener

    override fun showLoading() {
        loading = true
    }

    override fun hideLoading() {
        loading = false
    }

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
        mCardList.addUnique(cardList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder?, position: Int) {

        when (viewHolder) {
            is CardViewHolder? -> {
                val card: Card = mCardList[position]
                viewHolder?.title?.text = card.name

                card.company?.let {
                    viewHolder?.company?.text = it.name
                }
                viewHolder?.company?.visibility =
                        if (card.company == null)
                            View.GONE
                        else
                            View.VISIBLE
                viewHolder?.itemView?.setOnClickListener { onItemClickListener.onItemClick(card) }
            }
            is LoaderViewHolder? -> {
                viewHolder?.progressBar?.visibility = if (loading) View.VISIBLE else View.GONE
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup?, viewType: Int): BaseViewHolder {
        val v: View = LayoutInflater
                .from(viewGroup?.context)
                .inflate(viewType, viewGroup, false)
        return if (viewType == R.layout.card_list_item) CardViewHolder(v) else LoaderViewHolder(v)
    }

    override fun getItemViewType(position: Int): Int {
        return mCardList.getOrNull(position)?.let { R.layout.card_list_item } ?: R.layout.loading_list_item
    }

    override fun getItemCount(): Int {
        return if (mCardList.isEmpty()) 0 else mCardList.size + 1
    }

    open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class CardViewHolder(itemView: View) : BaseViewHolder(itemView) {
        @BindView(R.id.card_title) lateinit var title: TextView
        @BindView(R.id.card_company_name) lateinit var company: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    class LoaderViewHolder(itemView: View) : BaseViewHolder(itemView) {

        @BindView(R.id.progress_bar_item) lateinit var progressBar: ProgressBar

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}