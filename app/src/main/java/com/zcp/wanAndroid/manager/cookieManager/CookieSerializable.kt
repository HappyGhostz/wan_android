package com.zcp.wanAndroid.manager.cookieManager

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.zcp.wanAndroid.CookiePreferences
import com.zcp.wanAndroid.Cookies
import java.io.InputStream
import java.io.OutputStream

object CookieSerializable : Serializer<CookiePreferences> {

    override fun readFrom(input: InputStream): CookiePreferences {
        return CookiePreferences.parseFrom(input)
    }

    override fun writeTo(t: CookiePreferences, output: OutputStream) {
        t.writeTo(output)
    }

    override val defaultValue: CookiePreferences
        get() = CookiePreferences.getDefaultInstance()
}


object CookiesSerializable : Serializer<Cookies> {
    override val defaultValue: Cookies
        get() = Cookies.getDefaultInstance()

    override fun readFrom(input: InputStream): Cookies {
        try {
            return Cookies.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    }

    override fun writeTo(t: Cookies, output: OutputStream) {
        t.writeTo(output)
    }

}