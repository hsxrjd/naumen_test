package com.wagon.hsxrjd.computerdatabase.adapter.attribute

/**
 * Created by erychkov on 7/20/17.
 */
class ImageAttribute (val url:String) : Attribute {
    override fun getSubTitleRes(): Int? {
        return null
    }

    override fun getTitle(): String {
        return url
    }

    override fun getSubTitle(): String? {
        return null
    }
}