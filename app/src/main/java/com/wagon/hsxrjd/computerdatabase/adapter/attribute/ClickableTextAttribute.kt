package com.wagon.hsxrjd.computerdatabase.adapter.attribute

import android.view.View

/**
 * Created by erychkov on 7/20/17.
 */
class ClickableTextAttribute(val mTitle: String, val mSubTitle: String, listener: View.OnClickListener) : ClickableAttribute(listener) {
    override fun getTitle(): String {
        return mTitle
    }

    override fun getSubTitle(): String? {
        return mSubTitle
    }
}