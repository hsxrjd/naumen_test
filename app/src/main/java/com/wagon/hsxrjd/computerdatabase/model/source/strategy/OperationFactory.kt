package com.wagon.hsxrjd.computerdatabase.model.source.strategy

import android.content.Context
import android.net.ConnectivityManager
import com.wagon.hsxrjd.computerdatabase.log.Logger
import com.wagon.hsxrjd.computerdatabase.model.net.Card
import com.wagon.hsxrjd.computerdatabase.model.net.Page
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

    fun buildFetchCardOperation(): Operation<Card> {
        if (hasActiveInternetConnection()) {
            return Operation({ int: Int ->
                remoteSource.getCard(int).doOnNext { realmSource.storeCard(it) }
            })
        } else {
            return Operation({ int: Int -> realmSource.getDirtyCard(int) })
        }
    }

    fun buildFetchPageOperation(): Operation<Page> {
        if (hasActiveInternetConnection()) {
            return Operation({ int: Int ->
                remoteSource.getPage(int).doOnNext { realmSource.storePage(it) }
            })
        } else {
            return Operation({ int: Int -> realmSource.getDirtyPage(int) })
        }
    }

    fun buildFetchSimilarOperation(): Operation<List<Card>> {
        if (hasActiveInternetConnection()) {
            return Operation({ int: Int ->
                remoteSource.getSimilarTo(int).doOnNext { realmSource.attachSimilaritiesTo(it, int) }
            })
        } else {
            return Operation({ int: Int -> realmSource.getDirtySimilarTo(int) })
        }
    }
}