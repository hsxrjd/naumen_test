package com.wagon.hsxrjd.computerdatabase.dagger.card

import com.wagon.hsxrjd.computerdatabase.adapter.RecyclerAdapterFactory
import com.wagon.hsxrjd.computerdatabase.dagger.scope.CardScope
import com.wagon.hsxrjd.computerdatabase.model.source.strategy.OperationFactory
import com.wagon.hsxrjd.computerdatabase.module.card.Interactor.CardInteractor
import com.wagon.hsxrjd.computerdatabase.module.card.Interactor.CardInteractorImpl
import com.wagon.hsxrjd.computerdatabase.module.card.adapter.CardRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.module.card.presenter.CardPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by erychkov on 7/21/17.
 */
@Module
class CardModule {

    @Provides
    @CardScope
    fun provideAdapter(factory: RecyclerAdapterFactory, presenter: CardPresenter): CardRecyclerViewAdapter {
        return CardRecyclerViewAdapter(factory, presenter)
    }

    @CardScope
    @Provides
    fun provideCardInteractor(factory: OperationFactory): CardInteractor {
        return CardInteractorImpl(factory)
    }

    @CardScope
    @Provides
    fun provideCardPresenter(interactor: CardInteractor): CardPresenter {
        return CardPresenter(interactor)
    }
}