package id.flowerencee.qrpayapp.domain.usecase.useraccount

import id.flowerencee.qrpayapp.data.entity.UserAccount
import id.flowerencee.qrpayapp.domain.repository.useraccount.UserAccountRepository

class GetAccountUseCase(
    private val userAccountRepository: UserAccountRepository
) {
    suspend fun execute(id: Int): UserAccount? = userAccountRepository.getAccount(id)
}