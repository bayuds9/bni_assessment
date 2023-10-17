package id.flowerencee.qrpaymentapp.domain.usecase.transaction

import id.flowerencee.qrpaymentapp.domain.repository.transaction.TransactionRepository

class GetLimitedTransactionDescendingUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(limit: Int) = transactionRepository.getLimitedTransactionDescending(limit)
}