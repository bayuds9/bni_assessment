package id.flowerencee.qrpaymentapp.domain.usecase.transaction

import id.flowerencee.qrpaymentapp.data.entity.Transaction
import id.flowerencee.qrpaymentapp.domain.repository.transaction.TransactionRepository

class UpdateTransactionUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(transaction: Transaction) {
        transactionRepository.updateTransaction(transaction)
    }
}