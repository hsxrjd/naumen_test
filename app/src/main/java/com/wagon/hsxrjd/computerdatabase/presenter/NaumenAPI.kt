package com.wagon.hsxrjd.computerdatabase.presenter

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by hsxrjd on 24.05.17.
 */
object NaumenAPI {
    val api: NaumenRestAPI = Retrofit
            .Builder()
            .baseUrl("http://testwork.nsd.naumen.ru/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NaumenRestAPI::class.java)
}