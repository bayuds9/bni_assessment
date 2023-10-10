package id.flowerencee.qrpayapp.domain.usecase.transaction

import id.flowerencee.qrpayapp.data.entity.Transaction
import id.flowerencee.qrpayapp.domain.repository.transaction.TransactionRepository

class GetDetailTransaction(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(id: Int): Transaction? = transactionRepository.getTransaction(id)
}