package id.flowerencee.qrpaymentapp.data.networking

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import id.flowerencee.qrpaymentapp.BuildConfig
import id.flowerencee.qrpaymentapp.data.model.Constant
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.gson.gson
import java.util.concurrent.TimeUnit

interface KtorService {
    suspend fun getClient(): HttpClient

    companion object {
        fun create(context: Context): KtorService {
            return KtorServiceImplementation(
                client = HttpClient(OkHttp) {
                    engine {
                        if (BuildConfig.DEBUG) {
                            val chuckerInterceptor = ChuckerInterceptor.Builder(context)
                                .collector(ChuckerCollector(context))
                                .maxContentLength(250000L)
                                .redactHeaders(emptySet())
                                .alwaysReadResponseBody(false)
                                .build()
                            addInterceptor(chuckerInterceptor)
                        }
                        config {
                            followRedirects(true)
                            readTimeout(Constant.PARAM.READ_TIME_OUT, TimeUnit.SECONDS)
                            connectTimeout(Constant.PARAM.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                            callTimeout(Constant.PARAM.CALL_TIME_OUT, TimeUnit.SECONDS)
                        }
                    }
                    defaultRequest {
                        host = BuildConfig.BASE_URL
                        url {
                            protocol = URLProtocol.HTTPS
                        }
                    }
                    install(Logging) {
                        logger = object : Logger {
                            override fun log(message: String) {
                                DeLog.d("KTOR", message)
                            }

                        }
                        level = LogLevel.ALL
                    }
                    install(ContentNegotiation) {
                        gson {
                            setPrettyPrinting().toString()
                        }
                    }
                }
            )
        }
    }
}