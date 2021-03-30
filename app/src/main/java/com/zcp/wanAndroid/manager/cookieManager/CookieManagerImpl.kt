package com.zcp.wanAndroid.manager.cookieManager

import androidx.datastore.core.DataStore
import com.zcp.wanAndroid.CookiePreferences
import com.zcp.wanAndroid.Cookies
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Cookie
import okhttp3.HttpUrl
import java.io.IOException

class CookieManagerImpl : CookieManager {

    var dataStore: DataStore<Cookies>


    constructor(dataStore: DataStore<Cookies>) {
        this.dataStore = dataStore
    }

    override fun add(httpUrl: HttpUrl, cookie: Cookie) {
    }

    override fun add(httpUrl: HttpUrl, cookies: MutableList<Cookie>) {
        var cookiePreferences: MutableList<CookiePreferences> = mutableListOf()
        cookies.forEach {
            if (!isCookieExpired(it)) {
                add(httpUrl, it)
                cookiePreferences.add(changeCookieToPreferences(it))
            }
        }
        runBlocking {
            dataStore.updateData { cookies ->
                cookies.toBuilder().addAllCookiePreferences(cookiePreferences).build()
            }
        }
    }

    override fun getCookies(httpUrl: HttpUrl): MutableList<Cookie> {
        return mutableListOf()
    }

    override fun getCookies(): MutableList<Cookie> {
        val cookies = mutableListOf<Cookie>()
        val removeCookies = mutableMapOf<Int, Cookie>()
        var cookiePreferencesList = dataStore.data.catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(Cookies.getDefaultInstance())
            } else {
                throw exception
            }
        }.map { it ->
            it.cookiePreferencesList.forEachIndexed { index, cookiePreferences ->
                val cookie = changePreferencesToCookie(cookiePreferences)
                if (!isCookieExpired(cookie)) {
                    cookies.add(cookie)
                } else {
                    removeCookies[index] = cookie
                }
            }
            cookies
        }
        removeCookies.keys.forEach { index ->
            runBlocking {
                dataStore.updateData { cookies ->
                    cookies.toBuilder().removeCookiePreferences(index).build()
                }
            }
        }
        return cookies
    }

    private fun changePreferencesToCookie(it: CookiePreferences): Cookie {
        return Cookie.Builder().name(it.name)
            .domain(it.domain)
            .expiresAt(it.expiresAt)
            .httpOnly()
            .path(it.path)
            .secure()
            .value(it.value)
            .hostOnlyDomain(it.domain)
            .build()
    }

    override fun removeCookie(httpUrl: HttpUrl, cookie: Cookie): Boolean {
        return true
    }

    override fun removeAllCookie(): Boolean {
        runBlocking {
            dataStore.updateData { cookies ->
                cookies.toBuilder().clearCookiePreferences().build()
            }
        }
        return true
    }

    private fun isCookieExpired(cookie: Cookie): Boolean =
        cookie.expiresAt() < System.currentTimeMillis()

    private fun changeCookieToPreferences(cookie: Cookie): CookiePreferences {
        return CookiePreferences.getDefaultInstance().toBuilder()
            .setName(cookie.name())
            .setDomain(cookie.domain())
            .setExpiresAt(cookie.expiresAt())
            .setHostOnly(cookie.hostOnly())
            .setPath(cookie.path())
            .setSecure(cookie.secure())
            .setValue(cookie.value())
            .setPersistent(cookie.persistent())
            .setHttpOnly(cookie.httpOnly())
            .build()
    }
}