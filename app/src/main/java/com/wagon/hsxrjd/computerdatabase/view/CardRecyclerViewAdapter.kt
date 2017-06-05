package com.wagon.hsxrjd.computerdatabase.view

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.model.Card
import java.lang.ref.WeakReference

/**
 * Created by hsxrjd on 23.05.17.
 */
class CardRecyclerViewAdapter : RecyclerView.Adapter<CardRecyclerViewAdapter.BaseViewHolder>(), Loading {

    private var mCardList: MutableList<Card?> = mutableListOf()
    private var mLoadVisible: Boolean = true
    private var mProgressBar: WeakReference<Loading?> = WeakReference(null)

    interface OnItemClickListener {
        fun onItemClick(view: View, card: Card)
    }

    private lateinit var onItemClickListener: OnItemClickListener

    fun setLoadItemVisibility(flag: Boolean) {
        when {
            flag && (mCardList.isEmpty() || mCardList.last() != null) -> {
                mCardList.add(null)
                notifyDataSetChanged()
            }

            !flag && mCardList.isNotEmpty() && mCardList.last() == null -> {
                mCardList.removeAt(mCardList.size - 1)
                notifyDataSetChanged()
            }
        }
    }

    override fun showLoading() {
        mProgressBar.get()?.showLoading()
        mLoadVisible = true
    }

    override fun hideLoading() {
        mProgressBar.get()?.hideLoading()
        mLoadVisible = false
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun getCardList(): List<Card> {
        if (mCardList.isNotEmpty() && mCardList.last() == null)
            return mCardList.subList(0, mCardList.size - 1) as List<Card>
        else
            return mCardList as List<Card>
    }

    fun setCardList(cardList: List<Card>) {
        mCardList.clear()
        mCardList.addAll(cardList)
        mCardList.add(null)
        notifyDataSetChanged()
    }

    fun addCardsToList(cardList: List<Card>) {
        val count = mCardList.size - if (mCardList.isNotEmpty() && mCardList.last() == null) 1 else 0
        mCardList.addAll(count, cardList)
        notifyItemRangeInserted(count, cardList.size)
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
                viewHolder?.itemView?.setOnClickListener { onItemClickListener.onItemClick(it, card) }
                ViewCompat.setTransitionName(viewHolder?.itemView, card.id.toString())
            }
            is LoaderViewHolder? -> {
                viewHolder?.itemView?.visibility = if (mLoadVisible) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup?, viewType: Int): BaseViewHolder {
        val v: View = LayoutInflater
                .from(viewGroup?.context)
                .inflate(viewType, viewGroup, false)
        return if (viewType == R.layout.list_item_card) CardViewHolder(v) else {
            val holder = LoaderViewHolder(v)
            mProgressBar = WeakReference(holder)
            return holder
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mCardList[position]?.let { R.layout.list_item_card } ?: R.layout.list_item_loading
    }

    override fun getItemCount(): Int {
        return if (mCardList.size <= 1) 0 else mCardList.size
    }

    open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class CardViewHolder(itemView: View) : BaseViewHolder(itemView) {
        @BindView(R.id.card_title) lateinit var mTitle: TextView
        @BindView(R.id.card_company_name) lateinit var mCompany: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    class LoaderViewHolder(itemView: View) : BaseViewHolder(itemView), Loading {
        override fun showLoading() {
            itemView.visibility = View.VISIBLE
        }

        override fun hideLoading() {
            itemView.visibility = View.GONE
        }

        @BindView(R.id.progress_bar_item) lateinit var mProgressBar: ProgressBar

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}