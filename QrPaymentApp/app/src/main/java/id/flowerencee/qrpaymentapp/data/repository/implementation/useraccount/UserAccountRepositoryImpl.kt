package id.flowerencee.qrpaymentapp.data.repository.implementation.useraccount

import id.flowerencee.qrpaymentapp.data.entity.UserAccount
import id.flowerencee.qrpaymentapp.data.repository.source.useraccount.UserAccountDataSourceImpl
import id.flowerencee.qrpaymentapp.domain.repository.useraccount.UserAccountRepository
import kotlinx.coroutines.flow.Flow

class UserAccountRepositoryImpl(
    private val source: UserAccountDataSourceImpl
) : UserAccountRepository {
    override suspend fun getAllAccount(): Flow<List<UserAccount>> {
        return source.getAllAccount()
    }

    override suspend fun addUserAccount(userAccount: UserAccount): Long {
        return source.addUserAccount(userAccount)
    }

    override suspend fun getAccount(id: Int): UserAccount? {
        return source.getAccount(id)
    }

    override suspend fun updateUserAccount(userAccount: UserAccount) {
        source.updateAccount(userAccount)
    }
}