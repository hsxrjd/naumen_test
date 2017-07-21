package com.wagon.hsxrjd.computerdatabase.model.source.strategy

import io.reactivex.Observable

/**
 * Created by erychkov on 7/19/17.
 */
//private val operation: (Int) -> Observable<T>
interface Operation<T> {
    fun perform(): Observable<T>
//        return operation.invoke()
//    }
}