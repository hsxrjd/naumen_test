package com.wagon.hsxrjd.computerdatabase.dagger.list

import com.wagon.hsxrjd.computerdatabase.dagger.scope.ListScope
import com.wagon.hsxrjd.computerdatabase.dagger.scope.ObservableQ
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.presenter.CardListPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.Observable

/**
 * Created by erychkov on 7/13/17.
 */

@Module
class ListPresenterModule {

    @ListScope
    @Provides
    fun providePresenter(@ObservableQ observable: Observable<Page>): CardListPresenter {
        return CardListPresenter(observable)
    }
}