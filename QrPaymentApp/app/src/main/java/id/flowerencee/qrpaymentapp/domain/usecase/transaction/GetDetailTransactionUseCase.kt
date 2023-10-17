package id.flowerencee.qrpaymentapp.domain.usecase.transaction

import id.flowerencee.qrpaymentapp.data.model.entity.Transaction
import id.flowerencee.qrpaymentapp.domain.repository.transaction.TransactionRepository

class GetDetailTransactionUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(id: Int): Transaction? = transactionRepository.getTransaction(id)
}