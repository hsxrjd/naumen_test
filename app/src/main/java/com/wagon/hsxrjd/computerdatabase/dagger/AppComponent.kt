package com.wagon.hsxrjd.computerdatabase.dagger

import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.presenter.CardListPresenter
import com.wagon.hsxrjd.computerdatabase.presenter.CardPresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by erychkov on 7/13/17.
 */
@Singleton
@Component(modules = arrayOf(
        DataSourceModule::class,
        ApiModule::class,
        NavigatorModule::class
))
interface AppComponent {
    fun inject(cardListPresenter: CardListPresenter)
    fun inject(cardPresenter: CardPresenter)
}