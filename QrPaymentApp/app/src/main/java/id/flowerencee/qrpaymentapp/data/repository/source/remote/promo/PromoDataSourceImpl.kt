package id.flowerencee.qrpaymentapp.data.repository.source.remote.promo

import id.flowerencee.qrpaymentapp.BuildConfig
import id.flowerencee.qrpaymentapp.data.model.Constant
import id.flowerencee.qrpaymentapp.data.model.response.failed.StatusResponse
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoListResponse
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoItem
import id.flowerencee.qrpaymentapp.data.networking.KtorService
import id.flowerencee.qrpaymentapp.data.networking.MappingFailedResponse
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class PromoDataSourceImpl(
    private val ktorService: KtorService
) : PromoDataSource {
    var status: Flow<StatusResponse> = flowOf()
    override suspend fun getPromo(): Flow<List<PromoItem>> {
        val request = HttpRequestBuilder().apply {
            url { path(Constant.ENDPOINT.GET_PROMO_LIST) }
            headers.append(Constant.HEADER.AUTHORIZATION, BuildConfig.BEARER_TOKEN)
            build()
        }
        val raw = ktorService.callGetHttp(request)
        var response: List<PromoItem>? = null
        when (raw != null) {
            true -> {
                raw.let {
                    try {
                        when (it.status.isSuccess()) {
                            true -> {
                                response = it.body<List<PromoItem>>()
                                withContext(Dispatchers.Main){
                                    status = flowOf(StatusResponse(success = true))
                                }
                            }
                            else -> withContext(Dispatchers.Main) {
                                status = flowOf(MappingFailedResponse().mappingFailedResponse(it))
                            }
                        }
                    } catch (e: JsonConvertException) {
                        e.printStackTrace()
                        withContext(Dispatchers.Main) {
                            status =
                                flowOf(StatusResponse(e.message.toString(), e.cause.toString()))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        withContext(Dispatchers.Main) {
                            status =
                                flowOf(StatusResponse(e.message.toString(), e.cause.toString()))
                        }
                    }
                }
            }

            false -> {
                withContext(Dispatchers.Main) {
                    status = flowOf(StatusResponse("Failed", "Unknown Error"))
                }
            }
        }
        return flowOf(response ?: PromoListResponse())
    }
}