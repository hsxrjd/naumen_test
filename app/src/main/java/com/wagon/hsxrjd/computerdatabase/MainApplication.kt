package com.wagon.hsxrjd.computerdatabase

import android.app.Application
import com.wagon.hsxrjd.computerdatabase.dagger.AppComponent
import com.wagon.hsxrjd.computerdatabase.dagger.ContextModule
import com.wagon.hsxrjd.computerdatabase.dagger.DaggerAppComponent
import com.wagon.hsxrjd.computerdatabase.dagger.NavigatorModule
import com.wagon.hsxrjd.computerdatabase.dagger.source.ApiModule
import com.wagon.hsxrjd.computerdatabase.dagger.source.DataSourceModule

/**
 * Created by hsxrjd on 24.05.17.
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        contextModule = ContextModule(this)
    }

    companion object {
        lateinit var contextModule: ContextModule
        val appComponent: AppComponent by lazy {
            DaggerAppComponent
                    .builder()
                    .apiModule(ApiModule())
                    .contextModule(contextModule)
                    .dataSourceModule(DataSourceModule())
                    .navigatorModule(NavigatorModule())
                    .build()
        }
    }
}
