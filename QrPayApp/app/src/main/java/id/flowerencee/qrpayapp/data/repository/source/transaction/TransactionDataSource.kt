package id.flowerencee.qrpayapp.data.repository.source.transaction

import id.flowerencee.qrpayapp.data.entity.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionDataSource {
    suspend fun getAllTransaction(): Flow<List<Transaction>>
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun addTransaction(transaction: Transaction): Long
    suspend fun getTransaction(id: Int): Transaction?
}