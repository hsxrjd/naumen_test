package com.wagon.hsxrjd.computerdatabase.dagger.card

import com.wagon.hsxrjd.computerdatabase.dagger.scope.CardScope
import com.wagon.hsxrjd.computerdatabase.card.presenter.CardPresenter
import com.wagon.hsxrjd.computerdatabase.card.Interactor.CardInteractor
import dagger.Module
import dagger.Provides

/**
 * Created by erychkov on 7/13/17.
 */
@Module
class CardPresenterModule {

    @CardScope
    @Provides
    fun provideCardPresenter(interactor: CardInteractor): CardPresenter {
        return CardPresenter(interactor)
    }
}