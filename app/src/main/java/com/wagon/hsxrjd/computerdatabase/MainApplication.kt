package com.wagon.hsxrjd.computerdatabase

import android.app.Application
import android.util.Log
import com.wagon.hsxrjd.computerdatabase.dagger.AppComponent
import com.wagon.hsxrjd.computerdatabase.dagger.DaggerAppComponent
import com.wagon.hsxrjd.computerdatabase.dagger.NavigatorModule
import com.wagon.hsxrjd.computerdatabase.dagger.RealmModule
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
        Realm.init(this)
        val config = RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)
    }

    companion object {
        val appComponent: AppComponent by lazy {
            DaggerAppComponent
                    .builder()
                    .apiModule(ApiModule())
                    .dataSourceModule(DataSourceModule())
                    .navigatorModule(NavigatorModule())
                    .build()
        }
    }
}
