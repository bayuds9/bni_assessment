package id.flowerencee.qrpayapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import id.flowerencee.qrpayapp.data.entity.Transaction

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transaction: Transaction): Long

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Query("SELECT * FROM `transaction` WHERE transaction_id IN (:id)")
    suspend fun getTransactionDetail(id: Int): Transaction?

    @Query("SELECT * FROM `transaction`")
    suspend fun getAllTransaction(): List<Transaction>
}