package id.flowerencee.qrpaymentapp.domain.usecase.transaction

import id.flowerencee.qrpaymentapp.data.entity.Transaction
import id.flowerencee.qrpaymentapp.domain.repository.transaction.TransactionRepository
import id.flowerencee.qrpaymentapp.domain.repository.useraccount.UserAccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AddTransactionUseCase(
    private val transactionRepository: TransactionRepository,
    private val userAccountRepository: UserAccountRepository
) {
    suspend fun execute(transaction: Transaction): Long {
        return performTransaction(transaction)
    }

    private suspend fun performTransaction(transaction: Transaction): Long {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.Main).launch {
                val inquiryTransaction =
                    async(Dispatchers.IO) { performInquiryTransaction(transaction) }
                val inquiryAccount = async(Dispatchers.IO) { performInquiryAccount(transaction) }
                val accountSuccess = inquiryAccount.await()
                val transactionRow = inquiryTransaction.await()
                val transactionSuccess = transactionRow > 0
                if (transactionSuccess && accountSuccess) {
                    transactionRepository.getTransaction(transactionRow.toInt())?.apply {
                        this.transactionStatus = true
                        this.transactionTime = System.currentTimeMillis()
                        transactionRepository.updateTransaction(this)
                    }
                }
                continuation.resume(transactionRow)
            }
        }
    }

    private suspend fun performInquiryTransaction(transaction: Transaction): Long {
        return transactionRepository.addTransaction(transaction)
    }

    private suspend fun performInquiryAccount(transactionSource: Transaction): Boolean {
        return withContext(Dispatchers.Main) {
            transactionSource.transactionSource.let { src ->
                when (src != null) {
                    true -> {
                        val account = userAccountRepository.getAccount(src)
                        when (account != null) {
                            true -> {
                                try {
                                    when (transactionSource.transactionAmount!! < account.balance!!) {
                                        true -> {
                                            account.apply {
                                                this.balance =
                                                    this.balance?.minus(transactionSource.transactionAmount!!)
                                            }
                                            userAccountRepository.updateUserAccount(account)
                                            true
                                        }

                                        false -> false
                                    }
                                } catch (e: NullPointerException) {
                                    e.printStackTrace()
                                    false
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    false
                                }
                            }

                            false -> false
                        }
                    }

                    false -> false
                }
            }
        }
    }
}