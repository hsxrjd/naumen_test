package com.wagon.hsxrjd.computerdatabase.model.source

import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.net.Page
import io.reactivex.Observable

/**
 * Created by hsxrjd on 23.05.17.
 */
interface CardDataSource {
    fun getPage(page: Int): Observable<Page>
    fun getCard(id: Int): Observable<Card>
    fun getSimilarTo(id: Int): Observable<List<Card>>
}