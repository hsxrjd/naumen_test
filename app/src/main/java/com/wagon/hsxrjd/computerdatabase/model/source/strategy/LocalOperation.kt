package com.wagon.hsxrjd.computerdatabase.model.source.strategy

import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import io.reactivex.Observable

/**
 * Created by erychkov on 7/19/17.
 */
class LocalOperation<T>(val dataSource: CardDataSource, val func: (CardDataSource) -> (Observable<T>)) : Operation<T> {
    override fun perform(): Observable<T> = func.invoke(dataSource)
}