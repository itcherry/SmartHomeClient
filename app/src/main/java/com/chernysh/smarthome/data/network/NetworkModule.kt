package com.chernysh.smarthome.data.network

/**
 * Copyright 2020. Andrii Chernysh
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context
import com.chernysh.smarthome.BuildConfig
import com.chernysh.smarthome.R
import com.chernysh.smarthome.data.network.retrofit.AuthorizationHeaderInterceptor
import com.chernysh.smarthome.data.network.retrofit.ConnectivityInterceptor
import com.chernysh.smarthome.data.network.retrofit.HostSelectionInterceptor
import com.chernysh.smarthome.data.network.source.ApiDataSource
import com.chernysh.smarthome.di.qualifier.ApplicationContext
import com.chernysh.smarthome.di.scope.ApplicationScope
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File
import java.security.KeyStore
import java.security.SecureRandom
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/**
 * @author Andrii Chernysh. E-mail: itcherry97@gmail.com
 * Developed by <u>Transcendensoft</u>
 */
@Module
class NetworkModule {

    @Provides
    @ApplicationScope
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.i(message) }
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return loggingInterceptor
    }

    @Provides
    @ApplicationScope
    fun provideConnectivityInterceptor(@ApplicationContext context: Context): ConnectivityInterceptor {
        return ConnectivityInterceptor(context)
    }

    @Provides
    @ApplicationScope
    fun provideHostSelectionInterceptor(): HostSelectionInterceptor {
        return HostSelectionInterceptor()
    }

    @Provides
    @ApplicationScope
    fun provideChuckInterceptor(@ApplicationContext context: Context): ChuckInterceptor {
        return ChuckInterceptor(context)
    }

    @Provides
    @ApplicationScope
    fun provideCacheFile(@ApplicationContext context: Context): File {
        val cacheFile = File(context.cacheDir, "okhttp_cache")
        cacheFile.mkdirs()
        return cacheFile
    }

    @Provides
    @ApplicationScope
    fun provideCache(cacheFile: File): Cache {
        return Cache(cacheFile, CACHE_SIZE.toLong())
    }

    @Provides
    @ApplicationScope
    fun provideKeyStore(@ApplicationContext context: Context):KeyStore {
        // Get the file of our certificate
        var caFileInputStream = context.resources.openRawResource(R.raw.keystore)

        // We're going to put our certificates in a Keystore
        val keyStore = KeyStore.getInstance("PKCS12")
        keyStore.load(caFileInputStream, BuildConfig.SSL_CERT_PASSWORD.toCharArray())

        return keyStore
    }

    @Provides
    @ApplicationScope
    fun provideKeyManagerFactory(keyStore: KeyStore): KeyManagerFactory {
        // Create a KeyManagerFactory with our specific algorithm our our public keys
        // Most of the cases is gonna be "X509"
        val keyManagerFactory = KeyManagerFactory.getInstance("X509")
        keyManagerFactory.init(keyStore, BuildConfig.SSL_CERT_PASSWORD.toCharArray())

        return keyManagerFactory
    }

    @Provides
    @ApplicationScope
    fun provideTrustManager(keyStore: KeyStore): X509TrustManager {
        val trustManagerFactory = TrustManagerFactory.getInstance(
            TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)
        val trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || !(trustManagers[0] is X509TrustManager)) {
            throw IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers))
        }

        return trustManagers[0] as X509TrustManager
    }

    @Provides
    @ApplicationScope
    fun provideSslContext(keyManagerFactory: KeyManagerFactory, trustManager: X509TrustManager): SSLContext {
        // Create a SSL context with the key managers of the KeyManagerFactory
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(keyManagerFactory.keyManagers, arrayOf(trustManager), SecureRandom())

        return sslContext
    }

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: AuthorizationHeaderInterceptor,
        connectivityInterceptor: ConnectivityInterceptor,
        chuckInterceptor: ChuckInterceptor,
        hostSelectionInterceptor: HostSelectionInterceptor,
        sslContext: SSLContext,
        x509TrustManager: X509TrustManager,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(hostSelectionInterceptor)
            .addInterceptor(chuckInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .addInterceptor(connectivityInterceptor)
            //.sslSocketFactory(sslContext.socketFactory, x509TrustManager)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.HOURS)
            .readTimeout(1, TimeUnit.HOURS)
            .cache(cache)
            .build()
    }

    companion object {
        private const val CACHE_SIZE = 10 * 1000 * 1000 //10MB cache
    }
}
