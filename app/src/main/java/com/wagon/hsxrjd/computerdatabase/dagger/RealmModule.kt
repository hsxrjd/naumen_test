package com.wagon.hsxrjd.computerdatabase.dagger

import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

/**
 * Created by erychkov on 7/17/17.
 */
@Module
class RealmModule {

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }
}