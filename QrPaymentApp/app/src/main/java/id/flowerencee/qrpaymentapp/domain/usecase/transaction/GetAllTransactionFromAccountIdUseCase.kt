package id.flowerencee.qrpaymentapp.domain.usecase.transaction

import id.flowerencee.qrpaymentapp.data.entity.Transaction
import id.flowerencee.qrpaymentapp.domain.repository.transaction.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetAllTransactionFromAccountIdUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(id: Int): Flow<List<Transaction>> = transactionRepository.getAllTransactionFromAccountId(id)
}