package com.wagon.hsxrjd.computerdatabase

import android.app.Application
import com.wagon.hsxrjd.computerdatabase.dagger.*
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
                    .adapterFactoryModule(AdapterFactoryModule())
                    .contextModule(contextModule)
                    .toastModule(ToastModule())
                    .navigatorModule(NavigatorModule())
                    .build()
        }
    }
}
