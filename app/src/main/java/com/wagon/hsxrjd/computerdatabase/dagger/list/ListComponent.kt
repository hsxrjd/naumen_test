package com.wagon.hsxrjd.computerdatabase.dagger.list

import com.wagon.hsxrjd.computerdatabase.dagger.card.ListModule
import com.wagon.hsxrjd.computerdatabase.dagger.scope.ListScope
import com.wagon.hsxrjd.computerdatabase.module.list.CardListFragment
import dagger.Subcomponent

/**
 * Created by erychkov on 7/13/17.
 */

@ListScope
@Subcomponent(modules = arrayOf(
        ListModule::class
))
interface ListComponent {
    fun inject(fragment: CardListFragment)
}