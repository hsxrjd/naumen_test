package com.wagon.hsxrjd.computerdatabase.dagger.strategy

import android.content.Context
import com.wagon.hsxrjd.computerdatabase.model.source.strategy.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by erychkov on 7/19/17.
 */
@Module
class FactoryModule {

    @Provides
    @Singleton
    fun provideStrategyProvider(context: Context): OperationFactory {
        return OperationFactory(context)
    }
}