package id.flowerencee.qrpayapp.domain.usecase.useraccount

import id.flowerencee.qrpayapp.data.entity.Transaction
import id.flowerencee.qrpayapp.domain.repository.transaction.TransactionRepository
import id.flowerencee.qrpayapp.domain.repository.useraccount.UserAccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TopUpAccountBalanceUseCase(
    private val userAccountRepository: UserAccountRepository,
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(id: Int, amount: Double): Long {
        return performBalanceTopup(id, amount)
    }

    private suspend fun performBalanceTopup(id: Int, amount: Double): Long {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.Main).launch {
                val inquiryAccount = async(Dispatchers.IO) { performInquiryAccount(id, amount) }
                val inquiryTransaction =
                    async(Dispatchers.IO) { performInquiryTransaction(id, amount) }
                val accountResult = inquiryAccount.await()
                val transactionResult = inquiryTransaction.await()
                when {
                    transactionResult <= 0 -> performInquiryAccount(id, -amount)
                    accountResult -> {
                        transactionRepository.getTransaction(transactionResult.toInt())
                            ?.let { trx ->
                                trx.apply {
                                    transactionTime = System.currentTimeMillis()
                                    transactionStatus = true
                                }
                                transactionRepository.updateTransaction(trx)

                            }
                    }
                }
                continuation.resume(transactionResult)
            }
        }
    }

    private suspend fun performInquiryTransaction(id: Int, amount: Double): Long {
        return withContext(Dispatchers.Main) {
            val profile = userAccountRepository.getAccount(id)
            when (profile != null) {
                true -> {
                    val transaction = Transaction(
                        transactionName = "Topup Saldo",
                        transactionAmount = amount,
                        transactionDestination = profile.account_owner_name
                    )
                    transactionRepository.addTransaction(transaction)
                }

                false -> 0
            }
        }
    }

    private suspend fun performInquiryAccount(id: Int, amount: Double): Boolean {
        return withContext(Dispatchers.Main) {
            id.let { src ->
                val account = userAccountRepository.getAccount(src)
                when (account != null) {
                    true -> {
                        try {
                            account.balance = account.balance?.plus(amount)
                            true
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
        }
    }
}