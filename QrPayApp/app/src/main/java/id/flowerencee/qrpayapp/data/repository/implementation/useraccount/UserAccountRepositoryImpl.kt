package id.flowerencee.qrpayapp.data.repository.implementation.useraccount

import id.flowerencee.qrpayapp.data.entity.UserAccount
import id.flowerencee.qrpayapp.data.repository.source.useraccount.UserAccountDataSourceImpl
import id.flowerencee.qrpayapp.domain.repository.useraccount.UserAccountRepository
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