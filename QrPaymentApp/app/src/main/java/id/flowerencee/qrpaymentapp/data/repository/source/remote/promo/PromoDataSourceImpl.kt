package id.flowerencee.qrpaymentapp.data.repository.source.remote.promo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.flowerencee.qrpaymentapp.BuildConfig
import id.flowerencee.qrpaymentapp.data.model.Constant
import id.flowerencee.qrpaymentapp.data.model.response.failed.StatusResponse
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoListResponse
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoListResponseItem
import id.flowerencee.qrpaymentapp.data.networking.MappingFailedResponse
import id.flowerencee.qrpaymentapp.data.networking.KtorService
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
    private var _status = MutableLiveData<StatusResponse>()
    val status: LiveData<StatusResponse> get() = _status
    override suspend fun getPromo(): Flow<List<PromoListResponseItem>> {
        val request = HttpRequestBuilder().apply {
            url { path(Constant.ENDPOINT.GET_PROMO_LIST) }
            headers.append(Constant.HEADER.AUTHORIZATION, BuildConfig.BEARER_TOKEN)
            build()
        }
        val raw = ktorService.callGetHttp(request)
        var response: List<PromoListResponseItem>? = null
        when (raw != null) {
            true -> {
                raw.let {
                    try {
                        when (it.status.isSuccess()) {
                            true -> response = it.body<List<PromoListResponseItem>>()
                            else -> withContext(Dispatchers.Main) {
                                _status.value = MappingFailedResponse().mappingFailedResponse(it)
                            }
                        }
                    } catch (e: JsonConvertException) {
                        e.printStackTrace()
                        withContext(Dispatchers.Main) {
                            _status.value = StatusResponse(e.message.toString(), e.cause.toString())
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        withContext(Dispatchers.Main) {
                            _status.value = StatusResponse(e.message.toString(), e.cause.toString())
                        }
                    }
                }
            }

            false -> {
                withContext(Dispatchers.Main) {
                    _status.value = StatusResponse("Failed", "Unknown Error")
                }
            }
        }
        return flowOf(response ?: PromoListResponse())
    }
}