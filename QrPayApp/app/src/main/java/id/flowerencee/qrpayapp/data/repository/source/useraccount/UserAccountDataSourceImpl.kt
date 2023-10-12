package id.flowerencee.qrpayapp.data.repository.source.useraccount

import id.flowerencee.qrpayapp.data.database.dao.UserAccountDao
import id.flowerencee.qrpayapp.data.entity.UserAccount
import id.flowerencee.qrpayapp.support.DeLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

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
}