package com.wagon.hsxrjd.computerdatabase.dagger.paginator

import com.wagon.hsxrjd.computerdatabase.dagger.scope.CustomScope
import com.wagon.hsxrjd.computerdatabase.dagger.scope.ListScope
import com.wagon.hsxrjd.computerdatabase.dagger.scope.ObservableQ
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.presenter.Interactor
import com.wagon.hsxrjd.computerdatabase.presenter.PaginationPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.Observable

/**
 * Created by erychkov on 7/14/17.
 */
@Module
class PaginationModule {
    @ListScope
    @Provides
    fun providePaginationPresenter(source: Observable<Page>, interactor: Interactor): PaginationPresenter {
        return PaginationPresenter(source, interactor)
    }
}