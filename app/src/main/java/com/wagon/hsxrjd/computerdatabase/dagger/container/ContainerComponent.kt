package com.wagon.hsxrjd.computerdatabase.dagger.container

import com.wagon.hsxrjd.computerdatabase.dagger.card.ListModule
import com.wagon.hsxrjd.computerdatabase.dagger.list.ListComponent
import com.wagon.hsxrjd.computerdatabase.dagger.paginator.PaginationComponent
import com.wagon.hsxrjd.computerdatabase.dagger.paginator.PaginationModule
import com.wagon.hsxrjd.computerdatabase.dagger.scope.ContainerScope
import dagger.Subcomponent

/**
 * Created by erychkov on 7/17/17.
 */
@Subcomponent(modules = arrayOf(
        ListInteractorModule::class
))
@ContainerScope
interface ContainerComponent {
    fun plus(paginationModule: PaginationModule): PaginationComponent
    fun plus(listModule: ListModule): ListComponent
}