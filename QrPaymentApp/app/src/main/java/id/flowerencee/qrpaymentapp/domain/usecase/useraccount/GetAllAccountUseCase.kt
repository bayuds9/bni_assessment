package id.flowerencee.qrpaymentapp.domain.usecase.useraccount

import id.flowerencee.qrpaymentapp.data.model.entity.UserAccount
import id.flowerencee.qrpaymentapp.domain.repository.useraccount.UserAccountRepository
import kotlinx.coroutines.flow.Flow

class GetAllAccountUseCase(
    private val userAccountRepository: UserAccountRepository
) {
    suspend fun execute(): Flow<List<UserAccount>> = userAccountRepository.getAllAccount()
}