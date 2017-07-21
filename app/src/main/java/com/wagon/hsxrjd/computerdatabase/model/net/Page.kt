package com.wagon.hsxrjd.computerdatabase.model.net

/**
 * Created by hsxrjd on 23.05.17.
 */
open class Page{
    var items: List<Card> = listOf()
    var page: Int = -1
    var offset: Int = -1
    var total: Int = 0
}