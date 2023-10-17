package id.flowerencee.qrpaymentapp.data.repository.source.local.useraccount

import id.flowerencee.qrpaymentapp.data.model.entity.UserAccount
import kotlinx.coroutines.flow.Flow

interface UserAccountDataSource {
    suspend fun getAllAccount(): Flow<List<UserAccount>>
    suspend fun addUserAccount(userAccount: UserAccount): Long
    suspend fun updateAccount(userAccount: UserAccount)
    suspend fun getAccount(id: Int): UserAccount?
    suspend fun getAccountByNumber(number: String): UserAccount?
}