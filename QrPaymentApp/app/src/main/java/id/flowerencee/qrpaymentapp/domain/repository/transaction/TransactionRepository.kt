package id.flowerencee.qrpaymentapp.domain.repository.transaction

import id.flowerencee.qrpaymentapp.data.entity.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getAllTransaction(): Flow<List<Transaction>>
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun addTransaction(transaction: Transaction): Long
    suspend fun getTransaction(id: Int): Transaction?
}