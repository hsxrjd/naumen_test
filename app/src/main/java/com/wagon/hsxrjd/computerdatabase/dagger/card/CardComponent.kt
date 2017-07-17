package com.wagon.hsxrjd.computerdatabase.dagger.card

import com.wagon.hsxrjd.computerdatabase.dagger.scope.CardScope
import com.wagon.hsxrjd.computerdatabase.fragment.CardFragment
import dagger.Component
import dagger.Subcomponent

/**
 * Created by erychkov on 7/13/17.
 */
@CardScope
@Subcomponent(modules = arrayOf(
        CardPresenterModule::class,
        CardInteractorModule::class
))
interface CardComponent {
    fun inject(cardFragment: CardFragment)
}