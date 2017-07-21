package com.wagon.hsxrjd.computerdatabase.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.adapter.attribute.Attribute
import com.wagon.hsxrjd.computerdatabase.adapter.attribute.CardAttribute
import com.wagon.hsxrjd.computerdatabase.adapter.attribute.ClickableTextAttribute
import com.wagon.hsxrjd.computerdatabase.log.Logger
import com.wagon.hsxrjd.computerdatabase.module.card.CardFragment

/**
 * Created by erychkov on 7/19/17.
 */
class RecyclerAdapterFactory {

    fun buildHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder {
        val v: View = LayoutInflater
                .from(viewGroup.context)
                .inflate(viewType, viewGroup, false)
        when (viewType) {
            R.layout.list_item_textview -> {
                return TextViewHolder(v)
            }
            R.layout.list_item_imageview -> {
                return ImageViewHolder(v, viewGroup.context)
            }
            R.layout.list_item_cardview -> {
                return TextViewHolder(v)
            }
            else -> {
                return BaseViewHolder(v)
            }
        }
    }

    open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bind(attribute: Attribute) {}
    }

    class TextViewHolder(itemView: View) : BaseViewHolder(itemView) {

        fun bind(attribute: Attribute, listener: ClickableItem) {
            super.bind(attribute)
            textViewMain.text = attribute.getTitle()
            attribute.getSubTitle()
                    ?.let {
                        textViewSupport.text = it
                        textViewSupport.visibility = View.VISIBLE
                    }
                    ?: let {
                attribute.getSubTitleRes()
                        ?.let {
                            textViewSupport.setText(it)
                            textViewSupport.visibility = View.VISIBLE
                        }
                        ?: let { textViewSupport.visibility = View.GONE }
            }

            when (attribute) {
                is ClickableTextAttribute -> {
                    textViewMain.setOnClickListener {
                        text ->
                        val textView = (text as TextView)
                        if (textView.maxLines == CardFragment.DESCRIPTION_MAX_LINES_COLLAPSED) {
                            textView.maxLines = Integer.MAX_VALUE
                        } else {
                            textView.maxLines = CardFragment.DESCRIPTION_MAX_LINES_COLLAPSED
                        }
                    }
                }
                is CardAttribute -> {

                    itemView.setOnClickListener {
                        listener.onItemClick(attribute.card)
                    }
                }
            }
        }


        @BindView(R.id.list_item_textview_main) lateinit var textViewMain: TextView
        @BindView(R.id.list_item_textview_support) lateinit var textViewSupport: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    class ImageViewHolder(itemView: View, val context: Context) : BaseViewHolder(itemView) {

        override fun bind(attribute: Attribute) {
            super.bind(attribute)
            Picasso
                    .with(context)
                    .load(attribute.getTitle())
                    .placeholder(R.mipmap.ic_image_placeholder)
                    .into(imageView)
        }

        @BindView(R.id.list_item_imageview) lateinit var imageView: ImageView

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}