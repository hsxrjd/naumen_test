package com.wagon.hsxrjd.computerdatabase.model.db

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by erychkov on 7/18/17.
 */
@RealmClass
open class PageRealm :RealmObject(){
    var items: RealmList<CardRealm> = RealmList()
    @PrimaryKey
    var page: Int = -1
    var offset: Int = 0
    var total: Int = 0
    var creationTime: Long = 0
}