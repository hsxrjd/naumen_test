package com.wagon.hsxrjd.computerdatabase.adapter.attribute

/**
 * Created by erychkov on 7/20/17.
 */
class ClickableTextAttribute(val mTitle: String, val mSubTitle: Int) : ClickableAttribute() {

    override fun getSubTitleRes(): Int? {
        return mSubTitle
    }

    override fun getTitle(): String {
        return mTitle
    }

    override fun getSubTitle(): String? {
        return null
    }
}