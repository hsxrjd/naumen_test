package com.wagon.hsxrjd.computerdatabase.pagin

import com.wagon.hsxrjd.computerdatabase.contract.BaseCardView

/**
 * Created by erychkov on 7/14/17.
 */
interface PaginationFragmentView : BaseCardView {
    fun showPage(id: Int, total: Int)
    fun changeButtonPrevState(state: Boolean)
    fun changeButtonNextState(state: Boolean)
}