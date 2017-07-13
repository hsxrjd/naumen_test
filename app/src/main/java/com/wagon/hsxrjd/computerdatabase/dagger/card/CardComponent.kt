package com.wagon.hsxrjd.computerdatabase.dagger.card

import com.wagon.hsxrjd.computerdatabase.dagger.scope.CardScope
import com.wagon.hsxrjd.computerdatabase.fragment.CardFragment
import dagger.Component

/**
 * Created by erychkov on 7/13/17.
 */
@CardScope
@Component(modules = arrayOf(
        CardPresenterModule::class
))
interface CardComponent {
    fun inject(cardFragment: CardFragment)
}