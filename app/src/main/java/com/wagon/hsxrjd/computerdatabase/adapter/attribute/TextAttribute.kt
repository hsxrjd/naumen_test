package com.wagon.hsxrjd.computerdatabase.adapter.attribute

/**
 * Created by erychkov on 7/20/17.
 */
class TextAttribute(val mTitle: String, val mSubTitle: String?) : Attribute {
    override fun getTitle(): String {
        return mTitle
    }

    override fun getSubTitle(): String? {
        return mSubTitle
    }
}

