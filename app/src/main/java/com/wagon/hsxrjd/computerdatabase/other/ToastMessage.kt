package com.wagon.hsxrjd.computerdatabase.other

/**
 * Created by erychkov on 7/18/17.
 */
class ToastMessage() {
    var message: String? = null
    var resource: Int? = null

    constructor(message: String) : this() {
        this.message = message
    }

    constructor(resource: Int) : this() {
        this.resource = resource
    }

}