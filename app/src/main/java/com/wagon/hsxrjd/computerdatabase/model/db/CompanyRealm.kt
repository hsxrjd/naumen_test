package com.wagon.hsxrjd.computerdatabase.model.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by erychkov on 7/18/17.
 */
@RealmClass
open class CompanyRealm : RealmObject() {
    @PrimaryKey var id: Int = -1
    var name: String = ""
}