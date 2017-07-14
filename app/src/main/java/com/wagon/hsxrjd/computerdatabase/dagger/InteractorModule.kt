package com.wagon.hsxrjd.computerdatabase.dagger

import com.wagon.hsxrjd.computerdatabase.dagger.scope.ListScope
import com.wagon.hsxrjd.computerdatabase.model.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.presenter.Interactor
import com.wagon.hsxrjd.computerdatabase.presenter.InteractorImpl
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

/**
 * Created by erychkov on 7/14/17.
 */
@Module
class InteractorModule {
    val subject: ReplaySubject<Page> = ReplaySubject.create<Page>()

    @Provides
    @ListScope
    fun provideObservable(): Observable<Page> {
        return subject
    }

    @ListScope
    @Provides
    fun provideInteractor(source: CardDataSource): Interactor {
        return InteractorImpl(source, subject)
    }
}