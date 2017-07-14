package com.wagon.hsxrjd.computerdatabase.dagger.list

import com.wagon.hsxrjd.computerdatabase.dagger.scope.ListScope
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.presenter.CardListPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by erychkov on 7/13/17.
 */

@Module
class ListPresenterModule {

    @ListScope
    @Provides
    fun providePresenter(dataSource: CardDataSource): CardListPresenter {
        return CardListPresenter(dataSource)
    }
}