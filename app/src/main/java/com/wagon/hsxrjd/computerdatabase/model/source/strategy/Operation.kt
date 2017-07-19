package com.wagon.hsxrjd.computerdatabase.model.source.strategy

import io.reactivex.Observable

/**
 * Created by erychkov on 7/19/17.
 */
class Operation<T> (private val operation: (Int) -> Observable<T>) {
    fun perform(n:Int): Observable<T>{
        return operation.invoke(n)
    }
}