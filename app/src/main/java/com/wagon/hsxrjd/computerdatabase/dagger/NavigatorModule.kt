package com.wagon.hsxrjd.computerdatabase.dagger

import com.wagon.hsxrjd.computerdatabase.navigator.BaseNavigator
import com.wagon.hsxrjd.computerdatabase.navigator.Navigator
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