package com.wagon.hsxrjd.computerdatabase.dagger

import com.wagon.hsxrjd.computerdatabase.MainActivity
import com.wagon.hsxrjd.computerdatabase.dagger.card.CardComponent
import com.wagon.hsxrjd.computerdatabase.dagger.card.CardModule
import com.wagon.hsxrjd.computerdatabase.dagger.container.ContainerComponent
import com.wagon.hsxrjd.computerdatabase.dagger.container.ListInteractorModule
import com.wagon.hsxrjd.computerdatabase.dagger.source.DataSourceModule
import com.wagon.hsxrjd.computerdatabase.dagger.strategy.FactoryModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by erychkov on 7/13/17.
 */
@Singleton
@Component(
        modules = arrayOf(
                DataSourceModule::class,
                ToastModule::class,
                ContextModule::class,
                FactoryModule::class,
                AdapterFactoryModule::class,
                NavigatorModule::class)
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun plus(cardModule: CardModule): CardComponent
    fun plus(interactorModule: ListInteractorModule): ContainerComponent
}