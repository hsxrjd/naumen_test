package com.wagon.hsxrjd.computerdatabase.model

import io.realm.CompanyRealmProxy
import io.realm.RealmObject
import org.parceler.Parcel

/**
 * Created by hsxrjd on 23.05.17.
 */
@Parcel(implementations = arrayOf(CompanyRealmProxy::class),
        value = Parcel.Serialization.BEAN,
        analyze = arrayOf(Company::class))
open class Company() : RealmObject() {
    var id: Int = 0
    var name: String = ""

    constructor(eid: Int, ename: String) : this() {
        id = eid
        name = ename
    }
}



