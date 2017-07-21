package com.wagon.hsxrjd.computerdatabase.dagger.card

import com.wagon.hsxrjd.computerdatabase.adapter.RecyclerAdapterFactory
import com.wagon.hsxrjd.computerdatabase.dagger.qualifier.ObservableQ
import com.wagon.hsxrjd.computerdatabase.dagger.scope.ListScope
import com.wagon.hsxrjd.computerdatabase.model.source.ResultObject
import com.wagon.hsxrjd.computerdatabase.module.card.adapter.CardRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.module.list.adapter.CardListRecyclerViewAdapter
import com.wagon.hsxrjd.computerdatabase.module.list.presenter.CardListPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.Observable

/**
 * Created by erychkov on 7/21/17.
 */
@Module
class ListModule {

    @Provides
    @ListScope
    fun provideAdapter(factory: RecyclerAdapterFactory, presenter: CardListPresenter): CardListRecyclerViewAdapter {
        return CardListRecyclerViewAdapter(factory, presenter)
    }

    @ListScope
    @Provides
    fun providePresenter(@ObservableQ observable: Observable<ResultObject>): CardListPresenter {
        return CardListPresenter(observable)
    }
}