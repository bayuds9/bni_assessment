package id.flowerencee.qrpaymentapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import id.flowerencee.qrpaymentapp.data.entity.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transaction: Transaction): Long

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Query("SELECT * FROM `transaction` WHERE transaction_id IN (:id)")
    suspend fun getTransactionDetail(id: Int): Transaction?

    @Query("SELECT * FROM `transaction`")
    fun getAllTransaction(): Flow<List<Transaction>>

    @Query("SELECT * FROM `transaction` ORDER BY transaction_time DESC LIMIT (:limit)")
    fun getLimitedTransactionDescending(limit: Int): Flow<List<Transaction>>

    @Query("SELECT * FROM `transaction` WHERE transaction_source IN (:id)")
    fun getTransactionFromAccountId(id: Int): Flow<List<Transaction>>
}