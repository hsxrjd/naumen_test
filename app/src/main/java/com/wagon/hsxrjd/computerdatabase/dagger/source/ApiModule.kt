package com.wagon.hsxrjd.computerdatabase.dagger.source

import com.wagon.hsxrjd.computerdatabase.model.source.api.NaumenApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by erychkov on 7/13/17.
 */
@Module
class ApiModule {
    @Singleton
    @Provides
    fun provideApi(): NaumenApi {
        return NaumenApi()
    }
}