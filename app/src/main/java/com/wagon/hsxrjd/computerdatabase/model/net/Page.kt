package com.wagon.hsxrjd.computerdatabase.model.net

import io.realm.annotations.PrimaryKey

/**
 * Created by hsxrjd on 23.05.17.
 */
open class Page(
    var items: List<Card>,
    var page: Int,
    var offset: Int,
    var total: Int
    )