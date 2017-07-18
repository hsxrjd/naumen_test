package com.wagon.hsxrjd.computerdatabase.model.source.api

import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.net.Page
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by hsxrjd on 23.05.17.
 */

interface NaumenRestApi {

    @GET("/rest/computers")
    fun getPage(@Query("p") page: Int): Observable<Page?>

    @GET("/rest/computers/{id}")
    fun getCard(@Path("id") id: Int): Observable<Card>

    @GET("/rest/computers/{id}/similar")
    fun getSimilarTo(@Path("id") id: Int): Observable<List<Card>>
}