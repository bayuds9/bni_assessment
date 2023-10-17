package id.flowerencee.qrpaymentapp.data.repository.implementation.transaction

import id.flowerencee.qrpaymentapp.data.model.entity.Transaction
import id.flowerencee.qrpaymentapp.data.repository.source.local.transaction.TransactionDataSourceImpl
import id.flowerencee.qrpaymentapp.domain.repository.transaction.TransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(
    private val source: TransactionDataSourceImpl
) : TransactionRepository {
    override suspend fun getAllTransaction(): Flow<List<Transaction>> {
        return source.getAllTransaction()
    }

    override suspend fun getLimitedTransactionDescending(limit: Int): Flow<List<Transaction>> {
        return source.getLimitedTransactionDescending(limit)
    }

    override suspend fun getAllTransactionFromAccountId(id: Int): Flow<List<Transaction>> {
        return source.getAllTransactionFromAccountId(id)
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        source.updateTransaction(transaction)
    }

    override suspend fun addTransaction(transaction: Transaction): Long {
        return source.addTransaction(transaction)
    }

    override suspend fun getTransaction(id: Int): Transaction? {
        return source.getTransaction(id)
    }
}