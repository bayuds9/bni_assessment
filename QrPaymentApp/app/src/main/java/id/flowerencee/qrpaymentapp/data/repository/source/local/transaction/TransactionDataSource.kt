package id.flowerencee.qrpaymentapp.data.repository.source.local.transaction

import id.flowerencee.qrpaymentapp.data.model.entity.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionDataSource {
    suspend fun getAllTransaction(): Flow<List<Transaction>>
    suspend fun getLimitedTransactionDescending(limit: Int): Flow<List<Transaction>>
    suspend fun getAllTransactionFromAccountId(id: Int): Flow<List<Transaction>>
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun addTransaction(transaction: Transaction): Long
    suspend fun getTransaction(id: Int): Transaction?
}