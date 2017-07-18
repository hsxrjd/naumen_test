package com.wagon.hsxrjd.computerdatabase.model.net

import io.realm.annotations.PrimaryKey

/**
 * Created by hsxrjd on 23.05.17.
 */
open class Page() {
    var items: List<Card> = listOf()
    var page: Int = -1
    var offset: Int = 0
    var total: Int = 0

    constructor(
            eitems: List<Card>,
            epage: Int,
            eoffset: Int,
            etotal: Int
    ) : this() {
        items = eitems
        page = epage
        offset = eoffset
        total = etotal
    }
}