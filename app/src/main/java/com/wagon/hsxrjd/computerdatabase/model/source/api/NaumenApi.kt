package com.wagon.hsxrjd.computerdatabase.model.source.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by hsxrjd on 24.05.17.
 */
class NaumenApi {
    private val mAdapterFactory = RxJava2CallAdapterFactory
            .createAsync()

    private val mConverterFactory = MoshiConverterFactory
            .create()
            .asLenient()

    private val mHttpClient = OkHttpClient()
            .newBuilder()
            .retryOnConnectionFailure(true)
            .build()

    val mApi: NaumenRestApi = Retrofit
            .Builder()
            .client(mHttpClient)
            .baseUrl("http://testwork.nsd.naumen.ru/")
            .addCallAdapterFactory(mAdapterFactory)
            .addConverterFactory(mConverterFactory)
            .build()
            .create(NaumenRestApi::class.java)

}