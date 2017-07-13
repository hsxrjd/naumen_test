package com.wagon.hsxrjd.computerdatabase.dagger.card

import com.wagon.hsxrjd.computerdatabase.dagger.scope.CardScope
import com.wagon.hsxrjd.computerdatabase.presenter.CardPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by erychkov on 7/13/17.
 */
@Module
class CardPresenterModule {

    @CardScope
    @Provides
    fun provideCardPresenter(): CardPresenter{
        return CardPresenter()
    }
}