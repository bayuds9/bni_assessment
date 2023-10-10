package id.flowerencee.qrpayapp.domain.usecase.useraccount

import id.flowerencee.qrpayapp.data.entity.UserAccount
import id.flowerencee.qrpayapp.domain.repository.useraccount.UserAccountRepository

class UpdateAccountUseCase(
    private val userAccountRepository: UserAccountRepository
) {

    suspend fun execute(userAccount: UserAccount) {
        userAccountRepository.updateUserAccount(userAccount)
    }
}