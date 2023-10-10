package id.flowerencee.qrpayapp.domain.usecase.transaction

import id.flowerencee.qrpayapp.data.entity.Transaction
import id.flowerencee.qrpayapp.domain.repository.transaction.TransactionRepository

class UpdateTransactionUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(transaction: Transaction) {
        transactionRepository.updateTransaction(transaction)
    }
}