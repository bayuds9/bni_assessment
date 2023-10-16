package id.flowerencee.qrpaymentapp.dependency

import android.content.Context
import id.flowerencee.qrpaymentapp.data.database.QrPayDatabase
import id.flowerencee.qrpaymentapp.data.database.dao.TransactionDao
import id.flowerencee.qrpaymentapp.data.database.dao.UserAccountDao
import id.flowerencee.qrpaymentapp.data.repository.implementation.transaction.TransactionRepositoryImpl
import id.flowerencee.qrpaymentapp.data.repository.implementation.useraccount.UserAccountRepositoryImpl
import id.flowerencee.qrpaymentapp.data.repository.source.transaction.TransactionDataSourceImpl
import id.flowerencee.qrpaymentapp.data.repository.source.useraccount.UserAccountDataSourceImpl
import id.flowerencee.qrpaymentapp.domain.repository.transaction.TransactionRepository
import id.flowerencee.qrpaymentapp.domain.repository.useraccount.UserAccountRepository
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.AddTransactionUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.GetAllTransactionUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.GetDetailTransactionUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.UpdateTransactionUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.AddUserAccountUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.GetAccountUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.GetAllAccountUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.TopUpAccountBalanceUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.UpdateAccountUseCase
import id.flowerencee.qrpaymentapp.presentation.screens.main.dashboard.DashboardViewModel
import id.flowerencee.qrpaymentapp.presentation.screens.main.scanner.ScannerViewModel
import id.flowerencee.qrpaymentapp.presentation.screens.transaction.inquiry.InquiryViewModel
import id.flowerencee.qrpaymentapp.presentation.screens.transaction.receipt.ReceiptViewModel
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

    fun provideTopupAccountBalanceUseCase(
        userAccountRepository: UserAccountRepository,
        transactionRepository: TransactionRepository
    ): TopUpAccountBalanceUseCase {
        return TopUpAccountBalanceUseCase(userAccountRepository, transactionRepository)
    }

    single { provideAddUserUseCase(get()) }
    single { provideGetAccountUseCase(get()) }
    single { provideGetAllAccountUseCase(get()) }
    single { provideUpdateAccountUseCase(get()) }
    single { provideTopupAccountBalanceUseCase(get(), get()) }
}

val transactionUseCaseModule = module {
    fun provideAddTransactionUseCase(
        transactionRepository: TransactionRepository,
        userAccountRepository: UserAccountRepository
    ): AddTransactionUseCase {
        return AddTransactionUseCase(transactionRepository, userAccountRepository)
    }

    fun provideGetDetailTransactionUseCase(transactionRepository: TransactionRepository): GetDetailTransactionUseCase {
        return GetDetailTransactionUseCase(transactionRepository)
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
    viewModel { DashboardViewModel(get(), get(), get()) }
    viewModel { ScannerViewModel() }
    viewModel { InquiryViewModel(get(), get(), get()) }
    viewModel { ReceiptViewModel(get(), get()) }
    /*viewModel { PaymentViewModel(get(), get()) }
    viewModel { MainViewModel() }
    viewModel { TransactionViewModel() }*/
}