package id.flowerencee.qrpayapp.domain.repository.useraccount

import id.flowerencee.qrpayapp.data.entity.UserAccount
import kotlinx.coroutines.flow.Flow

interface UserAccountRepository {
    suspend fun getAllAccount(): Flow<List<UserAccount>>
    suspend fun addUserAccount(userAccount: UserAccount): Long
    suspend fun getAccount(id: Int): UserAccount?
    suspend fun updateUserAccount(userAccount: UserAccount)
}