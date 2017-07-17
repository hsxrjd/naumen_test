package com.wagon.hsxrjd.computerdatabase.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by erychkov on 7/17/17.
 */
open class Similar : RealmObject() {
    @PrimaryKey var id: Int = -1
    open var similarities: RealmList<Card> = RealmList()
}
