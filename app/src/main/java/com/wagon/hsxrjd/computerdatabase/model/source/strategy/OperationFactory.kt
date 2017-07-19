package com.wagon.hsxrjd.computerdatabase.model.source.strategy

import android.content.Context
import android.net.ConnectivityManager
import com.wagon.hsxrjd.computerdatabase.log.Logger
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.net.Page
import com.wagon.hsxrjd.computerdatabase.model.source.CacheDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.CardDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.api.NaumenApi
import com.wagon.hsxrjd.computerdatabase.model.source.api.RemoteCardDataSource
import com.wagon.hsxrjd.computerdatabase.model.source.local.RealmCacheCardDataSource
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by erychkov on 7/19/17.
 */
class OperationFactory(val context: Context) {
    val realmSource = RealmCacheCardDataSource()
    val remoteSource = RemoteCardDataSource(NaumenApi())

    private fun isNetworkAvailable(): Boolean {
        val activeNetworkInfo = (context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun hasActiveInternetConnection(): Boolean {
        return isNetworkAvailable()
        if (isNetworkAvailable()) {
            try {
                val urlConnection = URL("http://www.google.com").openConnection() as HttpURLConnection
                urlConnection.setRequestProperty("User-Agent", "Test")
                urlConnection.setRequestProperty("Connection", "close")
                urlConnection.connectTimeout = 1500
                urlConnection.connect()
                return urlConnection.responseCode == 200
            } catch (e: IOException) {
                Logger.logger.debug("Exception", e.localizedMessage)
            }
        }
        return false
    }

    fun buildFetchCardOperation(int: Int): Operation<Card> {
        if (hasActiveInternetConnection()) {
            return RemoteOperation(
                    realmSource,
                    remoteSource,
                    { local: CacheDataSource, remote: CardDataSource ->
                        remote.getCard(int).doOnNext { local.storeCard(it) }
                    })
        } else {
            return LocalOperation(realmSource, { source: CardDataSource -> source.getCard(int) })
        }
    }

    fun buildFetchPageOperation(int: Int): Operation<Page> {
        if (hasActiveInternetConnection()) {
            return RemoteOperation(realmSource, remoteSource, { local, remote ->
                remote.getPage(int).doOnNext { local.storePage(it) }
            })
        } else {
            return LocalOperation(realmSource, { source: CardDataSource -> source.getPage(int) })
        }
    }

    fun buildFetchSimilarOperation(int: Int): Operation<List<Card>> {
        if (hasActiveInternetConnection()) {
            return RemoteOperation(
                    realmSource,
                    remoteSource,
                    { local: CacheDataSource, remote: CardDataSource ->
                        remote.getSimilarTo(int).doOnNext { local.attachSimilaritiesTo(it, int) }
                    })
        } else {
            return LocalOperation(realmSource, { source: CardDataSource -> source.getSimilarTo(int) })
        }
    }
}