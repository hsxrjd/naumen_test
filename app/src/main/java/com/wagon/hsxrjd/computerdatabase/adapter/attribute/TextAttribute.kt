package com.wagon.hsxrjd.computerdatabase.adapter.attribute

/**
 * Created by erychkov on 7/20/17.
 */
class TextAttribute(val mTitle: String, val mSubTitle: String?, val mSubTitleRes: Int?) : Attribute {

    constructor(title: String) : this(title, null, null)
    constructor(title: String, subTitleRes: Int?) : this(title, null, subTitleRes)

    override fun getSubTitleRes(): Int? {
        return mSubTitleRes
    }

    override fun getTitle(): String {
        return mTitle
    }

    override fun getSubTitle(): String? {
        return mSubTitle
    }
}

