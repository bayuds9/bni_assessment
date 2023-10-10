package id.flowerencee.qrpayapp.domain.usecase.useraccount

import id.flowerencee.qrpayapp.data.entity.UserAccount
import id.flowerencee.qrpayapp.domain.repository.useraccount.UserAccountRepository
import kotlinx.coroutines.flow.Flow

class GetAllAccountUseCase(
    private val userAccountRepository: UserAccountRepository
) {
    suspend fun execute(): Flow<List<UserAccount>> = userAccountRepository.getAllAccount()
}