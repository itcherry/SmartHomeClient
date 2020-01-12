package com.chernysh.smarthome.data.network.retrofit

import com.chernysh.smarthome.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.ConnectException


/**
 * Created by Andrii Chernysh on 2020-01-12
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class HostSelectionInterceptor : Interceptor {
    @Volatile
    private var host: String? = null

    fun setHost(host: String) {
        this.host = HttpUrl.parse(host)?.host() ?: ""
    }

    fun getHost() = host

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val host = this.host

        if(host != null){
            val newUrl: HttpUrl = request.url().newBuilder()
                .host(host)
                .build()

            request = request.newBuilder()
                .url(newUrl)
                .build()
        }

        return try {
            chain.proceed(request)
        } catch (e: ConnectException) {
            setHost(getNeededHost(chain))
            intercept(chain)
        }
    }

    private fun getNeededHost(chain: Interceptor.Chain): String =
        if ("http://${chain.request().url().host()}" == BuildConfig.HOST_LINK_LOCAL) {
            BuildConfig.HOST_LINK_GLOBAL
        } else {
            BuildConfig.HOST_LINK_LOCAL
        } + BuildConfig.PORT_API
}