package id.flowerencee.qrpaymentapp.domain.usecase.useraccount

import id.flowerencee.qrpaymentapp.data.entity.UserAccount
import id.flowerencee.qrpaymentapp.domain.repository.useraccount.UserAccountRepository

class GetAccountUseCase(
    private val userAccountRepository: UserAccountRepository
) {
    suspend fun execute(id: Int): UserAccount? = userAccountRepository.getAccount(id)
}