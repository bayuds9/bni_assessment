package id.flowerencee.qrpaymentapp.domain.repository.useraccount

import id.flowerencee.qrpaymentapp.data.entity.UserAccount
import kotlinx.coroutines.flow.Flow

interface UserAccountRepository {
    suspend fun getAllAccount(): Flow<List<UserAccount>>
    suspend fun addUserAccount(userAccount: UserAccount): Long
    suspend fun getAccount(id: Int): UserAccount?
    suspend fun getAccountByNumber(number: String): UserAccount?
    suspend fun updateUserAccount(userAccount: UserAccount)
}