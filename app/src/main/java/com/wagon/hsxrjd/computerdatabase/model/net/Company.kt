package com.wagon.hsxrjd.computerdatabase.model.net

import org.parceler.Parcel

/**
 * Created by hsxrjd on 23.05.17.
 */
@Parcel(
        value = Parcel.Serialization.BEAN,
        analyze = arrayOf(Company::class))

open class Company() {
    var id: Int = -1
    var name: String = ""

    constructor(eid: Int, ename: String) : this() {
        id = eid
        name = ename
    }
}



