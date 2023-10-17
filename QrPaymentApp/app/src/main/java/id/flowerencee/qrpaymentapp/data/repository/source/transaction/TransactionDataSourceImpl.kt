package id.flowerencee.qrpaymentapp.data.repository.source.transaction

import id.flowerencee.qrpaymentapp.data.database.dao.TransactionDao
import id.flowerencee.qrpaymentapp.data.entity.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class TransactionDataSourceImpl(
    private val dao: TransactionDao
) : TransactionDataSource {
    override suspend fun getAllTransaction(): Flow<List<Transaction>> {
        return dao.getAllTransaction()
    }

    override suspend fun getLimitedTransactionDescending(limit: Int): Flow<List<Transaction>> {
        return dao.getLimitedTransactionDescending(limit)
    }

    override suspend fun getAllTransactionFromAccountId(id: Int): Flow<List<Transaction>> {
        return dao.getTransactionFromAccountId(id)
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        dao.updateTransaction(transaction)
    }

    override suspend fun addTransaction(transaction: Transaction): Long {
        return dao.addTransaction(transaction)
    }

    override suspend fun getTransaction(id: Int): Transaction? {
        return dao.getTransactionDetail(id)
    }
}