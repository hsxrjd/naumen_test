package com.wagon.hsxrjd.computerdatabase.model.source

import com.wagon.hsxrjd.computerdatabase.adapter.attribute.Attribute
import com.wagon.hsxrjd.computerdatabase.adapter.attribute.CardAttribute
import com.wagon.hsxrjd.computerdatabase.model.net.Page

/**
 * Created by erychkov on 7/18/17.
 */
class ResultObject(
        val state: State,
        val items: List<Attribute>?,
        val currentPage: Int?,
        val totalPageCount: Int?,
        val throwable: Throwable?
) {
    constructor(page: Page) : this(State.Success, page.items.map { card -> CardAttribute(card) }, page.page, page.total, null)
    constructor(throwable: Throwable) : this(State.Failure, null, null, null, throwable)

    enum class State {
        Success,
        Failure
    }
}