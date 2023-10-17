package id.flowerencee.qrpaymentapp.domain.repository.promo

import androidx.lifecycle.LiveData
import id.flowerencee.qrpaymentapp.data.model.response.failed.StatusResponse
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoListResponse
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoListResponseItem
import kotlinx.coroutines.flow.Flow

interface PromoRepository {
    suspend fun getAllPromo(): Flow<List<PromoListResponseItem>>
    suspend fun getStatusResponse(): LiveData<StatusResponse>
}