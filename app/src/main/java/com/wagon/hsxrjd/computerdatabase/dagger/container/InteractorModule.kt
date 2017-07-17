package com.wagon.hsxrjd.computerdatabase.dagger.container

import com.wagon.hsxrjd.computerdatabase.dagger.scope.ContainerScope
import com.wagon.hsxrjd.computerdatabase.dagger.scope.ObservableQ
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.presenter.interactor.ListInteractor
import com.wagon.hsxrjd.computerdatabase.presenter.interactor.ListInteractorImpl
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject

/**
 * Created by erychkov on 7/14/17.
 */
@Module
class InteractorModule {
    val subject: ReplaySubject<Page> = ReplaySubject.create<Page>()

    @Provides
    @ContainerScope
    @ObservableQ
    fun provideObservable(): Observable<Page> {
        return subject
    }

    @ContainerScope
    @Provides
    fun provideInteractor(source: CardDataSource): ListInteractor {
        return ListInteractorImpl(source, subject)
    }
}