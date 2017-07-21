package com.wagon.hsxrjd.computerdatabase.model.db

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by erychkov on 7/19/17.
 */
@RealmClass
open class SimilarCardsRealm : RealmObject() {
    @PrimaryKey var id: Int = -1
    var similarities: RealmList<CardRealmLight> = RealmList()
}