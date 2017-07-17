package com.wagon.hsxrjd.computerdatabase.dagger.source

import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.api.RemoteCardDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.api.NaumenApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by erychkov on 7/13/17.
 */
@Module
class DataSourceModule {
    @Singleton
    @Provides
    fun provideRepository(api: NaumenApi): CardDataSource {
        return RemoteCardDataSource(api)
    }
}