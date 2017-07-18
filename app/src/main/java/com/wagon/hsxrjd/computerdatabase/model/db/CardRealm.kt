package com.wagon.hsxrjd.computerdatabase.model.db

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by erychkov on 7/18/17.
 */
@RealmClass
open class CardRealm : RealmObject() {
    @PrimaryKey var id: Int = -1
    var name: String = ""
    var imageUrl: String? = null
    var company: CompanyRealm? = null
    var description: String? = null
    var creationTime: Long = 0
    var similarities: RealmList<CardRealm> = RealmList()
}