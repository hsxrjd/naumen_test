package com.wagon.hsxrjd.computerdatabase.model.source

import com.wagon.hsxrjd.computerdatabase.model.net.Page

/**
 * Created by erychkov on 7/18/17.
 */
class ResultObject(
        val state: State,
        val page: Page?,
        val throwable: Throwable?
) {
    constructor(page: Page) : this(State.Success, page, null)
    constructor(throwable: Throwable) : this(State.Failure, null, throwable)

    enum class State {
        Success,
        Failure
    }
}