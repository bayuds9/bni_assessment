package id.flowerencee.qrpayapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import id.flowerencee.qrpayapp.data.entity.UserAccount

@Dao
interface UserAccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserAccount(userAccount: UserAccount): Long

    @Update
    suspend fun updateAccount(userAccount: UserAccount)

    @Query("SELECT * FROM user_account")
    suspend fun getAllUserAccount(): List<UserAccount>

    @Query("SELECT * FROM `user_account` WHERE account_id IN (:id)")
    suspend fun getAccount(id: Int): UserAccount?
}