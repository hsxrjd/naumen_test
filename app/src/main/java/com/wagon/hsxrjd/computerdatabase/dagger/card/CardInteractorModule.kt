package com.wagon.hsxrjd.computerdatabase.dagger.card

import com.wagon.hsxrjd.computerdatabase.dagger.scope.CardScope
import com.wagon.hsxrjd.computerdatabase.model.source.strategy.OperationFactory
import com.wagon.hsxrjd.computerdatabase.module.card.Interactor.CardInteractor
import com.wagon.hsxrjd.computerdatabase.module.card.Interactor.CardInteractorImpl
import dagger.Module
import dagger.Provides

/**
 * Created by erychkov on 7/17/17.
 */
@Module
class CardInteractorModule {

    @CardScope
    @Provides
    fun provideCardInteractor(factory: OperationFactory): CardInteractor {
        return CardInteractorImpl(factory)
    }
}