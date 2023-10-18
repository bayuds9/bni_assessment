package id.flowerencee.qrpaymentapp.data.repository.implementation.promo

import id.flowerencee.qrpaymentapp.data.model.response.failed.StatusResponse
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoListResponseItem
import id.flowerencee.qrpaymentapp.data.repository.source.remote.promo.PromoDataSourceImpl
import id.flowerencee.qrpaymentapp.domain.repository.promo.PromoRepository
import kotlinx.coroutines.flow.Flow

class PromoRepositoryImpl(
    private val source: PromoDataSourceImpl
) : PromoRepository {
    override suspend fun getAllPromo(): Flow<List<PromoListResponseItem>> {
        return source.getPromo()
    }

    override suspend fun getStatusResponse(): Flow<StatusResponse> {
        return source.status
    }
}