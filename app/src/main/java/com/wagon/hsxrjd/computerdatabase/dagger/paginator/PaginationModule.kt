package com.wagon.hsxrjd.computerdatabase.dagger.paginator

import com.wagon.hsxrjd.computerdatabase.dagger.scope.ListScope
import com.wagon.hsxrjd.computerdatabase.dagger.scope.ObservableQ
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.list.interactor.ListInteractor
import com.wagon.hsxrjd.computerdatabase.pagin.presenter.PaginationPresenter
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
    fun providePaginationPresenter(@ObservableQ source: Observable<Page>, interactor: ListInteractor): PaginationPresenter {
        return PaginationPresenter(source, interactor)
    }
}