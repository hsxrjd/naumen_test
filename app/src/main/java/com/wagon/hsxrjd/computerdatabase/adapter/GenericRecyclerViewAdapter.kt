package com.wagon.hsxrjd.computerdatabase.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.adapter.attribute.*

/**
 * Created by erychkov on 7/20/17.
 */
abstract class GenericRecyclerViewAdapter<T : Attribute>(val factory: RecyclerAdapterFactory) : RecyclerView.Adapter<RecyclerAdapterFactory.BaseViewHolder>(), ClickableItem {
    protected var mContainer: MutableList<T> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterFactory.BaseViewHolder {
        return factory.buildHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterFactory.BaseViewHolder?, position: Int) {
        when (holder) {
            is RecyclerAdapterFactory.TextViewHolder? -> {
                holder?.bind(mContainer[position], this)
            }
            else -> {
                holder?.bind(mContainer[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return mContainer.size
    }

    open fun getData(): List<T> {
        return mContainer
    }

    override fun getItemViewType(position: Int): Int {
        when (mContainer[position]) {
            is TextAttribute, is ClickableTextAttribute -> {
                return R.layout.list_item_textview
            }
            is ImageAttribute -> {
                return R.layout.list_item_imageview
            }
            is CardAttribute -> {
                return R.layout.list_item_cardview
            }
        }
        return 0
    }

    open fun setData(list: List<T>) {
        mContainer.clear()
        mContainer.addAll(list)
        notifyDataSetChanged()
    }
}
