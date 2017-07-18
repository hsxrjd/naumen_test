package com.wagon.hsxrjd.computerdatabase.model.source

import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.net.Page
import io.reactivex.Observable

/**
 * Created by erychkov on 7/18/17.
 */
interface CacheDataSource : CardDataSource {
    fun getDirtyCards(page: Int): Observable<Page?>
    fun getDirtyCard(id: Int): Observable<Card>
    fun getDirtySimilarTo(id: Int): Observable<List<Card>>
}