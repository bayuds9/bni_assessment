package id.flowerencee.qrpaymentapp.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.appendPathSegments

class KtorServiceImplementation(
    private val client: HttpClient
) : KtorService {
    override suspend fun callGetHttp(httpRequestBuilder: HttpRequestBuilder): HttpResponse? {
        return try {
            client.get {
                url {
                    appendPathSegments(httpRequestBuilder.url.pathSegments)
                    parameters.appendAll(httpRequestBuilder.url.parameters.build())
                    headers.appendAll(httpRequestBuilder.headers.build())
                }
            }.body()
        } catch (e: RedirectResponseException) {
            e.printStackTrace()
            e.response.body()
        } catch (e: ClientRequestException) {
            e.printStackTrace()
            e.response.body()
        } catch (e: ServerResponseException) {
            e.printStackTrace()
            e.response.body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}