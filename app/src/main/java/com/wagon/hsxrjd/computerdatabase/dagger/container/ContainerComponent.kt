package com.wagon.hsxrjd.computerdatabase.dagger.container

import com.wagon.hsxrjd.computerdatabase.dagger.list.ListComponent
import com.wagon.hsxrjd.computerdatabase.dagger.list.ListPresenterModule
import com.wagon.hsxrjd.computerdatabase.dagger.paginator.PaginationComponent
import com.wagon.hsxrjd.computerdatabase.dagger.paginator.PaginationModule
import com.wagon.hsxrjd.computerdatabase.dagger.scope.ContainerScope
import dagger.Subcomponent

/**
 * Created by erychkov on 7/17/17.
 */
@Subcomponent(modules = arrayOf(
        InteractorModule::class
))
@ContainerScope
interface ContainerComponent {
    fun plus(paginationModule: PaginationModule): PaginationComponent
    fun plus(listPresenterModule: ListPresenterModule): ListComponent
}