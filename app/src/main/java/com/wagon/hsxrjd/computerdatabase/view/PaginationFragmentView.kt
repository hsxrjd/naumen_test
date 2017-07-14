package com.wagon.hsxrjd.computerdatabase.view

/**
 * Created by erychkov on 7/14/17.
 */
interface PaginationFragmentView : BaseCardView {
    fun showPage(id: Int, total: Int)
}