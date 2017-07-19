package com.wagon.hsxrjd.computerdatabase.model.source.strategy

import com.wagon.hsxrjd.computerdatabase.model.source.CacheDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import io.reactivex.Observable

/**
 * Created by erychkov on 7/19/17.
 */
class RemoteOperation<T>(val local: CacheDataSource, val remote: CardDataSource, val func: (CacheDataSource, CardDataSource) -> (Observable<T>)) : Operation<T> {
    override fun perform(): Observable<T> = func.invoke(local, remote)
}