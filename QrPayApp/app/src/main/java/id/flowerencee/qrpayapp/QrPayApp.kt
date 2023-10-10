package id.flowerencee.qrpayapp

import android.app.Application
import id.flowerencee.qrpayapp.injection.accountUseCaseModule
import id.flowerencee.qrpayapp.injection.databaseModule
import id.flowerencee.qrpayapp.injection.repositoryModule
import id.flowerencee.qrpayapp.injection.transactionUseCaseModule
import id.flowerencee.qrpayapp.injection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class QrPayApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@QrPayApp)
            modules(listOf(databaseModule, repositoryModule, accountUseCaseModule, transactionUseCaseModule, viewModelModule))
        }
    }
}