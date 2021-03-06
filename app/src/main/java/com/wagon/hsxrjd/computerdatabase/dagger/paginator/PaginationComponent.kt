package com.wagon.hsxrjd.computerdatabase.dagger.paginator

import com.wagon.hsxrjd.computerdatabase.dagger.scope.ListScope
import com.wagon.hsxrjd.computerdatabase.module.pagin.PaginationFragment
import dagger.Subcomponent

/**
 * Created by erychkov on 7/14/17.
 */
@ListScope
@Subcomponent(modules = arrayOf(
        PaginationModule::class
))
interface PaginationComponent {
    fun inject(paginationFragment: PaginationFragment)
}