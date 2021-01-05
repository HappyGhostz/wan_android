package com.zcp.wanAndroid.manager

import com.zcp.wanAndroid.manager.cookieManager.CookieManager
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.concurrent.ConcurrentHashMap

const val HOST_NAME_PREFIX = "host_"
const val COOKIE_NAME_PREFIX = "cookie_"

class CookieJarImpl : CookieJar, CookieManager {


    lateinit var cookieManager: CookieManager

    private lateinit var cookieStore: HashMap<String, ConcurrentHashMap<String, Cookie>>

    constructor(CookieManager: CookieManager) {
        this.cookieManager = CookieManager
        cookieStore = HashMap<String, ConcurrentHashMap<String, Cookie>>()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        cookies.forEach {
            if (!isCookieExpired(it)) {
                add(url, it)
            }
        }
        cookieManager.add(url,cookies)
    }

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        return if(getCookies(url).isNotEmpty()){
            getCookies(url)
        }else{
            cookieManager.getCookies()
        }
    }

    private fun isCookieExpired(cookie: Cookie): Boolean =
        cookie.expiresAt() < System.currentTimeMillis()

    override fun add(httpUrl: HttpUrl, cookie: Cookie) {
        if (!cookie.persistent()) {
            return
        }

        val nameKey = cookieName(cookie)
        val hostKey = hostName(httpUrl)
        if (!cookieStore.containsKey(hostKey)) {
            cookieStore[hostKey] = ConcurrentHashMap()
        }
        cookieStore[hostKey]?.set(nameKey, cookie)
    }

    override fun add(httpUrl: HttpUrl, cookies: MutableList<Cookie>) {
        cookies.forEach {
            if (!isCookieExpired(it)) {
                add(httpUrl, it)
            }
        }
    }

    override fun getCookies(httpUrl: HttpUrl): MutableList<Cookie> {
        return get(hostName(httpUrl))
    }

    override fun getCookies(): MutableList<Cookie> {
        var cookies = mutableListOf<Cookie>()
        cookieStore.keys.forEach {
            cookies.addAll(get(it))
        }
        return cookies
    }

    override fun removeCookie(httpUrl: HttpUrl, cookie: Cookie): Boolean {
        return remove(hostName(httpUrl), cookie)
    }

    override fun removeAllCookie(): Boolean {
        cookieStore.clear()
        return true
    }

    /** 获取cookie集合 */
    private fun get(hostKey: String): MutableList<Cookie> {
        var resultCookies = mutableListOf<Cookie>()

        if (this.cookieStore.containsKey(hostKey)) {
            val cookies = cookieStore[hostKey]?.values
            cookies?.forEach {
                if (isCookieExpired(it)) {
                    remove(hostKey, it)
                } else {
                    resultCookies.add(it)
                }
            }
        }
        return resultCookies
    }

    /** 从缓存中移除cookie */
    private fun remove(hostKey: String, cookie: Cookie): Boolean {
        val name = cookieName(cookie)
        if (cookieStore.containsKey(hostKey) && cookieStore[hostKey]!!.containsKey(name)) {
            // 从内存中移除httpUrl对应的cookie
            cookieStore[hostKey]!!.remove(name)
            return true
        }
        return false
    }

    private fun hostName(httpUrl: HttpUrl): String {
        return if (httpUrl.host().startsWith(HOST_NAME_PREFIX)) {
            httpUrl.host()
        } else {
            HOST_NAME_PREFIX + httpUrl.host()
        }
    }

    private fun cookieName(cookie: Cookie): String {
        return cookie.name() + cookie.domain()
    }

}