package com.wagon.hsxrjd.computerdatabase.dagger

import com.wagon.hsxrjd.computerdatabase.adapter.RecyclerAdapterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by erychkov on 7/20/17.
 */
@Module
class AdapterFactoryModule {

    @Provides
    @Singleton
    fun provideAdapterFactory(): RecyclerAdapterFactory {
        return RecyclerAdapterFactory()
    }
}