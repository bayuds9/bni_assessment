package id.flowerencee.qrpaymentapp.data.repository.source.remote.promo

import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoItem
import kotlinx.coroutines.flow.Flow

interface PromoDataSource {
    suspend fun getPromo(): Flow<List<PromoItem>>
}