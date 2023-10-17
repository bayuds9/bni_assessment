package id.flowerencee.qrpaymentapp.data.repository.source.remote.promo

import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoListResponse
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoListResponseItem
import kotlinx.coroutines.flow.Flow

interface PromoDataSource {
    suspend fun getPromo(): Flow<List<PromoListResponseItem>>
}