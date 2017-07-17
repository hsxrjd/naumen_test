package com.wagon.hsxrjd.computerdatabase.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by hsxrjd on 23.05.17.
 */
open class Page() : RealmObject() {
    var items: RealmList<Card> = RealmList()
    @PrimaryKey var page: Int = -1
    var offset: Int = 0
    var total: Int = 0

    constructor(
            eitems: RealmList<Card>,
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