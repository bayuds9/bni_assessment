package id.flowerencee.qrpayapp.support.injection

import android.content.Context
import id.flowerencee.qrpayapp.data.database.QrPayDatabase
import id.flowerencee.qrpayapp.data.database.dao.TransactionDao
import id.flowerencee.qrpayapp.data.database.dao.UserAccountDao
import id.flowerencee.qrpayapp.data.repository.implementation.transaction.TransactionRepositoryImpl
import id.flowerencee.qrpayapp.data.repository.implementation.useraccount.UserAccountRepositoryImpl
import id.flowerencee.qrpayapp.data.repository.source.transaction.TransactionDataSourceImpl
import id.flowerencee.qrpayapp.data.repository.source.useraccount.UserAccountDataSourceImpl
import id.flowerencee.qrpayapp.domain.repository.transaction.TransactionRepository
import id.flowerencee.qrpayapp.domain.repository.useraccount.UserAccountRepository
import id.flowerencee.qrpayapp.domain.usecase.transaction.AddTransactionUseCase
import id.flowerencee.qrpayapp.domain.usecase.transaction.GetAllTransactionUseCase
import id.flowerencee.qrpayapp.domain.usecase.transaction.GetDetailTransaction
import id.flowerencee.qrpayapp.domain.usecase.transaction.UpdateTransactionUseCase
import id.flowerencee.qrpayapp.domain.usecase.useraccount.AddUserAccountUseCase
import id.flowerencee.qrpayapp.domain.usecase.useraccount.GetAccountUseCase
import id.flowerencee.qrpayapp.domain.usecase.useraccount.GetAllAccountUseCase
import id.flowerencee.qrpayapp.domain.usecase.useraccount.TopUpAccountBalanceUseCase
import id.flowerencee.qrpayapp.domain.usecase.useraccount.UpdateAccountUseCase
import id.flowerencee.qrpayapp.presentation.PaymentViewModel
import id.flowerencee.qrpayapp.presentation.ui.screens.main.MainViewModel
import id.flowerencee.qrpayapp.presentation.ui.screens.main.dashboard.DashboardViewModel
import id.flowerencee.qrpayapp.presentation.ui.screens.transaction.TransactionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(context: Context): QrPayDatabase {
        return QrPayDatabase.getInstance(context)
    }
    fun provideTransactionDao(database: QrPayDatabase): TransactionDao {
        return database.transactionDao
    }
    fun provideUserAccountDao(database: QrPayDatabase): UserAccountDao {
        return database.userAccountDao
    }

    single { provideDatabase(androidContext()) }
    single { provideTransactionDao(get()) }
    single { provideUserAccountDao(get()) }
}

val repositoryModule = module {
    fun provideUserAccountDataSource(dao: UserAccountDao): UserAccountDataSourceImpl {
        return UserAccountDataSourceImpl(dao)
    }
    fun provideTransactionDataSource(dao: TransactionDao): TransactionDataSourceImpl {
        return TransactionDataSourceImpl(dao)
    }
    fun provideUserAccountRepositoryImpl(source: UserAccountDataSourceImpl): UserAccountRepository {
        return UserAccountRepositoryImpl(source)
    }
    fun provideTransactionRepositoryImpl(source: TransactionDataSourceImpl): TransactionRepository {
        return TransactionRepositoryImpl(source)
    }
    single { provideUserAccountDataSource(get()) }
    single { provideTransactionDataSource(get()) }
    single { provideUserAccountRepositoryImpl(get()) }
    single { provideTransactionRepositoryImpl(get()) }
}

val accountUseCaseModule = module {
    fun provideAddUserUseCase(userAccountRepository: UserAccountRepository): AddUserAccountUseCase {
        return AddUserAccountUseCase(userAccountRepository)
    }
    fun provideGetAccountUseCase(userAccountRepository: UserAccountRepository): GetAccountUseCase {
        return GetAccountUseCase(userAccountRepository)
    }
    fun provideGetAllAccountUseCase(userAccountRepository: UserAccountRepository): GetAllAccountUseCase {
        return GetAllAccountUseCase(userAccountRepository)
    }
    fun provideUpdateAccountUseCase(userAccountRepository: UserAccountRepository): UpdateAccountUseCase {
        return UpdateAccountUseCase(userAccountRepository)
    }
    fun provideTopupAccountBalanceUseCase(userAccountRepository: UserAccountRepository, transactionRepository: TransactionRepository): TopUpAccountBalanceUseCase {
        return TopUpAccountBalanceUseCase(userAccountRepository, transactionRepository)
    }

    single { provideAddUserUseCase(get()) }
    single { provideGetAccountUseCase(get()) }
    single { provideGetAllAccountUseCase(get()) }
    single { provideUpdateAccountUseCase(get()) }
    single { provideTopupAccountBalanceUseCase(get(), get()) }
}

val transactionUseCaseModule = module {
    fun provideAddTransactionUseCase(transactionRepository: TransactionRepository, userAccountRepository: UserAccountRepository): AddTransactionUseCase {
        return AddTransactionUseCase(transactionRepository, userAccountRepository)
    }
    fun provideGetDetailTransactionUseCase(transactionRepository: TransactionRepository): GetDetailTransaction {
        return GetDetailTransaction(transactionRepository)
    }
    fun provideAllTransactionUseCase(transactionRepository: TransactionRepository): GetAllTransactionUseCase {
        return GetAllTransactionUseCase(transactionRepository)
    }
    fun provideUpdateTransactionUseCase(transactionRepository: TransactionRepository): UpdateTransactionUseCase {
        return UpdateTransactionUseCase(transactionRepository)
    }
    single { provideAddTransactionUseCase(get(), get()) }
    single { provideGetDetailTransactionUseCase(get()) }
    single { provideAllTransactionUseCase(get()) }
    single { provideUpdateTransactionUseCase(get()) }
}

val viewModelModule = module {
    viewModel { PaymentViewModel(get(), get()) }
    viewModel { DashboardViewModel(get(), get()) }
    viewModel { MainViewModel() }
    viewModel { TransactionViewModel() }
}