package com.wagon.hsxrjd.computerdatabase.dagger.list

import com.wagon.hsxrjd.computerdatabase.dagger.InteractorModule
import com.wagon.hsxrjd.computerdatabase.dagger.scope.ListScope
import com.wagon.hsxrjd.computerdatabase.fragment.CardListFragment
import dagger.Subcomponent

/**
 * Created by erychkov on 7/13/17.
 */

@ListScope
@Subcomponent(modules = arrayOf(
        InteractorModule::class,

        ListPresenterModule::class
))
interface ListComponent {
    fun inject(fragment: CardListFragment)
}