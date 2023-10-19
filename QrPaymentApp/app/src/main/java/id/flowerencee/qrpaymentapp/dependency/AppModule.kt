package id.flowerencee.qrpaymentapp.dependency

import android.content.Context
import android.content.res.Resources
import id.flowerencee.qrpaymentapp.data.database.QrPayDatabase
import id.flowerencee.qrpaymentapp.data.database.dao.TransactionDao
import id.flowerencee.qrpaymentapp.data.database.dao.UserAccountDao
import id.flowerencee.qrpaymentapp.data.networking.KtorService
import id.flowerencee.qrpaymentapp.data.repository.implementation.portfolio.PortfolioRepositoryImpl
import id.flowerencee.qrpaymentapp.data.repository.implementation.promo.PromoRepositoryImpl
import id.flowerencee.qrpaymentapp.data.repository.implementation.transaction.TransactionRepositoryImpl
import id.flowerencee.qrpaymentapp.data.repository.implementation.useraccount.UserAccountRepositoryImpl
import id.flowerencee.qrpaymentapp.data.repository.source.local.portfolio.PortfolioDataSourceImpl
import id.flowerencee.qrpaymentapp.data.repository.source.local.transaction.TransactionDataSourceImpl
import id.flowerencee.qrpaymentapp.data.repository.source.local.useraccount.UserAccountDataSourceImpl
import id.flowerencee.qrpaymentapp.data.repository.source.remote.promo.PromoDataSourceImpl
import id.flowerencee.qrpaymentapp.domain.repository.portfolio.PortfolioRepository
import id.flowerencee.qrpaymentapp.domain.repository.promo.PromoRepository
import id.flowerencee.qrpaymentapp.domain.repository.transaction.TransactionRepository
import id.flowerencee.qrpaymentapp.domain.repository.useraccount.UserAccountRepository
import id.flowerencee.qrpaymentapp.domain.usecase.portfolio.GetPortfolioUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.promo.GetAllPromoUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.AddTransactionUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.GetAllTransactionFromAccountIdUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.GetAllTransactionUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.GetDetailTransactionUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.GetLimitedTransactionDescendingUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.UpdateTransactionUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.AddUserAccountUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.GetAccountUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.GetAllAccountUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.TopUpAccountBalanceUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.UpdateAccountUseCase
import id.flowerencee.qrpaymentapp.presentation.screens.main.account.AccountViewModel
import id.flowerencee.qrpaymentapp.presentation.screens.main.account.history.HistoryViewModel
import id.flowerencee.qrpaymentapp.presentation.screens.main.cart.CartViewModel
import id.flowerencee.qrpaymentapp.presentation.screens.main.dashboard.DashboardViewModel
import id.flowerencee.qrpaymentapp.presentation.screens.main.scanner.ScannerViewModel
import id.flowerencee.qrpaymentapp.presentation.screens.promo.PromoViewModel
import id.flowerencee.qrpaymentapp.presentation.screens.transaction.inquiry.InquiryViewModel
import id.flowerencee.qrpaymentapp.presentation.screens.transaction.receipt.ReceiptViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val apiModule = module {
    fun provideApiService(context: Context): KtorService {
        return KtorService.create(context)
    }

    single { provideApiService(androidApplication()) }
}

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

val dataSourceModule = module {
    fun provideUserAccountDataSource(dao: UserAccountDao): UserAccountDataSourceImpl {
        return UserAccountDataSourceImpl(dao)
    }

    fun provideTransactionDataSource(dao: TransactionDao): TransactionDataSourceImpl {
        return TransactionDataSourceImpl(dao)
    }

    fun providePromoDataSource(ktorService: KtorService): PromoDataSourceImpl {
        return PromoDataSourceImpl(ktorService)
    }

    fun providePortfolioDataSource(resources: Resources): PortfolioDataSourceImpl {
        return PortfolioDataSourceImpl(resources)
    }

    single { provideUserAccountDataSource(get()) }
    single { provideTransactionDataSource(get()) }
    single { providePromoDataSource(get()) }
    single { providePortfolioDataSource(androidContext().resources) }

}

val repositoryModule = module {
    fun provideUserAccountRepositoryImpl(source: UserAccountDataSourceImpl): UserAccountRepository {
        return UserAccountRepositoryImpl(source)
    }

    fun provideTransactionRepositoryImpl(source: TransactionDataSourceImpl): TransactionRepository {
        return TransactionRepositoryImpl(source)
    }

    fun providePromoRepositoryImpl(source: PromoDataSourceImpl): PromoRepository {
        return PromoRepositoryImpl(source)
    }

    fun providePortfolioRepositoryImpl(source: PortfolioDataSourceImpl): PortfolioRepository {
        return PortfolioRepositoryImpl(source)
    }

    single { provideUserAccountRepositoryImpl(get()) }
    single { provideTransactionRepositoryImpl(get()) }
    single { providePromoRepositoryImpl(get()) }
    single { providePortfolioRepositoryImpl(get()) }
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

    fun provideAllTransactionFromAccountId(transactionRepository: TransactionRepository): GetAllTransactionFromAccountIdUseCase {
        return GetAllTransactionFromAccountIdUseCase(transactionRepository)
    }

    fun provideLimitedTransactionDescending(transactionRepository: TransactionRepository): GetLimitedTransactionDescendingUseCase {
        return GetLimitedTransactionDescendingUseCase(transactionRepository)
    }

    fun provideUpdateTransactionUseCase(transactionRepository: TransactionRepository): UpdateTransactionUseCase {
        return UpdateTransactionUseCase(transactionRepository)
    }
    single { provideAddTransactionUseCase(get(), get()) }
    single { provideGetDetailTransactionUseCase(get()) }
    single { provideAllTransactionUseCase(get()) }
    single { provideAllTransactionFromAccountId(get()) }
    single { provideLimitedTransactionDescending(get()) }
    single { provideUpdateTransactionUseCase(get()) }
}

val promoUseCaseModule = module {
    fun provideGetAllPromoUseCase(promoRepository: PromoRepository): GetAllPromoUseCase {
        return GetAllPromoUseCase(promoRepository)
    }

    single { provideGetAllPromoUseCase(get()) }
}

val portfolioUseCaseModule = module {
    fun provideGetPortfolioUseCase(portfolioRepository: PortfolioRepository): GetPortfolioUseCase {
        return GetPortfolioUseCase(portfolioRepository)
    }
    single { provideGetPortfolioUseCase(get()) }
}

val viewModelModule = module {
    viewModel { DashboardViewModel(get(), get()) }
    viewModel { AccountViewModel(get(), get()) }
    viewModel { ScannerViewModel() }
    viewModel { CartViewModel(get()) }
    viewModel { InquiryViewModel(get(), get(), get()) }
    viewModel { ReceiptViewModel(get(), get()) }
    viewModel { HistoryViewModel(get(), get()) }
    viewModel { PromoViewModel() }
    /*viewModel { PaymentViewModel(get(), get()) }
    viewModel { MainViewModel() }
    viewModel { TransactionViewModel() }*/
}