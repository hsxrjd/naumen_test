package com.wagon.hsxrjd.computerdatabase.dagger.container

import com.wagon.hsxrjd.computerdatabase.dagger.scope.ContainerScope
import com.wagon.hsxrjd.computerdatabase.dagger.qualifier.ObservableQ
import com.wagon.hsxrjd.computerdatabase.dagger.qualifier.RemoteApiSource
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.list.interactor.ListInteractor
import com.wagon.hsxrjd.computerdatabase.list.interactor.ListInteractorImpl
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
    fun provideInteractor(@RemoteApiSource source: CardDataSource): ListInteractor {
        return ListInteractorImpl(source, subject)
    }
}