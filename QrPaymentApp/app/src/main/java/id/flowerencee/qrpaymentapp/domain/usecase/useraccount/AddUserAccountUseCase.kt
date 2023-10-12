package id.flowerencee.qrpaymentapp.domain.usecase.useraccount

import id.flowerencee.qrpaymentapp.data.entity.UserAccount
import id.flowerencee.qrpaymentapp.domain.repository.useraccount.UserAccountRepository

class AddUserAccountUseCase(
    private val userAccountRepository: UserAccountRepository
) {
    suspend fun execute(userAccount: UserAccount): Long = userAccountRepository.addUserAccount(userAccount)
}