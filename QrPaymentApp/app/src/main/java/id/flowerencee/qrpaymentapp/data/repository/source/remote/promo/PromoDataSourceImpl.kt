package id.flowerencee.qrpaymentapp.data.repository.source.remote.promo

import id.flowerencee.qrpaymentapp.BuildConfig
import id.flowerencee.qrpaymentapp.data.model.Constant
import id.flowerencee.qrpaymentapp.data.model.response.failed.StatusResponses
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoItem
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoListResponse
import id.flowerencee.qrpaymentapp.data.networking.BaseApiClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class PromoDataSourceImpl(
    private val baseApiClient: BaseApiClient
) : PromoDataSource {
    var status: Flow<StatusResponses> = flowOf()

    override suspend fun getPromo(): Flow<List<PromoItem>> {
        var result = listOf<PromoItem>()
        val request = HttpRequestBuilder().apply {
            url { path(Constant.ENDPOINT.GET_PROMO_LIST) }
            headers.append(Constant.HEADER.AUTHORIZATION, BuildConfig.BEARER_TOKEN)
            build()
        }
        baseApiClient.get<PromoListResponse>(
            request,
            onError = { statusResponse ->
                withContext(Dispatchers.Main) {
                    status = flowOf(statusResponse)
                }
            },
            onSuccess = { data ->
                result = data.list
            }
        )
        return flowOf(result)
    }

}