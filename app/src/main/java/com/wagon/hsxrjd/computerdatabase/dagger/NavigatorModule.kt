package com.wagon.hsxrjd.computerdatabase.dagger

import com.wagon.hsxrjd.computerdatabase.BaseNavigator
import com.wagon.hsxrjd.computerdatabase.Navigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by erychkov on 7/13/17.
 */
@Module
class NavigatorModule {

    @Provides
    @Singleton
    fun provideNavigator(): Navigator {
        return BaseNavigator()
    }
}