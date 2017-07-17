package com.wagon.hsxrjd.computerdatabase.dagger.source

import android.content.Context
import com.wagon.hsxrjd.computerdatabase.dagger.qualifier.LocalRealmSource
import com.wagon.hsxrjd.computerdatabase.dagger.qualifier.RemoteApiSource
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.api.RemoteCardDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.api.NaumenApi
import com.wagon.hsxrjd.computerdatabase.model.source.local.RealmCacheCardDataSource
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

/**
 * Created by erychkov on 7/13/17.
 */
@Module
class DataSourceModule {
    @Singleton
    @Provides
    @RemoteApiSource
    fun provideRemoteSource(api: NaumenApi): CardDataSource {
        return RemoteCardDataSource(api)
    }

    @Provides
    @Singleton
    fun provideRealm(context: Context): Realm {
        Realm.init(context)
        return Realm.getDefaultInstance()
    }

    @Provides
    @Singleton
    @LocalRealmSource
    fun provideCacheDataSource(realm: Realm): CardDataSource{
        return RealmCacheCardDataSource(realm)
    }
}