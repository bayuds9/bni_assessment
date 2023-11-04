package id.flowerencee.qrpaymentapp.data.networking

import id.flowerencee.qrpaymentapp.data.model.response.failed.Errors
import id.flowerencee.qrpaymentapp.data.model.response.failed.StatusResponses
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess

class BaseApiClient(val ktorService: KtorService) {

    suspend inline fun <reified T> get(
        httpRequestBuilder: HttpRequestBuilder,
        onError: (StatusResponses) -> Unit,
        onSuccess: (T) -> Unit
    ) {
        try {
            ktorService.getClient().get {
                url {
                    appendPathSegments(httpRequestBuilder.url.pathSegments)
                    parameters.appendAll(httpRequestBuilder.url.parameters.build())
                    headers.appendAll(httpRequestBuilder.headers.build())
                }
            }.let {
                when (it.status.isSuccess()) {
                    true -> {
                        val response = it.body<SuccessResponse<T>>()
                        onSuccess(response.data)
                    }

                    false -> {
                        onError(it.body())
                    }
                }
                DeLog.d("haha", "on try ${it.body<StatusResponses>()}")
            }
        } catch (e: Exception) {
            DeLog.d("haha", "e $e")
            val statusResponse = when (e) {
                is RedirectResponseException -> {
                    e.printStackTrace()
                    e.response.body()
                }

                is ClientRequestException -> {
                    e.printStackTrace()
                    e.response.body()
                }

                is ServerResponseException -> {
                    e.printStackTrace()
                    e.response.body()
                }

                else -> {
                    val errors = Errors(0, e.cause.toString(), e.message.toString())
                    StatusResponses(error = errors)
                }
            }
            onError(statusResponse)
        }
    }
}
