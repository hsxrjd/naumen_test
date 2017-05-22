package com.wagon.hsxrjd.computerdatabase.presenter

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by hsxrjd on 23.05.17.
 */

interface NaumenRestAPI {
    //TODO: доделать апи до нормального вида - добавить типы к методам, после добавления основных типов

    @GET("/rest/computers")
    fun getListOfCards(@Query("p") page: Int)

    @GET("/rest/computers/{id}")
    fun getCard(@Path("id") id: Int)

    @GET("/rest/computers/{id}/similar")
    fun getSimilarTo(@Path("id") id: Int)
}