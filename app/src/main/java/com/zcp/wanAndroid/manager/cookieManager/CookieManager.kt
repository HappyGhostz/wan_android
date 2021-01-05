package com.zcp.wanAndroid.manager.cookieManager

import okhttp3.Cookie
import okhttp3.HttpUrl

interface CookieManager {

    /**  添加cookie */
    fun add(httpUrl: HttpUrl, cookie: Cookie)

    /** 添加指定httpurl cookie集合 */
    fun add(httpUrl: HttpUrl, cookies: MutableList<Cookie>)

    /** 根据HttpUrl从缓存中读取cookie集合 */
    fun getCookies(httpUrl: HttpUrl):MutableList<Cookie>

    /** 获取全部缓存cookie */
    fun getCookies():MutableList<Cookie>

    /**  移除指定httpurl cookie集合 */
    fun removeCookie(httpUrl: HttpUrl, cookie: Cookie):Boolean

    /** 移除所有cookie */
    fun removeAllCookie():Boolean
}