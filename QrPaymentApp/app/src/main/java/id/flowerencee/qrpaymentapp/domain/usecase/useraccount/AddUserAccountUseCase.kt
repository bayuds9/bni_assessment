package id.flowerencee.qrpaymentapp.domain.usecase.useraccount

import id.flowerencee.qrpaymentapp.data.model.entity.UserAccount
import id.flowerencee.qrpaymentapp.domain.repository.useraccount.UserAccountRepository
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog

class AddUserAccountUseCase(
    private val userAccountRepository: UserAccountRepository
) {
    suspend fun execute(userAccount: UserAccount): Long {
        val validateAccount = userAccountRepository.getAccountByNumber(userAccount.accountNumber!!)
        return when(validateAccount == null) {
            true -> userAccountRepository.addUserAccount(userAccount)
            else -> 0
        }
    }
}