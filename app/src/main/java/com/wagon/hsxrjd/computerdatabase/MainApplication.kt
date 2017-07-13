package com.wagon.hsxrjd.computerdatabase

import android.app.Application
import com.wagon.hsxrjd.computerdatabase.dagger.ApiModule
import com.wagon.hsxrjd.computerdatabase.dagger.AppComponent
import com.wagon.hsxrjd.computerdatabase.dagger.DaggerAppComponent
import com.wagon.hsxrjd.computerdatabase.dagger.DataSourceModule
import com.wagon.hsxrjd.computerdatabase.dagger.card.CardComponent
import com.wagon.hsxrjd.computerdatabase.dagger.card.CardPresenterModule
import com.wagon.hsxrjd.computerdatabase.dagger.card.DaggerCardComponent
import com.wagon.hsxrjd.computerdatabase.dagger.list.DaggerListComponent
import com.wagon.hsxrjd.computerdatabase.dagger.list.ListComponent
import com.wagon.hsxrjd.computerdatabase.dagger.list.ListPresenterModule

/**
 * Created by hsxrjd on 24.05.17.
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        val dataSourceComponent: AppComponent by lazy {
            DaggerAppComponent
                    .builder()
                    .apiModule(ApiModule())
                    .dataSourceModule(DataSourceModule())
                    .build()
        }


        val cardComponent: CardComponent by lazy {
            DaggerCardComponent
                    .builder()
                    .cardPresenterModule(CardPresenterModule())
                    .build()
        }

        val listComponent: ListComponent by lazy {
            DaggerListComponent
                    .builder()
                    .listPresenterModule(ListPresenterModule())
                    .build()
        }


    }
}
