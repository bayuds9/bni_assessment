package id.flowerencee.qrpaymentapp.data.repository.source.local.useraccount

import id.flowerencee.qrpaymentapp.data.database.dao.UserAccountDao
import id.flowerencee.qrpaymentapp.data.model.entity.UserAccount
import kotlinx.coroutines.flow.Flow

class UserAccountDataSourceImpl(
    private val dao: UserAccountDao
) : UserAccountDataSource {
    override suspend fun getAllAccount(): Flow<List<UserAccount>> {
        return dao.getAllUserAccount()
    }

    override suspend fun addUserAccount(userAccount: UserAccount): Long {
        return dao.addUserAccount(userAccount)
    }

    override suspend fun updateAccount(userAccount: UserAccount) {
        dao.updateAccount(userAccount)
    }

    override suspend fun getAccount(id: Int): UserAccount? {
        return dao.getAccount(id)
    }

    override suspend fun getAccountByNumber(number: String): UserAccount? {
        return dao.getAccountByNumber(number)
    }
}