package com.wagon.hsxrjd.computerdatabase.adapter.attribute

import android.view.View

/**
 * Created by erychkov on 7/20/17.
 */
class ClickableTextAttribute(val title: String, val subTitle: String, listener: View.OnClickListener) : ClickableAttribute(listener) {
}