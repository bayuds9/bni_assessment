package id.flowerencee.qrpaymentapp.data.networking

import id.flowerencee.qrpaymentapp.data.model.response.failed.StatusResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

class MappingFailedResponse {
    suspend fun mappingFailedResponse(response: HttpResponse): StatusResponse {
        val failedResponse = response.body<StatusResponse?>()
        return if (failedResponse != null) response.body()
        else StatusResponse(response.requestTime.timestamp.toString(), response.status.description, response.status.value)
    }
}