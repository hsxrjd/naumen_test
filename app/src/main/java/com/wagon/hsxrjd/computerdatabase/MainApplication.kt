package com.wagon.hsxrjd.computerdatabase

import android.app.Application
import com.wagon.hsxrjd.computerdatabase.dagger.*
import com.wagon.hsxrjd.computerdatabase.dagger.source.ApiModule
import com.wagon.hsxrjd.computerdatabase.dagger.source.DataSourceModule
import io.realm.Realm
import io.realm.RealmConfiguration


/**
 * Created by hsxrjd on 24.05.17.
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        contextModule = ContextModule(this)
        Realm.init(this)
        val config = RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)
    }

    companion object {
        lateinit var contextModule: ContextModule

        val appComponent: AppComponent by lazy {
            DaggerAppComponent
                    .builder()
                    .apiModule(ApiModule())
                    .contextModule(contextModule)
                    .toastModule(ToastModule())
                    .dataSourceModule(DataSourceModule())
                    .navigatorModule(NavigatorModule())
                    .build()
        }
    }
}
