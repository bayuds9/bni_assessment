package id.flowerencee.qrpaymentapp.domain.usecase.useraccount

import id.flowerencee.qrpaymentapp.data.entity.UserAccount
import id.flowerencee.qrpaymentapp.domain.repository.useraccount.UserAccountRepository

class UpdateAccountUseCase(
    private val userAccountRepository: UserAccountRepository
) {

    suspend fun execute(userAccount: UserAccount) {
        userAccountRepository.updateUserAccount(userAccount)
    }
}