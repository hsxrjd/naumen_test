package com.wagon.hsxrjd.computerdatabase.dagger.container

import com.wagon.hsxrjd.computerdatabase.dagger.qualifier.LocalRealmSource
import com.wagon.hsxrjd.computerdatabase.dagger.scope.ContainerScope
import com.wagon.hsxrjd.computerdatabase.dagger.qualifier.ObservableQ
import com.wagon.hsxrjd.computerdatabase.dagger.qualifier.RemoteApiSource
import com.wagon.hsxrjd.computerdatabase.model.net.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CacheDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.ResultObject
import com.wagon.hsxrjd.computerdatabase.module.list.interactor.ListInteractor
import com.wagon.hsxrjd.computerdatabase.module.list.interactor.ListInteractorImpl
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject

/**
 * Created by erychkov on 7/14/17.
 */
@Module
class ListInteractorModule {
    val subject: ReplaySubject<ResultObject> = ReplaySubject.create<ResultObject>()

    @Provides
    @ContainerScope
    @ObservableQ
    fun provideObservable(): Observable<ResultObject> {
        return subject
    }

    @ContainerScope
    @Provides
    fun provideInteractor(@RemoteApiSource remote: CardDataSource, @LocalRealmSource cache: CacheDataSource): ListInteractor {
        return ListInteractorImpl(remote, cache, subject)
    }
}