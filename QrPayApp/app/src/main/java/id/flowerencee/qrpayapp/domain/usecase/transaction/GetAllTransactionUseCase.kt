package id.flowerencee.qrpayapp.domain.usecase.transaction

import id.flowerencee.qrpayapp.data.entity.Transaction
import id.flowerencee.qrpayapp.domain.repository.transaction.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetAllTransactionUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(): Flow<List<Transaction>> = transactionRepository.getAllTransaction()
}