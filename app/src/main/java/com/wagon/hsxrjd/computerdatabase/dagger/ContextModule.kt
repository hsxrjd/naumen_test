package com.wagon.hsxrjd.computerdatabase.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by erychkov on 7/17/17.
 */
@Module
class ContextModule(val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }
}