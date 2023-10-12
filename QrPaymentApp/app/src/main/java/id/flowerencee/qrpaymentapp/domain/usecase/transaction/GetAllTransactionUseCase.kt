package id.flowerencee.qrpaymentapp.domain.usecase.transaction

import id.flowerencee.qrpaymentapp.data.entity.Transaction
import id.flowerencee.qrpaymentapp.domain.repository.transaction.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetAllTransactionUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(): Flow<List<Transaction>> = transactionRepository.getAllTransaction()
}