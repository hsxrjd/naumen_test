package com.wagon.hsxrjd.computerdatabase.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.card.adapter.CardRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.model.Card
import com.wagon.hsxrjd.computerdatabase.contract.Loading
import java.lang.ref.WeakReference

/**
 * Created by hsxrjd on 23.05.17.
 */
class EndlessCardRecyclerViewAdapter : CardRecyclerViewAdapter(), Loading {

    private var mLoadVisible: Boolean = true
    private var mProgressBar: WeakReference<Loading?> = WeakReference(null)
    fun setLoadItemVisibility(flag: Boolean) {
        when {
            flag && (mCardList.isEmpty() || mCardList.last() != null) -> {
//                mCardList.add(null)
                notifyDataSetChanged()
            }

            !flag && mCardList.isNotEmpty() && mCardList.last() == null -> {
//                mCardList.removeAt(mCardList.size - 1)
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

    override fun getCardList(): List<Card> {
        if (mCardList.isNotEmpty() && mCardList.last() == null)
            return mCardList.subList(0, mCardList.size - 1) as List<Card>
        else
            return mCardList as List<Card>
    }

    override fun setCardList(cardList: List<Card>) {
        mCardList.clear()
        mCardList.addAll(cardList)
//        mCardList.add(null)
        notifyDataSetChanged()
    }

    fun addCardsToList(cardList: List<Card>) {
        val count = mCardList.size - if (mCardList.isNotEmpty() && mCardList.last() == null) 1 else 0
        mCardList.addAll(count, cardList)
        notifyItemRangeInserted(count, cardList.size)
    }

    override fun onBindViewHolder(viewHolder: CardRecyclerViewAdapter.BaseViewHolder?, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        when (viewHolder) {
            is LoaderViewHolder? -> {
                viewHolder?.itemView?.visibility = if (mLoadVisible) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup?, viewType: Int): CardRecyclerViewAdapter.BaseViewHolder {
        val v: View = LayoutInflater
                .from(viewGroup?.context)
                .inflate(viewType, viewGroup, false)
        return if (viewType == R.layout.list_item_card_cardview) CardRecyclerViewAdapter.CardViewHolder(v) else {
            val holder = EndlessCardRecyclerViewAdapter.LoaderViewHolder(v)
            mProgressBar = WeakReference(holder)
            return holder
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mCardList[position]?.let { R.layout.list_item_card_cardview } ?: R.layout.list_item_loading
    }

    override fun getItemCount(): Int {
        return if (mCardList.size <= 1) 0 else mCardList.size
    }

    class LoaderViewHolder(itemView: View) : BaseViewHolder(itemView), Loading {
        override fun showLoading() {
            itemView.visibility = View.VISIBLE
        }

        override fun hideLoading() {
            itemView.visibility = View.GONE
        }

        @butterknife.BindView(R.id.progress_bar_item) lateinit var mProgressBar: ProgressBar

        init {
            butterknife.ButterKnife.bind(this, itemView)
        }
    }
}