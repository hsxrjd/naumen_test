package com.wagon.hsxrjd.computerdatabase.presenter

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by hsxrjd on 24.05.17.
 */
object NaumenAPI {
    val api: NaumenRestAPI = Retrofit
            .Builder()
            .baseUrl("http://testwork.nsd.naumen.ru/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NaumenRestAPI::class.java)
}