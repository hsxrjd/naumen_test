package com.wagon.hsxrjd.computerdatabase.dagger.container

import com.wagon.hsxrjd.computerdatabase.dagger.scope.ContainerScope
import com.wagon.hsxrjd.computerdatabase.dagger.qualifier.ObservableQ
import com.wagon.hsxrjd.computerdatabase.model.source.ResultObject
import com.wagon.hsxrjd.computerdatabase.model.source.strategy.OperationFactory
import com.wagon.hsxrjd.computerdatabase.module.list.interactor.ListInteractor
import com.wagon.hsxrjd.computerdatabase.module.list.interactor.ListInteractorImpl
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by erychkov on 7/14/17.
 */
@Module
class ListInteractorModule {
    val subject: PublishSubject<ResultObject> = PublishSubject.create<ResultObject>()

    @Provides
    @ContainerScope
    @ObservableQ
    fun provideObservable(): Observable<ResultObject> {
        return subject
    }

    @ContainerScope
    @Provides
    fun provideInteractor(factory: OperationFactory): ListInteractor {
        return ListInteractorImpl(factory, subject)
    }
}