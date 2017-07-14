package com.wagon.hsxrjd.computerdatabase.dagger

import com.wagon.hsxrjd.computerdatabase.MainActivity
import com.wagon.hsxrjd.computerdatabase.dagger.card.CardComponent
import com.wagon.hsxrjd.computerdatabase.dagger.card.CardPresenterModule
import com.wagon.hsxrjd.computerdatabase.dagger.list.ListComponent
import com.wagon.hsxrjd.computerdatabase.dagger.list.ListPresenterModule
import com.wagon.hsxrjd.computerdatabase.dagger.paginator.PaginationComponent
import com.wagon.hsxrjd.computerdatabase.dagger.paginator.PaginationModule
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
    fun inject(mainActivity: MainActivity)
    fun plus(cardPresenterModule: CardPresenterModule): CardComponent
    fun plus(paginationModule: PaginationModule): PaginationComponent
    fun plus(listPresenterModule: ListPresenterModule): ListComponent
}