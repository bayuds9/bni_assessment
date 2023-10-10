package id.flowerencee.qrpayapp.data.repository.implementation.transaction

import id.flowerencee.qrpayapp.data.entity.Transaction
import id.flowerencee.qrpayapp.data.repository.source.transaction.TransactionDataSourceImpl
import id.flowerencee.qrpayapp.domain.repository.transaction.TransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(
    private val source: TransactionDataSourceImpl
) : TransactionRepository {
    override suspend fun getAllTransaction(): Flow<List<Transaction>> {
        return source.getAllTransaction()
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